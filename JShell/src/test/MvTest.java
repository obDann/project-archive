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
import commands.Mv;
import commands.Tree;
import console.Console;
import console.OutputQueue;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.BadFileSystemObjectNameError;
import filesystem.Directory;
import filesystem.DuplicateFileSystemObjectNameError;
import filesystem.InvalidPathError;
import filesystem.UnexpectedFsoError;

public class MvTest {
  Mv myMv;
  Console c;
  FileSystem fs;
  Tree myTree;
  String[] treeCommand = {"tree"};
  OutputQueue cmdOPQ;
  TestSetUp su;

  @Before
  public void setup() {
    // Set up for file system
    su = new TestSetUp();
    c = su.getConsole1();
    fs = c.getFileSystem();
    myTree = new Tree(c, treeCommand);
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

  @Test
  public void testMvFileToFileNonOverwrite() {
    OutputStream os = header();
    String[] MvCommand = {"mv", "/fileWithContents", "/one/level2one"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    myTree.execute();
    String expected = "/\r\n" + "    one\r\n" + "        level2one\r\n"
        + "            level3one\r\n" + "                level4\r\n"
        + "            level3two\r\n" + "            fileWithContents\r\n"
        + "        level2two\r\n" + "        level2three\r\n"
        + "        someFile\r\n" + "    two\r\n" + "        level2four\r\n"
        + "        level2five\r\n" + "    three\r\n";
    assertEquals(expected, os.toString());
    footer();
  }

  @Test
  public void testMvFileToFileOverwrite2() {
    OutputStream os = header();
    try {
      File someFile = new File("fileWithContents", "overwrite");
      ((Directory) fs.getFsoByPath("/one")).storeNewFileSystemObject(someFile);
      String[] MvCommand = {"mv", "/fileWithContents", "/one/fileWithContents"};
      myMv = new Mv(c, MvCommand);
      cmdOPQ = myMv.getOutputQueue();
      myMv.execute();
      myTree.execute();

      String expected = "/\r\n" + "    one\r\n" + "        level2one\r\n"
          + "            level3one\r\n" + "                level4\r\n"
          + "            level3two\r\n" + "        level2two\r\n"
          + "        level2three\r\n" + "        someFile\r\n"
          + "        fileWithContents\r\n" + "    two\r\n"
          + "        level2four\r\n" + "        level2five\r\n"
          + "    three\r\n";
      assertEquals(expected, os.toString());

      assertEquals(fs.getFileByPath("/one/fileWithContents").getFileContents(),
          "This file has contents");
    } catch (UnexpectedFsoError | InvalidPathError
        | BadFileSystemObjectNameError | DuplicateFileSystemObjectNameError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    footer();
  }

  @Test
  public void testMvFileToFileOverwrite() {
    OutputStream os = header();
    try {
      File someFile = new File("fileWithContents", "overwrite");
      ((Directory) fs.getFsoByPath("/one")).storeNewFileSystemObject(someFile);
      String[] MvCommand = {"mv", "/fileWithContents", "/one"};
      myMv = new Mv(c, MvCommand);
      cmdOPQ = myMv.getOutputQueue();
      myMv.execute();
      myTree.execute();

      String expected = "/\r\n" + "    one\r\n" + "        level2one\r\n"
          + "            level3one\r\n" + "                level4\r\n"
          + "            level3two\r\n" + "        level2two\r\n"
          + "        level2three\r\n" + "        someFile\r\n"
          + "        fileWithContents\r\n" + "    two\r\n"
          + "        level2four\r\n" + "        level2five\r\n"
          + "    three\r\n";
      assertEquals(expected, os.toString());

      assertEquals(fs.getFileByPath("/one/fileWithContents").getFileContents(),
          "This file has contents");
    } catch (UnexpectedFsoError | InvalidPathError
        | BadFileSystemObjectNameError | DuplicateFileSystemObjectNameError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    footer();
  }


  @Test
  public void testMvFileToDir() {
    OutputStream os = header();
    String[] MvCommand =
        {"mv", "/fileWithContents", "/one/level2one/level3one"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    myTree.execute();
    String expected = "/\r\n" + "    one\r\n" + "        level2one\r\n"
        + "            level3one\r\n" + "                level4\r\n"
        + "                fileWithContents\r\n" + "            level3two\r\n"
        + "        level2two\r\n" + "        level2three\r\n"
        + "        someFile\r\n" + "    two\r\n" + "        level2four\r\n"
        + "        level2five\r\n" + "    three\r\n";
    assertEquals(expected, os.toString());
    footer();
  }

  @Test
  public void testMvRoot() {
    OutputStream os = header();
    String expected = "mv: can not move root directory\n";
    String[] MvCommand = {"mv", "/", "/one/someFile"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test
  public void testMvDirToChild() {
    OutputStream os = header();
    String expected = "mv: /one: can not move to child\n";
    String[] MvCommand = {"mv", "/one", "/one/someFile"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test
  public void testMvDirToItself() {
    OutputStream os = header();
    String expected = "mv: /one: can not copy to itself\n";
    String[] MvCommand = {"mv", "/one", "/one"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test
  public void testMvDirToFile() {
    OutputStream os = header();
    String expected = "mv: can not copy a directory to a file\n";
    String[] MvCommand = {"mv", "/one", "/fileWithContents"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }

  @Test
  public void testMvDirToDir() {
    OutputStream os = header();
    String[] MvCommand = {"mv", "/one", "/two"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    myTree.execute();
    String expected = "/\r\n" + "    two\r\n" + "        level2four\r\n"
        + "        level2five\r\n" + "        one\r\n"
        + "            level2one\r\n" + "                level3one\r\n"
        + "                    level4\r\n" + "                level3two\r\n"
        + "            level2two\r\n" + "            level2three\r\n"
        + "            someFile\r\n" + "    three\r\n"
        + "    fileWithContents\r\n";
    assertEquals(expected, os.toString());
    footer();
  }


  @Test
  public void testInvalidArgument1() {
    OutputStream os = header();
    String expected = "Invalid Command Use: Please see the man page of mv\n";
    String[] MvCommand = {"mv", "/one/someFile"};
    myMv = new Mv(c, MvCommand);
    cmdOPQ = myMv.getOutputQueue();
    myMv.execute();
    assertEquals(expected, cmdOPQ.getOnlyStdErr());
    footer();
  }



}
