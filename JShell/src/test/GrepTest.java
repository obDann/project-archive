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
import commands.Grep;
import console.Console;
import console.OutputQueue;

public class GrepTest {

  Console console;
  Grep grep;

  @Before
  public void setUp() {
    TestSetUp holder = new TestSetUp();
    console = holder.getConsole1();
  }

  /**
   * Test grep's ability to match regex with lines in a file
   */
  @Test
  public void testGrepFileNoR() {
    String[] command = {"grep", "file", "fileWithContents"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.replace("\n", "");
    String expected = "This file has contents";
    assertEquals(expected, actual);
  }

  /**
   * Test grep's ability to match regex with lines in a file with -R given
   */
  @Test
  public void testGrepFileWithR() {
    String[] command = {"grep", "file", "fileWithContents"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.replace("\n", "");
    String expected = "This file has contents";
    assertEquals(expected, actual);
  }

  /**
   * Test grep's ability to match regex with lines in all files of a directory
   */
  @Test
  public void testGrepDirectory() {
    String[] command = {"grep", "-R", "contents", "/"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.trim();
    String expected = "/one/someFile:more contents\n"
        + "/fileWithContents:This file has contents";
    assertEquals(expected, actual);
  }

  /**
   * Test grep's ability to catch error when directory path is given but -R is
   * not given
   */
  @Test
  public void testGrepDirectoryNoR() {
    String[] command = {"grep", "contents", "/"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.trim();
    String expected = "commands.MissingRException: -R is required if path is a"
        + " directory path";
    assertEquals(expected, actual);
  }

  /**
   * Test grep's ability to catch error when invalid number of arguments is
   * given
   */
  @Test
  public void testGrepLessThanThreeArguments() {
    String[] command = {"grep"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.trim();
    String expected =
        "commands.InvalidNumberOfArgumentsException: grep requires a"
            + " minimum of 3 user inputs";
    assertEquals(expected, actual);
  }

  /**
   * Test grep's ability to catch error when a path that does not exist is
   * given
   */
  @Test
  public void testGrepInvalidPath() {
    String[] command = {"grep", "contents", "somePath"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.trim();
    String expected =
        "commands.PathDoesNotExistException: path does not exist in"
            + " filesystem";
    assertEquals(expected, actual);
  }

  /**
   * Test grep's ability output correct message when no lines of files in a
   * directory matches the regex
   */
  @Test
  public void testGrepNoLinesMatchRegex() {
    String[] command = {"grep", "-R", "regex", "/"};
    grep = new Grep(console, command);
    grep.execute();
    OutputQueue grepOPQ = grep.getOutputQueue();
    String actual = grepOPQ.getAllOutput();
    actual = actual.trim();
    String expected = "No lines match the regex";
    assertEquals(expected, actual);
  }

}
