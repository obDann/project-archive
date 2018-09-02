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

import commands.Cd;
import commands.Pwd;
import console.Console;
import console.OutputQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CdTest {
  // then we want to get a testing console
  private Console testConsole;
  //we want to make a testCd
  private Cd testCd;
  // and we want a pwd class, but that's not the main focus
  private Pwd helperPwd;

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
   * Test Pwd's ability to change directories through a relative path
   */
  @Test public void testRelativePathOnce() {
    // make a Cd array
    String cdArray[] = {"cd", "two/level2four/"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();

    // then we want to look at the current working directory, so we make a
    // pwd array
    String pwdArray[] = {"pwd"};
    // initialize the helperPwd
    helperPwd = new Pwd(testConsole, pwdArray);
    // execute the pwd
    helperPwd.execute();

    // then we want the get Pwd's output for the string
    OutputQueue helpOPQ = helperPwd.getOutputQueue();
    // get all standard out, so this will be the actual value
    String actual = helpOPQ.getOnlyStdOut();

    // then state our expected string
    String expected = "/two/level2four\n";

    // then test our strings
    assertEquals(expected, actual);
  }

  /**
   * Test Pwd's ability to change directories through a relative path
   * (changing directories twice)
   */
  @Test public void testRelativePathTwice() {
    // make a Cd array
    String cdArray[] = {"cd", "one"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();

    // re-initialize our cd class, but let's make a new array
    String newCdArray[] = {"cd", "level2one"};
    testCd = new Cd(testConsole, newCdArray);
    testCd.execute();

    // then we want to look at the current working directory, so we make a
    // pwd array
    String pwdArray[] = {"pwd"};
    // initialize the helperPwd
    helperPwd = new Pwd(testConsole, pwdArray);
    // execute the pwd
    helperPwd.execute();

    // then we want the get Pwd's output for the string
    OutputQueue helpOPQ = helperPwd.getOutputQueue();
    // get all standard out, so this will be the actual value
    String actual = helpOPQ.getOnlyStdOut();

    // then state our expected string
    String expected = "/one/level2one\n";

    // then test our strings
    assertEquals(expected, actual);
  }

  /**
   * Test Cd's ability to change directories through an absolute path
   */
  @Test public void testAbsolutePath() {
    // make a Cd array
    String oldCdArray[] = {"cd", "one"};
    // initialize our cd class
    testCd = new Cd(testConsole, oldCdArray);
    // execute the cd
    testCd.execute();

    // make a Cd array
    String cdArray[] = {"cd", "/two/level2four/"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();

    // then we want to look at the current working directory, so we make a
    // pwd array
    String pwdArray[] = {"pwd"};
    // initialize the helperPwd
    helperPwd = new Pwd(testConsole, pwdArray);
    // execute the pwd
    helperPwd.execute();

    // then we want the get Pwd's output for the string
    OutputQueue helpOPQ = helperPwd.getOutputQueue();
    // get all standard out, so this will be the actual value
    String actual = helpOPQ.getOnlyStdOut();

    // then state our expected string
    String expected = "/two/level2four\n";

    // then test our strings
    assertEquals(expected, actual);
  }

  /**
   * Test Cd's ability to recognize an invalid path
   */
  @Test public void testInvalidPath() {
    // make a Cd array
    String cdArray[] = {"cd", "badPath"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();

    // then we want the get Cd's output for the error
    OutputQueue helpOPQ = testCd.getOutputQueue();
    // get the standard error
    String actual = helpOPQ.getOnlyStdErr();

    // then state our expected string
    String expected =
        "filesystem.InvalidPathError: Path 'badPath' does not exist\n";

    // then test our strings
    assertEquals(expected, actual);
  }

  /**
   * Test Cd's ability to produce an error when requested to change the
   * current working directory into a file
   */
  @Test public void testCdIntoRelativeFile() {
    // make a Cd array which will produce an error for files
    String cdArray[] = {"cd", "fileWithContents"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();

    // then we want the get Cd's output for the error
    OutputQueue helpOPQ = testCd.getOutputQueue();
    // get the standard error
    String actual = helpOPQ.getOnlyStdErr();

    // then state our expected string
    String expected = "commands.InvalidCmdUseError: The path "
        + "'fileWithContents' is not a directory\n";

    // then test our strings
    assertEquals(expected, actual);
  }

  /**
   * Test Cd's ability to produce an error when requested to change the
   * current working directory into a file
   */
  @Test public void testCdIntoAbsoluteFile() {
    // make a Cd array which will produce an error for files
    String cdArray[] = {"cd", "/one/someFile"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();

    // then we want the get Cd's output for the error
    OutputQueue helpOPQ = testCd.getOutputQueue();
    // get the standard error
    String actual = helpOPQ.getOnlyStdErr();

    // then state our expected string
    String expected = "commands.InvalidCmdUseError: The path "
        + "'/one/someFile' is not a directory\n";

    // then test our strings
    assertEquals(expected, actual);
  }

  /**
   * Test Cd's ability to produce an error when given multiple paths to
   * change into
   */
  @Test public void testInvalidUse() {
    // make a Cd array
    String cdArray[] = {"cd", "two/level2four/", "one"};
    // initialize our cd class
    testCd = new Cd(testConsole, cdArray);
    // execute the cd
    testCd.execute();


    // then we want the get Cd's output for the error
    OutputQueue helpOPQ = testCd.getOutputQueue();
    // get all standard out, so this will be the actual value
    String actual = helpOPQ.getOnlyStdErr();

    // then state our expected string
    String expected =
        "commands.InvalidCmdUseError: Please see the man page of cd\n";

    // then test our strings
    assertEquals(expected, actual);
  }
}
