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

import static org.junit.Assert.*;

public class PwdTest {

  // then we want to get a testing console
  private Console testConsole;
  //we want to make a testPwd
  private Pwd testPwd;
  // and we want a Cd class, but that's not the main focus
  private Cd helperCd;

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
   * Given a file system who's current directory is "/" test pwd to output "/"
   */
  @Test public void testSimplePwd() {
    // make a pwd array
    String pwdArray[] = {"pwd"};
    // initialize the testPwd
    testPwd = new Pwd(testConsole, pwdArray);
    // then execute the pwd
    testPwd.execute();

    // then get its standard output
    OutputQueue holder = testPwd.getOutputQueue();
    String actual = holder.getOnlyStdOut();

    // implement an expected string
    String expected = "/\n";

    // then test it out
    assertEquals(expected, actual);
  }

  /**
   * Test the ability to change directories and have Pwd reflect that the
   * file system did change directories
   */
  @Test public void testCdWithPwd() {

    // first make a Cd array
    String cdArray[] = {"cd", "one/level2one/level3one"};
    // then make a Cd
    helperCd = new Cd(testConsole, cdArray);
    // then execute the Cd
    helperCd.execute();

    // make a pwd array
    String pwdArray[] = {"pwd"};
    // initialize the testPwd
    testPwd = new Pwd(testConsole, pwdArray);
    // then execute the pwd
    testPwd.execute();

    // then get its standard output
    OutputQueue holder = testPwd.getOutputQueue();
    String actual = holder.getOnlyStdOut();

    // implement an expected string
    String expected = "/one/level2one/level3one\n";

    // then test it out
    assertEquals(expected, actual);
  }

  /**
   * Test Cd's ability to output an error
   */
  @Test public void testPwdError() {
    // make a pwd array to cause an error
    String pwdArray[] = {"pwd", "cause an error"};
    // initialize the testPwd
    testPwd = new Pwd(testConsole, pwdArray);
    // then execute the pwd
    testPwd.execute();

    // then get its standard error
    OutputQueue holder = testPwd.getOutputQueue();
    String actual = holder.getOnlyStdErr();

    // implement an expected string
    String expected =
        "commands.InvalidCmdUseError: Please see the man page of pwd\n";
    // then test it out
    assertEquals(expected, actual);
  }
}
