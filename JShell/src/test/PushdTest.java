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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Pushd;
import console.Console;

public class PushdTest {
  Console console;
  Pushd pushd;

  @Before
  public void setUp() {
    TestSetUp holder = new TestSetUp();
    console = holder.getConsole1();
  }

  /**
   * Test pushd's ability to push pwd given a directory's name
   */
  @Test
  public void testPushdWithDirectoryName() {
    String[] command = {"pushd", "one"};
    pushd = new Pushd(console, command);
    pushd.execute();
    String expected = "/";
    String actual = console.getFileSystem().getStack().getStack().get(0)
        .getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  /**
   * Test pushd's ability to push pwd given an absolute path
   */
  @Test
  public void testPushdWithAbsolutePath() {
    String[] command = {"pushd", "/one"};
    pushd = new Pushd(console, command);
    pushd.execute();
    String expected = "/";
    String actual = console.getFileSystem().getStack().getStack().get(0)
        .getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  /**
   * Test pushd's ability to push pwd given a relative path
   */
  @Test
  public void testPushdWithRelativePath() {
    String[] command = {"pushd", "one/level2one"};
    pushd = new Pushd(console, command);
    pushd.execute();
    String expected = "/";
    String actual = console.getFileSystem().getStack().getStack().get(0)
        .getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  /**
   * Test pushd's ability to push pwd given no path or directory name
   */
  @Test
  public void testPushdWithNoInput() {
    String[] command = {"pushd"};
    pushd = new Pushd(console, command);
    pushd.execute();
    String expected =
        "commands.InvalidCmdUseError: Please see man page for pushd."
        + " Please see man page for pushd";
    String actual = pushd.getOutputQueue().getAllOutput();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

}
