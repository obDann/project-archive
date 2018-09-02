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

import commands.Cat;
import console.Console;
import console.OutputQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CatTest {

  // then we want to get a testing console
  private Console testConsole;
  private Cat testCat;

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
   * Test cat's ability to read from a relative path
   */
  @Test public void testSimpleCatRelative() {
    // make a cat array to replicate the cat command
    String catArray[] = {"cat", "./one/./someFile"};
    // then let's make a cat command and inject the console
    testCat = new Cat(testConsole, catArray);
    // execute testCat
    testCat.execute();

    // then let's get its output
    OutputQueue catOPQ = testCat.getOutputQueue();
    String actual = catOPQ.getAllOutput();

    // then we make an expected string
    String expected = "more contents\n\n\n";

    assertEquals(expected, actual);
  }

  /**
   * Test cat's ability to read from an absolute path
   */
  @Test public void testSimpleCatAbsolute() {
    // make a cat array to replicate the cat command
    String catArray[] = {"cat", "/one/someFile"};
    // then let's make a cat command and inject the console
    testCat = new Cat(testConsole, catArray);
    // execute testCat
    testCat.execute();

    // then let's get its output
    OutputQueue catOPQ = testCat.getOutputQueue();
    String actual = catOPQ.getAllOutput();

    // then we make an expected string
    String expected = "more contents\n\n\n";

    assertEquals(expected, actual);
  }

  /**
   * Test cat's ability to read more than one file
   */
  @Test public void testCatMultiple() {
    // make a cat array to replicate the cat command
    String catArray[] = {"cat", "/one/someFile", "fileWithContents"};
    // then let's make a cat command and inject the console
    testCat = new Cat(testConsole, catArray);
    // execute testCat
    testCat.execute();

    // then let's get its output
    OutputQueue catOPQ = testCat.getOutputQueue();
    String actual = catOPQ.getAllOutput();

    // then we make an expected string
    String expected = "more contents\n\n\n";
    expected += "This file has contents\n\n\n";

    assertEquals(expected, actual);
  }

  /**
   * Test cat's ability to recognize a directory
   */
  @Test public void testCatRecognizeDir() {
    // make a cat array to replicate the cat command
    String catArray[] =
        {"cat", "/one/someFile", "one/level2one", "fileWithContents"};
    // then let's make a cat command and inject the console
    testCat = new Cat(testConsole, catArray);
    // execute testCat
    testCat.execute();

    // then let's get its output
    OutputQueue catOPQ = testCat.getOutputQueue();
    String actualStdOut = catOPQ.getOnlyStdOut();
    String actualStdErr = catOPQ.getOnlyStdErr();

    // then we make an expected stdOut
    String expectedStdOut = "more contents\n\n\n";
    expectedStdOut += "This file has contents\n\n\n";
    // and its expected stdErr
    String expectedStdErr = "cat: 'one/level2one' is a Directory\n\n\n";

    assertEquals(expectedStdOut, actualStdOut);
    assertEquals(expectedStdErr, actualStdErr);
  }


  /**
   * Test cat's ability to recognize an invalid path
   */
  @Test public void testCatRecognizeBadPath() {
    // make a cat array to replicate the cat command
    String catArray[] =
        {"cat", "/one/someFile", "notAFile", "fileWithContents"};
    // then let's make a cat command and inject the console
    testCat = new Cat(testConsole, catArray);
    // execute testCat
    testCat.execute();

    // then let's get its output
    OutputQueue catOPQ = testCat.getOutputQueue();
    String actualStdOut = catOPQ.getOnlyStdOut();
    String actualStdErr = catOPQ.getOnlyStdErr();

    // then we make an expected stdOut
    String expectedStdOut = "more contents\n\n\n";
    expectedStdOut += "This file has contents\n\n\n";
    // and its expected stdErr
    String expectedStdErr = "cat: 'notAFile' is an invalid path\n\n\n";

    assertEquals(expectedStdOut, actualStdOut);
    assertEquals(expectedStdErr, actualStdErr);
  }

  /**
   * Test if cat recognizes if its not being used properly
   */
  @Test public void testImproperUse() {
    // make a cat array to replicate the cat command
    String catArray[] = {"cat"};
    // then let's make a cat command and inject the console
    testCat = new Cat(testConsole, catArray);
    // execute testCat
    testCat.execute();

    // then let's get its output
    OutputQueue catOPQ = testCat.getOutputQueue();
    String actual = catOPQ.getOnlyStdErr();

    // then make an expected string
    String expected =
        "commands.InvalidCmdUseError: Please see the man page " + "of cat\n";
    assertEquals(expected, actual);
  }
}
