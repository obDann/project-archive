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
import commands.Mkdir;

import static org.junit.Assert.*;

import console.Console;
import console.OutputQueue;
import org.junit.Before;
import org.junit.Test;

public class MkdirTest {
  // make our initial variables
  private Ls testLs;
  private Console testConsole;
  private Mkdir testMkdir;

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

  // where the current working directory is in "/"

  /**
   * Set up a console with an initial file system
   */
  @Before public void setUp() {
    TestSetUp holder = new TestSetUp();
    testConsole = holder.getConsole1();
  }

  /**
   * Test mkdir's ability to make a directory in the current working directory
   */
  @Test public void testSimpleMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] = {"mkdir", "newDirectory"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls
    OutputQueue lsOutput = testLs.getOutputQueue();

    // then get the actual output
    String actual = lsOutput.getAllOutput();

    // then make the expected output
    String expected = "one two three fileWithContents newDirectory\n\n";

    // then check if the outputs are the same
    assertEquals(expected, actual);
  }

  /**
   * Test mkdir's ability to make a directory via relative path
   */
  @Test public void testRelativeMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] = {"mkdir", "one/level2one/newDirectory"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls", "one/level2one"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls
    OutputQueue lsOutput = testLs.getOutputQueue();

    // then get the actual output
    String actual = lsOutput.getAllOutput();

    // then make the expected output
    String expected = "one/level2one:\n";
    expected += "level3one level3two newDirectory\n\n";

    // then check if the outputs are the same
    assertEquals(expected, actual);
  }

  /**
   * Test mkdir's ability to make a directory via absolute path
   */
  @Test public void testAbsoluteMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] = {"mkdir", "/two/level2five/newDirectory"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls", "/two/level2five"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls
    OutputQueue lsOutput = testLs.getOutputQueue();

    // then get the actual output
    String actual = lsOutput.getAllOutput();

    // then make the expected output
    String expected = "/two/level2five:\n";
    expected += "newDirectory\n\n";

    // then check if the outputs are the same
    assertEquals(expected, actual);
  }

  /**
   * Test mkdir's ability to make multiple directories
   */
  @Test public void testMultipleMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] =
        {"mkdir", "one/newDirectory", "one/anotherDirectory"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls", "one"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls
    OutputQueue lsOutput = testLs.getOutputQueue();

    // then get the actual output
    String actual = lsOutput.getAllOutput();

    // then make the expected output
    String expected = "one:\n";
    expected += "level2one level2two level2three someFile newDirectory "
        + "anotherDirectory\n\n";

    // then check if the outputs are the same
    assertEquals(expected, actual);
  }

  /**
   * Test mkdir's ability to recognize an already made file system object,
   * produce an error, but continue to make directories
   */
  @Test public void testMadeFSOForMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] = {"mkdir", "one/newDirectory", "fileWithContents",
        "one/anotherDirectory"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls", "one"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls and mkdir
    OutputQueue lsOutput = testLs.getOutputQueue();
    OutputQueue mkdirOutput = testMkdir.getOutputQueue();

    // then get the actual standard output and standard error
    String actualStdOut = lsOutput.getOnlyStdOut();
    String actualStdErr = mkdirOutput.getOnlyStdErr();

    // then make the expected output
    String expectedStdOut = "one:\n";
    expectedStdOut += "level2one level2two level2three someFile newDirectory "
        + "anotherDirectory\n\n";

    // then make a expected standard error
    String expectedStdErr = "filesystem.DuplicateFileSystemObjectNameError:";
    expectedStdErr += " The File System Object 'fileWithContents' already";
    expectedStdErr += " exists in this directory\n";

    // then check if the standard outputs are the same
    assertEquals(expectedStdOut, actualStdOut);
    // as well as the standard errors
    assertEquals(expectedStdErr, actualStdErr);
  }

  /**
   * Test mkdir's ability to determine invalid paths but continue to make
   * directories
   */
  @Test public void testInvalidPathMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] = {"mkdir", "notAPath/newDirectory", "one/new"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls", "one"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls
    OutputQueue lsOutput = testLs.getOutputQueue();
    // as well as the output queue of mkdir
    OutputQueue mkdirOutput = testMkdir.getOutputQueue();

    // then get the actual standard output as well as the std err
    String actualStdOut = lsOutput.getAllOutput();
    String actualStdErr = mkdirOutput.getOnlyStdErr();

    // then make the expected standard output
    String expectedStdOut = "one:\n";
    expectedStdOut += "level2one level2two level2three someFile new\n\n";

    // then make the expected standard error
    String expectedStdErr =
        "filesystem.InvalidPathError: Path 'notAPath/' " + "does not exist\n";

    // check if we were able to make a directory past the error
    assertEquals(expectedStdOut, actualStdOut);
    assertEquals(expectedStdErr, actualStdErr);
  }

  /**
   * Test mkdir's ability to determine bad directory names but continue to make
   * directories
   */
  @Test public void testBadNameMkdir() {
    // we make a mkdir array to replicate the mkdir command
    String mkdirArray[] = {"mkdir", "Bad!! Directory!!", "one/new"};
    // then initialize our mkdir variable
    testMkdir = new Mkdir(testConsole, mkdirArray);
    // and execute the command
    testMkdir.execute();

    // now we want to ls in the current working directory
    String lsArray[] = {"ls", "one"};
    // initialize the ls variable
    testLs = new Ls(testConsole, lsArray);
    // execute the command
    testLs.execute();

    // get the output queue of ls
    OutputQueue lsOutput = testLs.getOutputQueue();
    // as well as the output queue of mkdir
    OutputQueue mkdirOutput = testMkdir.getOutputQueue();

    // then get the actual standard output as well as the std err
    String actualStdOut = lsOutput.getAllOutput();
    String actualStdErr = mkdirOutput.getOnlyStdErr();

    // then make the expected standard output
    String expectedStdOut = "one:\n";
    expectedStdOut += "level2one level2two level2three someFile new\n\n";

    // then make the expected standard error
    String expectedStdErr = "filesystem.BadFileSystemObjectNameError: "
        + "FileSystemObject names can only be alphanumeric\n";

    // check if we were able to make a directory past the error
    assertEquals(expectedStdOut, actualStdOut);
    assertEquals(expectedStdErr, actualStdErr);
  }


}
