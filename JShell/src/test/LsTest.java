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
package test;

import commands.Ls;
import console.Console;
import console.OutputQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LsTest {
  // make our initial variables
  private Ls testLs;
  private Console testConsole;

  // we are using the console from TestSetUp

  /* The file system in the testConsole is structured as follows:
   *
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

  // where someFile has contents of "more contents"
  // where fileWithContents has contents of "This file has contents"

  /**
   * Set up a console with an initial file system
   */
  @Before public void setUp() {
    TestSetUp holder = new TestSetUp();
    testConsole = holder.getConsole1();
  }

  /**
   * Test Ls' ability to check the contents in the current working directory
   */
  @Test public void testSimpleLs() {
    // make a ls array
    String lsArray[] = {"ls"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all output
    String actual = theOutput.getAllOutput();

    // then we make an expected string
    String expected = "one two three fileWithContents\n\n";

    // then we check if both of the strings are equal
    assertEquals(expected, actual);
  }

  /**
   * Test Ls' ability to check the directory of a relative path
   */
  @Test public void testRelativeLs() {
    // make a ls array
    String lsArray[] = {"ls", "one/level2one"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all output
    String actual = theOutput.getAllOutput();

    // then we make an expected string
    String expected = "one/level2one:\n";
    expected += "level3one level3two\n\n";

    // then we check if both of the strings are equal
    assertEquals(expected, actual);
  }


  /**
   * Test Ls' ability to check the directory of a relative path
   */
  @Test public void testAbsoluteLs() {
    // make a ls array
    String lsArray[] = {"ls", "/one/level2one"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all output
    String actual = theOutput.getAllOutput();

    // then we make an expected string
    String expected = "/one/level2one:\n";
    expected += "level3one level3two\n\n";

    // then we check if both of the strings are equal
    assertEquals(expected, actual);
  }

  /**
   * Test Ls' ability to check if the path given is a file
   */
  @Test public void testLsFile() {
    // make a ls array
    String lsArray[] = {"ls", "one/someFile"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all output
    String actual = theOutput.getAllOutput();

    // then we make an expected string
    String expected = "one/someFile\n\n";

    // then we check if both of the strings are equal
    assertEquals(expected, actual);
  }

  /**
   * Test Ls' ability to acknowledge multiple files given
   */
  @Test public void testLsMultiple() {
    // make a ls array
    String lsArray[] = {"ls", "two", "one/someFile", "one/level2one"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all output
    String actual = theOutput.getAllOutput();

    // then we make an expected string
    String expected = "two:\n";
    expected += "level2four level2five\n\n";

    // we check the file
    expected += "one/someFile\n\n";

    // then we check another directory
    expected += "one/level2one:\n";
    expected += "level3one level3two\n\n";

    // then we check if both of the strings are equal
    assertEquals(expected, actual);
  }

  /**
   * Test Ls' ability to determine a given invalid path
   */
  @Test public void testLsInvalidPath() {
    // make a ls array
    String lsArray[] = {"ls", "two", "NotAPath", "one/level2one"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all standard output
    String actualOut = theOutput.getOnlyStdOut();
    String actualErr = theOutput.getOnlyStdErr();

    // then we make an expected string for standard out; we check the
    // directory "two"
    String expectedOut = "two:\n";
    expectedOut += "level2four level2five\n\n";

    // then we check another directory
    expectedOut += "one/level2one:\n";
    expectedOut += "level3one level3two\n\n";

    // we make an expected standard error
    String expectedErr =
        "ls: cannot access 'NotAPath': No such file or " + "directory\n\n";

    // check if the standard out is the same
    assertEquals(expectedOut, actualOut);
    // then check if the standard err is the same
    assertEquals(expectedErr, actualErr);
  }

  /**
   * Test Ls' ability of the "-R" token, with multiple paths
   */
  @Test public void testLsRecursiveTokenWithPath() {
    // make a ls array
    String lsArray[] = {"ls", "-R", "two", "/one/level2one"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all standard output
    String actualOut = theOutput.getOnlyStdOut();

    // then we make an expected string for standard out; we check the
    // directory "two"
    String expectedOut = "two:\n";
    expectedOut += "level2four level2five\n\n";

    // we go further into two (these directories don't have anything in them)
    expectedOut += "two/level2four:\n\n\n";
    expectedOut += "two/level2five:\n\n\n";

    // then we go into the other passed path
    expectedOut += "/one/level2one:\n";
    expectedOut += "level3one level3two\n\n";

    // we go into level3one
    expectedOut += "/one/level2one/level3one:\n";
    expectedOut += "level4\n\n";

    // we go into level4
    expectedOut += "/one/level2one/level3one/level4:\n\n\n";

    // then we go back and go into level3two
    expectedOut += "/one/level2one/level3two:\n\n\n";

    // check if the standard out is the same
    assertEquals(expectedOut, actualOut);
  }

  /**
   * Test Ls' ability of the "-R" token, with multiple paths
   */
  @Test public void testLsRecursiveTokenWithoutPath() {
    // make a ls array
    String lsArray[] = {"ls", "-R"};

    // then inject the console to the ls object
    testLs = new Ls(testConsole, lsArray);
    // execute the ls command
    testLs.execute();

    // then we need to get the output queue of the testLs
    OutputQueue theOutput = testLs.getOutputQueue();

    // then we need to get all standard output
    String actualOut = theOutput.getOnlyStdOut();

    // we make our expected string
    String expectedOut = "";
    // then we make what's expected
    expectedOut += ".:\n";
    expectedOut += "one two three fileWithContents\n\n";
    expectedOut += "./one:\n";
    expectedOut += "level2one level2two level2three someFile\n\n";
    expectedOut += "./one/level2one:\n";
    expectedOut += "level3one level3two\n\n";
    expectedOut += "./one/level2one/level3one:\n";
    expectedOut += "level4\n\n";
    expectedOut += "./one/level2one/level3one/level4:\n\n\n";
    expectedOut += "./one/level2one/level3two:\n\n\n";
    expectedOut += "./one/level2two:\n\n\n";
    expectedOut += "./one/level2three:\n\n\n";
    expectedOut += "./two:\n";
    expectedOut += "level2four level2five\n\n";
    expectedOut += "./two/level2four:\n\n\n";
    expectedOut += "./two/level2five:\n\n\n";
    expectedOut += "./three:\n\n\n";

    // check if the standard out is the same
    assertEquals(expectedOut, actualOut);
  }
}
