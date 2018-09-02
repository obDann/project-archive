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

public class FileSystemObject {

  private FileSystemObject parent; // Shouldn't this always be a directory?
  protected String fileSystemObjectName;
  /**
   * A regex to be used to see if FileSystemObject names match to alphanumeric
   * characters or dots.
   */
  private static String alphanumericRegex = "^.*[^a-zA-Z0-9\\. ].*$";

  /**
   * creates an empty FileSystemObject, for FileSystem class findDir method use
   * only
   */
  public FileSystemObject() {
    fileSystemObjectName = null;
  }

  /**
   * Creates a FileSystemObject, given its name
   *
   * @param fsoName is the name of the newly created FileSystem Object
   * @throws BadFileSystemObjectNameError when the fsoName is invalid
   */
  public FileSystemObject(String fsoName) throws BadFileSystemObjectNameError {
    // check if the fso Name is invalid (checking if the string is
    // alphanumeric)
    boolean isValid = !fsoName.matches(alphanumericRegex);
    // and we have to determine if the name has spaces
    boolean haveSpaces = fsoName.contains(" ");

    // if it is alpha numeric and does not contain spaces
    if (isValid && ! haveSpaces) {
      // then we assign the name
      fileSystemObjectName = fsoName;
    } else {
      // otherwise we just throw an error
      throw new BadFileSystemObjectNameError(
          "FileSystemObject names can " + "only be alphanumeric");
    }
  }

  /**
   * Returns the parent of the FileSystemObject
   *
   * @return Returns the parent of the FileSystemObject
   */
  public FileSystemObject getParent() {
    return parent;
  }

  /**
   * Sets the parent of the FileSystemObject
   *
   * @param parent the FileSystemObject that is to be the parent
   */
  public void setParent(FileSystemObject parent) {
    this.parent = parent;
  }

  /**
   * Returns the FileSystemObject's name
   *
   * @return the FileSystemObject's name
   */
  public String getFileSystemObjectName() {
    return fileSystemObjectName;
  }

  /**
   * Sets the FileSystemObject's name
   *
   * @param fsoName the name that is to be the FileSystemObject's name
   * @throws BadFileSystemObjectNameError when the fsoName is invalid
   */
  public void setFileSystemObjectName(String fsoName)
      throws BadFileSystemObjectNameError {
    // check if the fso Name is invalid (checking if the string is
    // alphanumeric)
    boolean isValid = !fsoName.matches(alphanumericRegex);
    // and we have to determine if the name has spaces
    boolean haveSpaces = fsoName.contains(" ");

    // if it is alpha numeric and does not contain spaces
    if (isValid && ! haveSpaces) {
      // then we assign the name
      fileSystemObjectName = fsoName;
    } else {
      // otherwise we just throw an error
      throw new BadFileSystemObjectNameError(
          "FileSystemObject names can " + "only be alphanumeric");
    }
  }
}
