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
import commands.Popd;
import commands.Pushd;
import console.Console;

public class PopdTest {
  Console console;
  Popd popd;
  Pushd pushd;

  @Before
  public void setUp() {
    TestSetUp holder = new TestSetUp();
    console = holder.getConsole1();
  }

  /*
   * Test popd's ability to pop from dir stack and set path from dir stack to
   * pwd
   */
  @Test
  public void testPopdOneItemInStack() {
    String[] pushdCommand = {"pushd", "one"};
    pushd = new Pushd(console, pushdCommand);
    pushd.execute();
    String[] popdCommand = {"popd"};
    popd = new Popd(console, popdCommand);
    popd.execute();
    String expected = "/";
    String actual =
        console.getFileSystem().getCurrentPath().getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  /**
   * Test popd's ability to catch error when pop from empty stack
   */
  @Test
  public void testPopdEmptyStack() {
    String[] popdCommand = {"popd"};
    popd = new Popd(console, popdCommand);
    popd.execute();
    String expected =
        "commands.PopFromEmptyStackError: popd: directory stack empty: popd:"
        + " directory stack empty";
    String actual = popd.getOutputQueue().getAllOutput();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

  /**
   * Test popd's ability to catch error when invalid number of arguments is
   * given
   */
  @Test
  public void testPopdInvalidNumArg() {
    String[] popdCommand = {"popd", "arg2"};
    popd = new Popd(console, popdCommand);
    popd.execute();
    String expected = "popd: requires no user input";
    String actual = popd.getOutputQueue().getAllOutput();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

}
