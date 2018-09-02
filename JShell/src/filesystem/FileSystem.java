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
// principle
package filesystem;

import java.util.ArrayList;

import commands.DirectoryStack;

public class FileSystem {

  // null is not actually neccessary here, just for clarity.
  private static FileSystem singleReference = null;

  private final Directory rootDir;
  private FileSystemObject currentDir;
  private FileSystemObject previousDir;
  private DirectoryStack stack = new DirectoryStack();

  /**
   * Create a path for a single user by giving the root directory
   */
  private FileSystem() {
    // Set up a root directory. TODO Potentially allow root to have no name
    rootDir = Directory.makeRoot(); // Name is "root" for now. Change??
    // this.rootDir.setParent(this.rootDir);
    currentDir = rootDir;
    previousDir = rootDir.getParent();
  }

  /**
   * Creates a single instance of a FileSystem. In other words, if a FileSystem
   * already exists, return that instance instead of making a new one.
   *
   * @return A (single) instance of a FileSystem.
   */
  public static FileSystem createFileSystemInstance() {
    // Only creates a single instance of FileSystem.
    return (singleReference == null) ? new FileSystem() : singleReference;
  }

  /**
   * returns the Directory Stack
   *
   * @return stack The directory stack
   */
  public DirectoryStack getStack() {
    return stack;
  }

  /**
   * Return the current working directory
   *
   * @return currentDir
   */
  public FileSystemObject getCurrentPath() {
    return currentDir;
  }

  /**
   * Return the previous directory relative to the current working directory
   *
   * @return The previous working directory
   */
  public FileSystemObject getPreviousDir() {
    return previousDir;
  }

  /**
   * returns the root directory of the user
   *
   * @return The root directory of the user
   */
  public Directory getRootDir() {
    return rootDir;
  }

  /**
   * Set the current working directory to another existing directory or file
   *
   * @param newObj The address of the another existed directory (or file)
   */
  public void setCurrentObj(FileSystemObject newObj) {
    // set the current working path and its previous path
    currentDir = newObj;
    previousDir = newObj.getParent();
  }

  /**
   * returns a string representation of the given FileSystemObject
   *
   * @return The string representation of the given FileSystemObject
   */
  public String printFsoPath(FileSystemObject fso) {
    String result = "";
    if (fso == rootDir) {
      result = rootDir.getFileSystemObjectName();
    } else {
      while (fso != rootDir) {
        result = ("/" + fso.getFileSystemObjectName() + result);
        fso = fso.getParent();
      }
    }
    return result;
  }

  /**
   * returns a string representation of the current working directory
   *
   * @return The string representation of the current working directory
   */
  public String printCurrentPath() {
    return printFsoPath(currentDir);
  }

  /**
   * Determines if a path exists or not given a string representation of an
   * absolute or relative path.
   *
   * @param obj The string representation of a path (absolute or relative)
   * @return result Return true if the path exists in the file system
   */
  public boolean isExistPath(String obj) {
    // check if the given path is relative
    if (obj.indexOf("/") != 0) {
      // convert the relative path to absolute path
      String curPath = this.printCurrentPath();
      obj = curPath + "/" + obj;
    }
    // split the absolute path into string array
    obj = obj.replace("/", " ").trim();
    String[] split = obj.split(" ");
    // initialize the current object and the result of finding
    FileSystemObject cur = rootDir;
    boolean result = true;
    // loop through each object in the absolute path
    for (String i : split) {
      // find the next object in current object's children
      cur = this.findObj(cur, i);
      // if the next object does not exist in the children
      if (cur.getFileSystemObjectName() == null) {
        // we cannot find the given path
        result = false;
      }
    }
    // return the result of finding
    return result;
  }

  /**
   * sets the current path to the existed path by giving a string representation
   * of an existed absolute path
   *
   * @param obj The string representation of the existed absolute path
   */
  public void goToPath(String obj) {
    try {
      // check if the path given is relative
      if (obj.indexOf("/") != 0) {
        // if the path given is relative, then change it to absolute
        String curPath = this.printCurrentPath();
        obj = curPath + "/" + obj;
      }
      // find the path using a string representation of it and set it to be the
      // current path
      FileSystemObject setPath = this.getFsoByPath(obj);
      this.setCurrentObj(setPath);
    } catch (InvalidPathError e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * returns the address of the given file or directory name if it exists
   * returns an empty FileSystemObject if the given file or directory name does
   * not exist
   *
   * @param start Search the directory or file name starting from this file or
   *              directory, the given file or directory must exist and does not
   *              equal to null
   * @param name  The directory or file name to search
   * @return find The address of the file or directory with the given name or an
   * empty FileSystemObject if the name does not exist
   */
  public FileSystemObject findObj(FileSystemObject start, String name) {
    // initialize the return statement
    FileSystemObject find = new FileSystemObject();
    // base case: check if the object has the name we are looking for
    if (start.getFileSystemObjectName().equals(name)) {
      find = start;
    }
    // recursive case: if the object is not the one we are looking for then go
    // to its children if applicable
    else if (start.getClass().getName() == "filesystem.Directory") {
      // find the object's children
      ArrayList<FileSystemObject> children = ((Directory) start).getChildren();
      // loop through each children of the object
      for (FileSystemObject child : children) {
        // loop through each children
        FileSystemObject get = findObj(child, name);
        // check if the object we are looping for is in the one of the children
        if (get.getFileSystemObjectName() != null) {
          // find the object we are looking for
          find = get;
        }
      }
    }
    // return the object we are looking for if exist
    return find;
  }

  /**
   * Check if the file system is empty i.e. it does not contain any directories
   * or files.
   *
   * @return True if the file system is empty.
   */
  public boolean isEmpty() {
    // TODO Perhaps change this to check the previous dir and current dir as
    // well.
    // The file system is empty if rootDir points to nothing.
    return rootDir == null;
  }

  /**
   * Returns a directory given an absolute or relative path
   *
   * @param path The absolute or relative path
   * @return fsoGetter The expected directory
   * @throws UnexpectedFsoError The error exists when the path given is not a
   *                            directory
   * @throws InvalidPathError   The error exists when the path given is invalid
   */
  public Directory getDirectoryByPath(String path)
      throws UnexpectedFsoError, InvalidPathError {
    // get the fso
    FileSystemObject fsoGetter = getFsoByPath(path);
    // check if the fso is a file
    if (fsoGetter instanceof Directory) {
      // then return the Directory
      return (Directory) fsoGetter;
    }
    // otherwise, just throw an unexpected fso error
    throw new UnexpectedFsoError("'" + path + "' is not a Directory");
  }

  /**
   * Returns a File given an absolute or relative path
   *
   * @param path the absolute or relative path
   * @return The expected file
   * @throws UnexpectedFsoError
   * @throws InvalidPathError
   */
  public File getFileByPath(String path)
      throws UnexpectedFsoError, InvalidPathError {
    // get the fso
    FileSystemObject fsoGetter = getFsoByPath(path);
    // check if the fso is a file
    if (fsoGetter instanceof File) {
      // then return the file
      return (File) fsoGetter;
    }
    // otherwise, just throw an unexpected fso error
    throw new UnexpectedFsoError("'" + path + "' is not a File");
  }

  /**
   * Returns a File System Object given an absolute or relative path
   *
   * @param path the absolute or relative path
   * @return returner The expected File System Object
   * @throws InvalidPathError The error exists when the given path does not
   *                          exist
   */
  public FileSystemObject getFsoByPath(String path) throws InvalidPathError {
    // get the current path, as it is a Directory
    Directory currentPath = (Directory) this.getCurrentPath();
    // check if the path is just a "/"
    if (path.equals("/")) {
      // if it is, then we return the root path
      return this.getRootDir();
    } else {
      // check if the passed path is absolute
      String isAbs = path.substring(0, 1);
      if (isAbs.equals("/")) {
        // if it is absolute, set the currentPath to root
        currentPath = this.getRootDir();
        // and we can remove the first "/" so it acts relative
        path = path.substring(1, path.length());
      }
      // we set a traveling directory to the current path
      Directory traveller = currentPath;
      // what we want to do is go through the relative path from the user
      // so we parse it
      String[] toMoveAround = path.split("/");
      return getFsoHelper(traveller, toMoveAround, path);
    }
  }


  /**
   * A helper function for getFsoByPath
   *
   * @param traveller    the Directory that is supposed to travel around the
   *                     file system
   * @param toMoveAround An array of strings where it is paresed by "/"
   * @param path         the path given
   * @return the file system object
   * @throws InvalidPathError if the given path is invalid
   */
  private FileSystemObject getFsoHelper(Directory traveller,
      String toMoveAround[], String path) throws InvalidPathError {
    // we save the last element of the string in the event that it is the
    // object we want to return
    String lastEle = toMoveAround[toMoveAround.length - 1];
    // and we want to reserve a "saving" fso to return
    FileSystemObject returner = null;
    // and then we move through the fsos
    for (String mover : toMoveAround) {
      // base case: if the relative path is only a "."
      if (mover.equals(".")) {
        // if it is, then we allocate the returner to the traveller
        returner = traveller;
      }
      // base case: if the relative path is only a ".."
      else if (mover.equals("..")) {
        // if there does exist a ".." then we assume that the parent is a
        // Directory
        Directory backwards = (Directory) traveller.getParent();
        // then we have to check if the traveller's parent isn't the root
        if (backwards != null) {
          // if it isn't, then we can set the traveller to go backwards
          traveller = backwards;
        }
        // and we hold the returner to traveller
        returner = traveller;
      }
      // otherwise, mover is an arbitrary fso
      else {
        // so we call our fso bulk
        FileSystemObject theBulk[] =
            getFsoBulk(traveller, mover, lastEle, returner, path);
        // we set our traveller
        traveller = (Directory) theBulk[0];
        // and we set our returning string
        returner = theBulk[1];
      }
      // we check if the mover is the same as the last element
      if (mover == lastEle) {
        // if it is, then we return the returner
        return returner;
        // this works because we are comparing references, not values
      }
    }
    return null;
  }

  /**
   * Represents the bulk of the original getFsoByPath code if mover is not "
   * ." or ".."
   *
   * @param traveller the travelling Directory
   * @param mover     a name of a directory in the path
   * @param lastEle   the last element of the array list
   * @param returner  the object that is proposed to be returned
   * @param path      the original path given
   * @return the updated traveller and returner
   * @throws InvalidPathError if the given path is invalid
   */
  private FileSystemObject[] getFsoBulk(Directory traveller, String mover,
      String lastEle, FileSystemObject returner, String path)
      throws InvalidPathError {
    try {
      // get the child indicated by mover
      FileSystemObject child = traveller.getChild(mover);
      // if the child is what we wanted
      if (mover == lastEle) {
        // then we set the returner to the child
        returner = child;
      }
      // otherwise we have to check if the child is a directory
      else if (child instanceof Directory) {
        // we set the traveler as the child so we traverse further
        traveller = (Directory) child;
      }
      // otherwise the child is a file and we cannot traverse further
      else {
        throw new InvalidPathError("Path '" + path + "' does not " + "exist");
      }
      // then we can return the traveller and the returner
      FileSystemObject toReturn[] = {traveller, returner};
      return toReturn;
    } catch (NonExistantChildError e) {
      // if this is ran, then there doesn't exist such an fso, so the
      // path doesn't exist
      throw new InvalidPathError("Path '" + path + "' does not exist");
    }
  }
}
