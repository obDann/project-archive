// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: leevic10
// UT Student #: 998965133
// Author: Victor Lee
//
// Student2:
// UTORID user_name: siosonda
// UT Student #: 1001346377
// Author: Dann Sioson
//
// Student3:
// UTORID user_name: yangsh90
// UT Student #: 1004390161
// Author: Shu Qi Yang
//
// Student4:
// UTORID user_name: dongsibo
// UT Student #: 1003400269
// Author: Sibo Dong
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
/*
CREDIT FOR TESTING PRINTLN STATEMENTS:
Zheng Hong, L. (2015). JUnit with System.out.println. Retrieved from
https://limzhenghong.wordpress.com/2015/03/18/junit-with-system-out-println/
Retrieved on June 30, 2018
*/
package test;

import console.*;
import filesystem.*;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class OutputTest {

  /*
    set up initial variables used for testing
   */ Console ourCons;
  FileSystem setupFs;
  Output testOutput;
  OutputQueue simpleOPQ = new OutputQueue();
  OutputQueue largerOPQ = new OutputQueue();
  OutputQueue faultyOPQ = new OutputQueue();
  OutputStream os = new ByteArrayOutputStream();

  /* The file system is structured as follows (from TestSetUp):
   * /
   *     one
   *         level2one
   *                   level3one
   *                             level4
   *                   level3two
   *         level2two
   *         level2three
   *         someFile
   *     two
   *         level2four
   *         level2five
   *     three
   *     fileWithContents
   * */

  /**
   * Captures the print statements to the shell
   *
   * @return
   */
  @Before public void header() {
    // set up the objects needed to test output
    TestSetUp su = new TestSetUp();
    ourCons = su.getConsole1();
    setupFs = ourCons.getFileSystem();
    testOutput = new Output(setupFs);

    // we want to be able to capture the println statements
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);

    // we have to set up several output queues available for use
    simpleOPQ.enqueue("hello", true);

    largerOPQ.enqueue("bye", true);
    largerOPQ.enqueue("bigger", true);
    largerOPQ.enqueue("bigo", true);

    faultyOPQ.enqueue("bad error", false);
    faultyOPQ.enqueue("bad output queue", true);
    faultyOPQ.enqueue("stderr", false);
  }

  /**
   * Restores output to normal
   */
  @After public void footer() {
    PrintStream originalOut = System.out;
    System.setOut(originalOut);
  }

  /**
   * Returns the expected line with the System.getProperty("line.separator")
   *
   * @param expectedString
   * @return
   */
  public String expectedHelper(String expectedString) {
    expectedString += System.getProperty("line.separator");
    return expectedString;
  }

  /**
   * Tests a simple print to the shell
   */
  @Test public void testSimpleOutput() {
    // we have to first validate the output with an empty array, simply just
    // for printing
    String empty[] = {};
    try {
      // inject an empty array
      testOutput.validateDirection(empty);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);
      // our expected is "hello"; it must have a line.separator
      String expected = expectedHelper("hello");
      // then we want to test our output
      assertEquals(expected, os.toString());
    } catch (InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to make a file in a relative path
   */
  @Test public void testMakeFile() {
    // set the array to some file
    String toFile[] = {">", "./file"};
    try {
      // inject the array
      testOutput.validateDirection(toFile);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);
      // our expected is "hello"
      String expected = "hello\n";

      // get the file of the supposedly made file
      File madeFile = setupFs.getFileByPath("./file");
      String actual = madeFile.getFileContents();
      // then we want to test our output
      assertEquals(expected, actual);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to append to a file in a relative path
   */
  @Test public void testAppendFile() {
    // set the array to some file
    String toFile[] = {">>", "file"};
    String appender[] = {">>", "file"};
    try {
      // inject the array
      testOutput.validateDirection(toFile);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);

      // inject another array
      testOutput.validateDirection(appender);
      // then we want to output a series of strings
      testOutput.outputMe(largerOPQ);

      // get the file of the supposedly made file
      File madeFile = setupFs.getFileByPath("file");

      // then we get our actual output
      String actual = madeFile.getFileContents();

      // and then we make our expected string
      String expected = "hello\nbye\nbigger\nbigo\n";
      // then we want to test our output
      assertEquals(expected, actual);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to overwrite to a file
   */
  @Test public void testOverwriteFile() {
    // set the array to some file
    String toFile[] = {">", "file"};
    String appender[] = {">", "file"};
    try {
      // inject the array
      testOutput.validateDirection(toFile);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);

      // inject another array
      testOutput.validateDirection(appender);
      // then we want to output a series of strings
      testOutput.outputMe(largerOPQ);

      // get the file of the supposedly made file
      File madeFile = setupFs.getFileByPath("file");

      // then we get our actual output
      String actual = madeFile.getFileContents();

      // and then we make our expected string
      String expected = "bye\nbigger\nbigo\n";
      // then we want to test our output
      assertEquals(expected, actual);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to make a file through an absolute path
   */
  @Test public void testMakeFileViaAbsolute() {
    // set the array to some file
    String toFile[] = {">", "/one/level2one/file"};
    try {
      // inject the array
      testOutput.validateDirection(toFile);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);
      // our expected is "hello"
      String expected = "hello\n";

      // get the file of the supposedly made file
      File madeFile = setupFs.getFileByPath("/one/level2one/file");
      String actual = madeFile.getFileContents();
      // then we want to test our output
      assertEquals(expected, actual);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to append to a file through an absolute path
   */
  @Test public void testAppendFileViaAbsolute() {
    // set the array to some file
    String toFile[] = {">>", "/two/level2four/file"};
    String appender[] = {">>", "/two/level2four/file"};
    try {
      // inject the array
      testOutput.validateDirection(toFile);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);

      // inject another array
      testOutput.validateDirection(appender);
      // then we want to output a series of strings
      testOutput.outputMe(largerOPQ);

      // get the file of the supposedly made file
      File madeFile = setupFs.getFileByPath("/two/level2four/file");

      // then we get our actual output
      String actual = madeFile.getFileContents();

      // and then we make our expected string
      String expected = "hello\nbye\nbigger\nbigo\n";
      // then we want to test our output
      assertEquals(expected, actual);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to overwrite to a file through an absolute path
   */
  @Test public void testOverwriteFileViaAbsolute() {
    // set the array to an existing file with contents
    String toFile[] = {">", "/fileWithContents"};
    try {
      // inject the array
      testOutput.validateDirection(toFile);
      // then we want to output a string
      testOutput.outputMe(simpleOPQ);

      // get the file of the existing file
      File madeFile = setupFs.getFileByPath("/fileWithContents");

      // then we get our actual output
      String actual = madeFile.getFileContents();

      // and then we make our expected string
      String expected = "hello\n";
      // then we want to test our output
      assertEquals(expected, actual);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to check whether the directed file is a directory
   */
  @Test public void testDirectoryDirection() {
    // use the direction towards a directory
    String forErr[] = {">", "/one"};
    try {
      testOutput.validateDirection(forErr);
      // this should produce an error
    } catch (InvalidDirectionError e) {
      String expected = "console.InvalidDirectionError: /one is a directory"
          + " and cannot be used in the redirection of output";
      String actual = e.toString();
      assertEquals(expected, actual);
    }
  }

  /**
   * Tests output's ability to show both standard output and standard error
   * to the shell
   */
  @Test public void testStdErrPrint() {
    // just make an empty array
    String empty[] = {};
    try {
      testOutput.validateDirection(empty);
      // inject a faulty output queue
      testOutput.outputMe(faultyOPQ);

      // we create our expected output
      String expected = expectedHelper("bad error");
      expected += expectedHelper("bad output queue");
      expected += expectedHelper("stderr");

      // then we get our actual output
      String actual = os.toString();

      // then compare
      assertEquals(expected, actual);
    } catch (InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

  /**
   * Tests output's ability to show both standard output and standard error
   * to the shell
   */
  @Test public void testStdErrToFile() {
    // make an array, directing to a file
    String empty[] = {">", "file"};
    try {
      testOutput.validateDirection(empty);
      // inject a faulty output queue
      testOutput.outputMe(faultyOPQ);

      // we create our expected output to the shell
      String expectedToShell = expectedHelper("bad error");
      expectedToShell += expectedHelper("stderr");

      // we create our expected output to the file
      String expectedToFile = "bad output queue\n";

      // then we get our actual output to the shell
      String actualToShell = os.toString();

      // and then get the actual output to the file
      File holdingFile = setupFs.getFileByPath("file");
      String actualToFile = holdingFile.getFileContents();

      // then compare the strings of output to the shell
      assertEquals(expectedToShell, actualToShell);
      assertEquals(expectedToFile, actualToFile);
    } catch (UnexpectedFsoError | InvalidPathError | InvalidDirectionError e) {
      assertEquals(e.toString(), "bad");
    }
  }

}
