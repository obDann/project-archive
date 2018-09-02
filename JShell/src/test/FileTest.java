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
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileTest {

  /**
   * Test File's ability to make a file with a name
   */
  @Test public void testMakeFile() {
    try {
      // make a file
      File testFile = new File("fileName");
      // get the actual file name
      String actual = testFile.getFileName();
      // make the expected file name
      String expected = "fileName";
      // then compare the two
      assertEquals(expected, actual);
    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, then it doesn't work as we expected
      assertEquals("Does not", "work");
    }
  }

  /**
   * Test File's ability to make a file with a name and contents
   */
  @Test public void testMakeFileWithContents() {
    try {
      File testFile = new File("fileName", "Contents");
      // get the actual file name
      String actualName = testFile.getFileName();
      // make the expected file name
      String expectedName = "fileName";

      // get the actual contents
      String actualContents = testFile.getFileContents();
      // make the expected contents
      String expectedContents = "Contents";

      // test the file names
      assertEquals(expectedName, actualName);
      // and the contents
      assertEquals(expectedContents, actualContents);
    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, then it doesn't work as we expected
      assertEquals("Does not", "work");
    }
  }

  /**
   * Test file's ability to append contents
   */
  @Test public void testAppend() {
    try {
      File testFile = new File("fileName", "Contents");
      // what we want to do is append contents
      String addingContents = "More Contents!";
      // we make an expecting string
      String expected = "ContentsMore Contents!";

      // then we append the contents
      testFile.addFileContents(addingContents);
      // then we make an actual string
      String actual = testFile.getFileContents();

      // then we compare the two
      assertEquals(expected, actual);
    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, then it doesn't work as we expected
      assertEquals("Does not", "work");
    }
  }

  /**
   * Test file's ability to overwrite existing contents
   */
  @Test public void testOverwrite() {
    try {
      File testFile = new File("fileName", "Contents");
      // what we want to do is append contents
      String addingContents = "More Contents!";
      // we make an expecting string
      String expected = "More Contents!";

      // then we overwrite the contents
      testFile.overwriteFile(addingContents);
      // then we make an actual string
      String actual = testFile.getFileContents();

      // then we compare the two
      assertEquals(expected, actual);
    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, then it doesn't work as we expected
      assertEquals("Does not", "work");
    }
  }

  /**
   * Test file's ability to determine non=alphanumeric characters in its
   * file name
   */
  @Test public void testNonAlphaName() {
    try {
      // make a "bad file name"
      File someFile = new File("NotValid!txt");

      // if we somehow continue, then this test case is bad
      assertEquals("failed", "test case");
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
   * Test file's ability to determine spaces in its file name
   */
  @Test public void testSpaceName() {
    try {
      // make a "bad file name" with a space
      File someFile = new File("Not Valid");

      // if we somehow continue, then this test case is bad
      assertEquals("failed", "test case");
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
}
