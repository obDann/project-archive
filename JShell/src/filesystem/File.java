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
package filesystem;

public class File extends FileSystemObject {
  private String fileContents = "";

  /**
   * Creates a file with the name of the file
   *
   * @param potentialFileName is the name of the file
   * @throws BadFileSystemObjectNameError when the file name is invalid
   */
  public File(String potentialFileName) throws BadFileSystemObjectNameError {
    // just use the inherited method
    super(potentialFileName);
  }

  /**
   * Creates a file with the name and the initial contents of the file
   *
   * @param potentialFileName is the name of the file
   * @param contents          contents of the file
   * @throws BadFileSystemObjectNameError when the file name is invalid
   */
  public File(String potentialFileName, String contents)
      throws BadFileSystemObjectNameError {
    // use the inherited method
    super(potentialFileName);
    fileContents = contents;
  }

  /**
   * Returns a String of the file's contents
   *
   * @return the file's contents
   */
  public String getFileContents() {
    return fileContents;
  }

  /**
   * Adds the string onto the end of the file's contents. This method does
   * mutate the file.
   *
   * @param adder The string that is to be added onto the file's contents
   */
  public void addFileContents(String adder) {
    fileContents += adder;
  }

  /**
   * Returns the name of the file
   *
   * @return the name of the file
   */
  public String getFileName() {
    return super.getFileSystemObjectName();
  }

  /**
   * Given a string, overwrite all of the contents in the file with the
   * string
   *
   * @param overwriter the string that overwrites the file's contents
   */
  public void overwriteFile(String overwriter) {
    fileContents = overwriter;
  }

}
