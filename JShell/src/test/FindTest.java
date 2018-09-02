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
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import commands.Find;
import console.Console;
import console.OutputQueue;
import filesystem.FileSystem;

public class FindTest {

  // Set up for file system
  TestSetUp su;
  Console c;
  FileSystem fs;
  OutputQueue cmdOPQ;

  @Before
  public void setup() {
    // Set up for file system
    su = new TestSetUp();
    c = su.getConsole1();
    fs = c.getFileSystem();
  }
  
  /**
   * Captures the print statements to the shell
   *
   * @return
   */
  public OutputStream header() {
    OutputStream os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);
    return os;
  }

  /**
   * Restores output to normal
   */
  public void footer() {
    PrintStream originalOut = System.out;
    System.setOut(originalOut);
  }

  @Test public void testOnePathExistDirectory() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/one", "-type", "d", "-name", "\"level4\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "level4\n";
    assertEquals(expected, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test public void testOnePathExistFile() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/one", "-type", "f", "-name", "\"someFile\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "someFile\n";
    assertEquals(expected, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test public void testOnePathNotExistDirectory() {
    OutputStream os = header();
    String[] findCommand = {"find", "/one", "-type", "d", "-name", "\"D\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "find: D : no such Directory in /one\n";
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test public void testOnePathNotExistFile() {
    OutputStream os = header();
    String[] findCommand = {"find", "/one", "-type", "f", "-name", "\"F\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "find: F : no such File in /one\n";
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test public void testTwoPathExistDirectory() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "-type", "d", "-name", "\"level4\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "level4\nlevel4\n";
    assertEquals(expected, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test public void testTwoPathExistFile() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "-type", "f", "-name", "\"someFile\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "someFile\nsomeFile\n";
    assertEquals(expected, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test public void testTwoPathNotExistDirectory() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "-type", "d", "-name", "\"D\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "find: D : no such Directory in /\nfind: D : no such"
        + " Directory in /one\n";
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test public void testTwoPathNotExistFile() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "-type", "f", "-name", "\"F\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "find: F : no such File in /\nfind: F : no such File"
        + " in /one\n";
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test public void testThreePathExistDirectory() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "/", "-type", "d", "-name", "\"level4\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "level4\nlevel4\nlevel4\n";
    assertEquals(expected, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test public void testThreePathExistFile() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "/", "-type", "f", "-name", "\"someFile\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "someFile\nsomeFile\nsomeFile\n";
    assertEquals(expected, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test public void testThreePathNotExistDirectory() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "/", "-type", "d", "-name", "\"D\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "find: D : no such Directory in /\nfind: D :"
        + " no such Directory in /one\nfind: D : no such"
        + " Directory in /\n";
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test public void testThreePathNotExistFile() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one/", "/", "-type", "f", "-name", "\"F\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "find: F : no such File in /\nfind: F : no such"
        + " File in /one/\nfind: F : no such File in /\n";
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    cmdOPQ = myFind.getOutputQueue();
    footer();
  }

  @Test public void testThreePathMixExistDirectory() {
    OutputStream os = header();
    String[] findCommand =
        {"find", "/", "/one", "/two", "-type", "d", "-name", "\"level4\""};
    Find myFind = new Find(c, findCommand);
    cmdOPQ = myFind.getOutputQueue();
    myFind.execute();
    String expected = "level4\nlevel4\nfind: level4 :"
        + " no such Directory in /two\n";
    assertEquals(expected, cmdOPQ.getAllOutput());
    footer();
  }

}
