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

import filesystem.BadFileSystemObjectNameError;
import filesystem.File;
import filesystem.FileSystemObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileSystemObjectTest {

  /**
   * Test FileSystemObject's ability to make a FileSystemObject with a valid
   * name
   */
  @Test public void testMakeFso() {
    try {
      FileSystemObject testFso = new FileSystemObject("newFso");
      // then get the fso's name
      String actual = testFso.getFileSystemObjectName();
      // then make an expected string
      String expected = "newFso";

      // compare the two strings
      assertEquals(expected, actual);
    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, the behaviour is unexpected
      assertEquals("unexpected", e.toString());
    }
  }

  /**
   * Test FileSystemObject's ability to not allow users to make a
   * FileSystemObject with a name that has a space
   */
  @Test public void testDontMakeFsoSpace() {
    try {
      // make a bad file system object name
      FileSystemObject testFso = new FileSystemObject("newFso yeah");

      // if this somehow continues, we know this is an invalid test case
      assertEquals("Invalid", "test case");
    } catch (BadFileSystemObjectNameError e) {
      // this catch statement should run
      // we get the actual string
      String actual = e.toString();
      // then make the expected string
      String expected = "filesystem.BadFileSystemObjectNameError:"
          + " FileSystemObject names can only be alphanumeric";
      // then we test the two strings
      assertEquals(actual, expected);
    }
  }

  /**
   * Test FileSystemObject's ability to not allow users to make a
   * FileSystemObject with a name that has a non alpha numeric character
   */
  @Test public void testDontMakeFsoNonAlpha() {
    try {
      // make a bad file system object name
      FileSystemObject testFso = new FileSystemObject("newFso!yeah");

      // if this somehow continues, we know this is an invalid test case
      assertEquals("Invalid", "test case");
    } catch (BadFileSystemObjectNameError e) {
      // this catch statement should run
      // we get the actual string
      String actual = e.toString();
      // then make the expected string
      String expected = "filesystem.BadFileSystemObjectNameError:"
          + " FileSystemObject names can only be alphanumeric";
      // then we test the two strings
      assertEquals(actual, expected);
    }
  }

  /**
   * Test FileSystemObject's ability to not allow users to make a
   * FileSystemObject with a name that has a space (via setter)
   */
  @Test public void testDontMakeFsoSpaceSetter() {
    try {
      // make a bad file system object name
      FileSystemObject testFso = new FileSystemObject("newFso");
      // change the name of the fso
      testFso.setFileSystemObjectName("bad fso name");

      // if this somehow continues, we know this is an invalid test case
      assertEquals("Invalid", "test case");
    } catch (BadFileSystemObjectNameError e) {
      // this catch statement should run
      // we get the actual string
      String actual = e.toString();
      // then make the expected string
      String expected = "filesystem.BadFileSystemObjectNameError:"
          + " FileSystemObject names can only be alphanumeric";
      // then we test the two strings
      assertEquals(actual, expected);
    }
  }

  /**
   * Test FileSystemObject's ability to not allow users to make a
   * FileSystemObject with a name that has a non alpha numeric character
   * (via setter)
   */
  @Test public void testDontMakeFsoNonAlphaSetter() {
    try {
      // make a bad file system object name
      FileSystemObject testFso = new FileSystemObject("newFso");
      // change the name of the fso
      testFso.setFileSystemObjectName("Non!Alpha");

      // if this somehow continues, we know this is an invalid test case
      assertEquals("Invalid", "test case");
    } catch (BadFileSystemObjectNameError e) {
      // this catch statement should run
      // we get the actual string
      String actual = e.toString();
      // then make the expected string
      String expected = "filesystem.BadFileSystemObjectNameError:"
          + " FileSystemObject names can only be alphanumeric";
      // then we test the two strings
      assertEquals(actual, expected);
    }
  }

  /**
   * Test FileSystemObject's ability to determine its parents
   */
  @Test public void testParents(){
    try {
      // make two file system objects, one being the parent the other being
      // a child
      FileSystemObject parentFso = new FileSystemObject("pFso");
      FileSystemObject childFso = new FileSystemObject("cFso");

      // set the child's parent to be the parentFso
      childFso.setParent(parentFso);
      // then we want to get the "parent" fso
      FileSystemObject actual = childFso.getParent();
      // and we want to test if the parent is what is indicated
      FileSystemObject expected = parentFso;

      // then we test these objects via references
      assertEquals(expected, actual);

    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, then the behaviour is unexpected
      assertEquals("unexpected", e.toString());
    }
  }
}
