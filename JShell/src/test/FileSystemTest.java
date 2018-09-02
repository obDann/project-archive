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
import console.Console;
import filesystem.InvalidPathError;
import filesystem.UnexpectedFsoError;

public class FileSystemTest {
  // Set up for file system
  TestSetUp su;
  Console c;
  filesystem.FileSystem fs;
  
  @Before
  public void setup() {
    su = new TestSetUp();
    c = su.getConsole1();
    fs = c.getFileSystem();
  }

  @Test public void testIsExistPathTrue1() {
    boolean expected = true;
    boolean actual = fs.isExistPath("level4");
    assertEquals(expected, actual);
  }

  @Test public void testIsExistPathTrue2() {
    boolean expected = true;
    boolean actual = fs.isExistPath("/one/level2one/");
    assertEquals(expected, actual);
  }

  @Test public void testIsExistPathFalse() {
    boolean expected = false;
    boolean actual = fs.isExistPath("L");
    assertEquals(expected, actual);
  }

  @Test public void testPrintCurrentPathRoot() {
    String expected = "/";
    String actual = fs.printCurrentPath();
    assertEquals(expected, actual);
  }

  @Test public void testGoToAbsolutePath() {
    String expected = "/one";
    fs.goToPath("/one");
    String actual = fs.printCurrentPath();
    assertEquals(expected, actual);
  }

  @Test public void testGoToRelativePath() {
    String expected = "/one/level2one";
    fs.goToPath("/one");
    fs.goToPath("level2one/");
    String actual = fs.printCurrentPath();
    assertEquals(expected, actual);
  }

  @Test public void testFindObjExsitObj() {
    String expected = "level4";
    fs.goToPath("/");
    String actual =
        fs.findObj(fs.getCurrentPath(), "level4").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testFindObjNotExistObj() {
    String expected = null;
    fs.goToPath("/one");
    String actual =
        fs.findObj(fs.getCurrentPath(), "k").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testGetDirByRelativePath()
      throws UnexpectedFsoError, InvalidPathError {
    String expected = "level2one";
    fs.goToPath("/one");
    String actual =
        fs.getDirectoryByPath("level2one").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testGetDirByAbsolutePath()
      throws UnexpectedFsoError, InvalidPathError {
    String expected = "level2one";
    String actual =
        fs.getDirectoryByPath("/one/level2one").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testGetFileByRelativePath()
      throws UnexpectedFsoError, InvalidPathError {
    String expected = "someFile";
    fs.goToPath("/one");
    String actual = fs.getFileByPath("someFile").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testGetFileByAbsolutePath()
      throws UnexpectedFsoError, InvalidPathError {
    String expected = "someFile";
    String actual =
        fs.getFileByPath("/one/someFile").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testGetFsoByRelativePath()
      throws UnexpectedFsoError, InvalidPathError {
    String expected = "three";
    fs.goToPath("/");
    String actual = fs.getFsoByPath("three").getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  @Test public void testGetFsoByAbsolutePath()
      throws UnexpectedFsoError, InvalidPathError {
    String expected = "three";
    String actual = fs.getFsoByPath("/three").getFileSystemObjectName();
    assertEquals(expected, actual);
  }
}
