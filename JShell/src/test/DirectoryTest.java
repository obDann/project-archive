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

import filesystem.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DirectoryTest {

  /**
   * Test directory's ability to make a directory with a name
   */
  @Test public void testSimpleDir() {
    // make a directory
    try {
      Directory testDirectory = new Directory("aDir");
      // get the directory name
      String actual = testDirectory.getFileSystemObjectName();
      // make the expected string
      String expected = "aDir";

      // then test the two strings
      assertEquals(expected, actual);
    } catch (BadFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    }
  }

  /**
   * Test directory's ability to make an invalid directory
   */
  @Test public void testInvalidDir() {
    // make a directory
    try {
      Directory testDirectory = new Directory("Invalid Dir");

      // if this line continues, then the behaviour is unexpected
      assertEquals("unexpected", "behaviour");
    } catch (BadFileSystemObjectNameError e) {
      // this catch statement should run
      // make the actual string
      String actual = e.toString();
      // then make the expected string
      String expected = "filesystem.BadFileSystemObjectNameError:";
      expected += " FileSystemObject names can only be alphanumeric";
      assertEquals(expected, actual);
    }
  }

  /**
   * Test Directory's ability to make a root directory named "/"
   */
  @Test public void testRootDir() {
    // create the root directory
    Directory rootDir = Directory.makeRoot();
    // get the name of the directory
    String actual = rootDir.getFileSystemObjectName();
    // make an expected string of the directory name
    String expected = "/";
    // test the two strings
    assertEquals(expected, actual);
  }

  /**
   * Test Directory's ability to store different File System Objects
   * and get file system objects
   */
  @Test public void testStoreFSOs() {
    try {
      // create a parent directory
      Directory parentDirectory = new Directory("parent");
      // create different file system objects
      FileSystemObject childA = new FileSystemObject("childA");
      File childB = new File("childB");
      Directory childC = new Directory("childC");

      // make an array list for the children
      ArrayList<FileSystemObject> expectedChildren = new ArrayList<>();
      expectedChildren.add(childA);
      expectedChildren.add(childB);
      expectedChildren.add(childC);

      // add the FSOs
      parentDirectory.storeNewFileSystemObject(childA);
      parentDirectory.storeNewFileSystemObject(childB);
      parentDirectory.storeNewFileSystemObject(childC);

      // get the children of the parent
      ArrayList<FileSystemObject> actualChildren =
          parentDirectory.getChildren();

      // then check if the children are the same (by element references)
      assertEquals(expectedChildren, actualChildren);
    } catch (BadFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    } catch (DuplicateFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    }
  }

  /**
   * Test Directory's ability to determine if there would be children with
   * the same name
   */
  @Test public void testStoreFSODuplicate() {
    try {
      // create a parent directory
      Directory parentDirectory = new Directory("parent");
      // create different file system objects
      FileSystemObject childA = new FileSystemObject("childA");
      File childB = new File("childB");
      Directory childC = new Directory("childC");
      File duplicate = new File("childB");

      // add the FSOs
      parentDirectory.storeNewFileSystemObject(childA);
      parentDirectory.storeNewFileSystemObject(childB);
      parentDirectory.storeNewFileSystemObject(childC);
      parentDirectory.storeNewFileSystemObject(duplicate);

      // if a catch does not occur, then this is uncharacteristic Directory's
      // behaviour
      assertEquals("unexpected", "behaviour");
    } catch (BadFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    } catch (DuplicateFileSystemObjectNameError e) {
      // this catch statement should be ran, we make the actual string
      String actual = e.toString();
      // and we make the expected string
      String expected = "filesystem.DuplicateFileSystemObjectNameError: ";
      expected += "The File System Object 'childB' already exists in this "
          + "directory";
      // then we compare the two strings
      assertEquals(expected, actual);
    }
  }

  /**
   * Test Directory's ability to single out a child by a string by a file
   * system object name
   */
  @Test public void testGetChildByString() {
    try {
      // create a parent directory
      Directory parentDirectory = new Directory("parent");
      // create different file system objects
      FileSystemObject childA = new FileSystemObject("childA");
      File childB = new File("childB");
      Directory childC = new Directory("childC");

      // add the FSOs
      parentDirectory.storeNewFileSystemObject(childA);
      parentDirectory.storeNewFileSystemObject(childB);
      parentDirectory.storeNewFileSystemObject(childC);

      // get childB as it is the "actual" file in the directory
      FileSystemObject actual = parentDirectory.getChild("childB");
      // set the expected fso
      File expected = childB;

      // then we want to compare the two fsos' references
      assertEquals(expected, actual);
    } catch (BadFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    } catch (DuplicateFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    } catch (NonExistantChildError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    }
  }

  /**
   * Test Directory's ability to "get" a child that is not there
   */
  @Test public void testGetNonExistantChild() {
    // make some directory
    try {
      Directory testDirectory = new Directory("testDir");

      // we want to get a child that's not there (as there are no children
      // in the new directory we made)
      FileSystemObject makeError = testDirectory.getChild("NonExistant");

      // if for some reason, if the catches aren't being ran, it is
      // unexpected behaviour for directory
      assertEquals("unexpected", "behaviour");
    } catch (BadFileSystemObjectNameError e) {
      // the behaviour of directory should not allow this catch to run
      assertEquals("unexpected", e.toString());
    } catch (NonExistantChildError e) {
      // this catch should run, so we make our actual string
      String actual = e.toString();
      // then we make an expected string
      String expected = "filesystem.NonExistantChildError: The File System "
          + "Object 'NonExistant' doesn't exist";

      // we compare the two strings
      assertEquals(expected, actual);
    }
  }
}
