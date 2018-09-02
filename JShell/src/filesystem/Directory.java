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

import java.util.ArrayList;

public class Directory extends FileSystemObject {

  private ArrayList<FileSystemObject> children =
      new ArrayList<FileSystemObject>();

  public ArrayList<FileSystemObject> getChildren() {
    return children;
  }

  /**
   * Returns a directory whose name is "/"
   *
   * @return a directory whose name is "/"
   */
  public static Directory makeRoot() {
    try {
      Directory temp = new Directory("aDir");
      // we want to change temp's name
      temp.fileSystemObjectName = "/";
      // then we return our directory
      return temp;
    } catch (BadFileSystemObjectNameError e) {
      // this should not run as "aDir" is a valid directory
    }
    return null;
  }

  /**
   * Given the child's name, this method returns the child as an object
   *
   * @param childName the name of the child
   * @return the FileSystemObject representation of the child
   * @throws NonExistantChildError when there doesn't exist a child with
   *                               that name
   */
  public FileSystemObject getChild(String childName)
      throws NonExistantChildError {

    // go through the children
    for (FileSystemObject child : children) {
      String existingChildName = child.getFileSystemObjectName();
      if (childName.equals(existingChildName)) {
        // just return the child
        return child;
      }
    }
    // otherwise it doesn't exist
    throw new NonExistantChildError(
        "The File System Object '" + childName + "' doesn't exist");
  }

  /**
   * Create a directory with the given name
   *
   * @param directoryName name of the directory
   * @throws BadFileSystemObjectNameError when the directoryName is not
   *                                      alpha numeric
   */
  public Directory(String directoryName) throws BadFileSystemObjectNameError {
    // we use our inherited constructor to deal with this
    super(directoryName);
  }

  /**
   * Store a directory or file in a directory
   *
   * @param fileSystemObject the directory or file to be stored
   * @throws DuplicateFileSystemObjectNameError when there is already a file
   *                                            system object with the same
   *                                            name
   */
  public void storeNewFileSystemObject(FileSystemObject fileSystemObject)
      throws DuplicateFileSystemObjectNameError {
    // check if there is a duplicate
    checkDuplicateFsoName(fileSystemObject);
    // if it isn't then just continue to set the parent
    fileSystemObject.setParent(this);
    // set child of current directory to given fileSystemObject
    this.children.add(fileSystemObject);
  }

  /**
   * Checks if the passed fileSystemObject has the same name as one of
   * the file system objects in its directory
   *
   * @param fileSystemObject the file system object that is to be added
   *                         to the directory
   * @throws DuplicateFileSystemObjectNameError when there exists a
   *                                            duplicate file system name
   */
  private void checkDuplicateFsoName(FileSystemObject fileSystemObject)
      throws DuplicateFileSystemObjectNameError {
    // first, get the name of the fileSystemObject
    String theName = fileSystemObject.getFileSystemObjectName();

    // then we want to loop through the Directory
    for (FileSystemObject fso : children) {
      // get the name of the children
      String childName = fso.getFileSystemObjectName();
      // check if the names are the same
      if (theName.equals(childName)) {
        throw new DuplicateFileSystemObjectNameError(
            "The File System Object '" + theName
                + "' already exists in this directory");
      }
    }
  }
}
