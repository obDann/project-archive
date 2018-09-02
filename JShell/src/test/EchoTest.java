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

import commands.Echo;
import console.Console;
import console.OutputQueue;
import org.junit.*;

import static org.junit.Assert.*;

public class EchoTest {
  // initialize an echo class
  private Echo testEcho;
  // then we want to get a testing console
  private Console testConsole;

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
   * Test standard output
   */
  @Test public void testStandardOutput() {
    // create an echo array
    String echoArray[] = {"echo", "\"hello\""};

    // inject the console to our test echo
    testEcho = new Echo(testConsole, echoArray);
    // then execute
    testEcho.execute();

    // we want to get its output queue
    OutputQueue cmdOPQ = testEcho.getOutputQueue();

    // create an expected string
    String expected = "hello\n";
    // then we want to get only the standard output
    String actual = cmdOPQ.getOnlyStdOut();

    // then run the test
    assertEquals(expected, actual);
  }

  //commands.InvalidCmdUseError: Please see the man page of echo

  /**
   * Test standard Error
   */
  @Test public void testStandardErr() {
    // create an echo array, but leave out one quotation for an error
    String echoArray[] = {"echo", "hello\""};

    // inject the console to our test echo
    testEcho = new Echo(testConsole, echoArray);
    // then execute
    testEcho.execute();

    // we want to get its output queue
    OutputQueue cmdOPQ = testEcho.getOutputQueue();

    // create an expected string
    String expected =
        "commands.InvalidCmdUseError: Please see the man page " + "of echo\n";
    // then we want to get only the standard output
    String actual = cmdOPQ.getOnlyStdErr();

    // then run the test
    assertEquals(expected, actual);
  }

  /**
   * Test standard Error again
   */
  @Test public void testStandardErrAgain() {
    // create an echo array, but leave out the last quotation
    String echoArray[] = {"echo", "\"hello"};

    // inject the console to our test echo
    testEcho = new Echo(testConsole, echoArray);
    // then execute
    testEcho.execute();

    // we want to get its output queue
    OutputQueue cmdOPQ = testEcho.getOutputQueue();

    // create an expected string
    String expected =
        "commands.InvalidCmdUseError: Please see the man page of echo\n";
    // then we want to get only the standard output
    String actual = cmdOPQ.getOnlyStdErr();

    // then run the test
    assertEquals(expected, actual);
  }
}
