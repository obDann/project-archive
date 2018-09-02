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

package commands;

import java.util.ArrayList;
import console.Console;
import filesystem.BadFileSystemObjectNameError;
import filesystem.Directory;
import filesystem.DuplicateFileSystemObjectNameError;
import filesystem.File;
import filesystem.FileSystemObject;
import filesystem.InvalidPathError;
import filesystem.NonExistantChildError;

public class Cp extends Command {

  private Console c;
  private String[] cmdArg;
  private filesystem.FileSystem fs;
  int argLength;
  String cmdName;
  private String oldPath;
  private String newPath;
  private FileSystemObject o;
  private FileSystemObject n;
  boolean validOldPath;
  boolean validNewPath;
  String cmd = "copy";

  /**
   * creates a Cp command
   *
   * @param console The main Console
   * @param argument The argument of the command line
   */
  public Cp(Console console, String[] argument) {
    // store console, fileSystem, command Name, commandArgument, and
    // commandArgument length
    c = console;
    cmdArg = argument;
    fs = c.getFileSystem();
    argLength = cmdArg.length;
    cmdName = cmdArg[0];
    // if the command argument given is in the right format
    if ((cmdArg.length == 3) && (cmdName.equals("cp"))) {
      // find the String representation of the OldPath and NewPath
      oldPath = cmdArg[1];
      newPath = cmdArg[2];
      // check if the existence of the OldPath and NewPath
      validOldPath = fs.isExistPath(oldPath);
      validNewPath = fs.isExistPath(newPath);
    }
  }

  /**
   * copy the OldPath to NewPath without deleting the OldPath, where OldPath and
   * NewPath could be a directory or a file
   */
  public void execute() {
    // check the format of the argument
    if ((argLength != 3) || (!cmdName.equals("cp"))) {
      // print error message if given a invalid argument
      cmdOPQ.enqueue("Invalid Command Use: Please see the man page of cp",
          false);
    } else {
      try {
        // find the NewPath and OldPath address using the string representation
        // of the path
        o = fs.getFsoByPath(oldPath);
        n = fs.getFsoByPath(newPath);
        // check if we are copying the object to its self
        if (o.equals(n)) {
          // does not copy and print out the error message
          cmdOPQ.enqueue(cmdName + ": " + oldPath + ": can not copy to itself",
              false);
          // check if we are copying the root directory
        } else if (o != fs.getRootDir()) {
          // check if the NewPath given is inside the NewPath
          if (checkFsoParents(o, n)) {
         // copy the oldObj to newObj recursively
            notChildConditionHelper(o,n);
            // if the NewPath is a child of the NewPath, print the error message
          } else {
            cmdOPQ.enqueue(
                cmdName + ": " + oldPath + ": can not " + cmd + " to child",
                false);
          }
          // if we are copying the root directory, then print the error message
        } else {
          cmdOPQ.enqueue(cmdName + ": can not " + cmd + " root directory",
              false);
        }
      } catch (InvalidPathError e) {
        // print when the given path is not valid
        printInvalidPathError();
      }
    }
  }

  /**
   * help copy the oldObject to newObject recursively if the oldObject is not a
   * child of the newObject
   * @param o The oldPath we are copying
   * @param n The newPath we are copying to
   */
  public void notChildConditionHelper(FileSystemObject o,FileSystemObject n) {
    // if we are copying a directory
    if (o.getClass().getName().equals("filesystem.Directory")) {
      // copy OldPath to NewPath
      copyDir(o, n);
      // if we are copying a File
    } else if (o.getClass().getName().equals("filesystem.File")) {
      // copy OldPath to NewPath
      copyFile((File) o, n);
    }
  }

  /**
   * check if the newObj is a child of the oldObj or a child of the child of the
   * oldObj
   *
   * @param oldObj The FileSystemObject that we are copying
   * @param newObj The FileSystemObject that we are copying to
   * @return Whether the newObj is a child or a child of the child of the oldObj
   */
  public boolean checkFsoParents(FileSystemObject oldObj,
      FileSystemObject newObj) {
    // set the default return statement
    boolean result = true;
    // loop through the NewObject until the root directory
    while (newObj != null) {
      // move on to the parent of the newObject
      newObj = newObj.getParent();
      // if the newObject is equal to the OldObject
      if (newObj == oldObj) {
        // the newObject is the child of the OldObject
        result = false;
      }
    }
    // return the result of the check
    return result;
  }

  /**
   * prints when the path given does not exist
   */
  public void printInvalidPathError() {
    // if the OldPath given does not exist
    if (validOldPath == false) {
      cmdOPQ.enqueue(cmdName + ": " + oldPath + ": no such path", false);
    }
    // if the NewPath given does not exist
    if (validNewPath == false) {
      cmdOPQ.enqueue(cmdName + ": " + newPath + ": no such path", false);
    }
  }

  /**
   * copies the OldPath to the NewPath without deleting the OldPath, the OldPath
   * and NewPath can be a directory or a file
   *
   * @param o The FileSystemObject that we are copying
   * @param n The FileSystemObject that we are copying to
   */
  public void copyDir(FileSystemObject o, FileSystemObject n) {
    // check if we are copying a Directory to a file
    if (n.getClass().getName().equals("filesystem.File")) {
      // print error message if we are copying directory to a file
      cmdOPQ.enqueue(cmdName + ": can not " + cmd + " a directory to a file",
          false);
      // Base case: if the given FileSystemObject is a file, copy the file
    } else if (o.getClass().getName().equals("filesystem.File")) {
      copyFile((File) o, n);
      // recursive case
    } else if ((o.getClass().getName().equals("filesystem.Directory"))
        || (n.getClass().getName().equals("filesystem.Directory"))) {
      try {
        // create a new Directory and copy it to the NewPath
        Directory newDir = new Directory(o.getFileSystemObjectName());
        ((Directory) n).storeNewFileSystemObject(newDir);
        // find the child/children of the OldDirectory
        ArrayList<FileSystemObject> oDirChild = ((Directory) o).getChildren();
        // if the OldDirectory has child/children
        if (oDirChild != null) {
          n = ((Directory) n).getChild(o.getFileSystemObjectName());
          // loop through each child of the given oldDirectory and copy them to
          // NewPath
          for (FileSystemObject child : oDirChild) {
            copyDir(child, n);
          }
          // if the OldDirectory does not have a child, copy it self to NewPath
        } else if (oDirChild == null) {
          copyDir(o, n);
        }
        // print error message if the NewPath already have a FileSystemObject
        // that has the same name of the OldPath
      } catch (BadFileSystemObjectNameError | DuplicateFileSystemObjectNameError
          | NonExistantChildError e) {
        cmdOPQ.enqueue(cmdName + ": " + o.getFileSystemObjectName()
            + ": already exist in " + fs.printFsoPath(n), false);
      }
    }
  }

  /**
   * copies the OldFile to the NewPath without deleting the OldFile, the
   * OldPaFile is a file and NewPath can be a directory or a file
   *
   * @param o The File that we are copying
   * @param n The FileSystemObject that we are copying to
   */
  public void copyFile(File o, FileSystemObject n) {
    try {
      // copying a file to a file
      if (n.getClass().getName().equals("filesystem.File")) {
        // if we are copying to a file with the same name
        if (n.getFileSystemObjectName().equals(o.getFileSystemObjectName())) {
          ((File) n).overwriteFile(o.getFileContents());
          // if we are copying to a file with different name
        } else {
          // then we are copying to the new file's directory
          n = n.getParent();
          // create a new File we the oldFile's name and content
          File newFile =
              new File(o.getFileSystemObjectName(), o.getFileContents());
          // copy the file to the NewPath
          ((Directory) n).storeNewFileSystemObject(newFile);
        }
        // copying a file to a directory
      } else {
        // check we are copying a file to itself
        FileSystemObject child = checkChildName(o, (Directory) n);
        // if we are copying a file to itself, then overwrite the file
        if (child.equals(n) == false) {
          ((File) child).overwriteFile(o.getFileContents());
          // if we are not copying to itself
        } else {
          // create a new File we the oldFile's name and content
          File newFile =
              new File(o.getFileSystemObjectName(), o.getFileContents());
          // copy the file to the NewPath
          ((Directory) n).storeNewFileSystemObject(newFile);

          // print error message if the NewPath already have a FileSystemObject
          // that has the same name of the OldFile
        }
      }
    } catch (BadFileSystemObjectNameError
        | DuplicateFileSystemObjectNameError e) {
      cmdOPQ.enqueue(cmdName + ": " + o.getFileSystemObjectName()
          + ": already exist in " + fs.printFsoPath(n), false);
    }
  }

  /**
   * check if we are copying the file into its own directory, if we are copying
   * to its own directory then return the file. If we are not copying to its own
   * directory then return the directory we are copying to
   *
   * @param o The file we are copying
   * @param n The directory we are copying to
   * @return result The
   */
  public FileSystemObject checkChildName(File o, Directory n) {
    // initiate the result
    FileSystemObject result;
    // check if we are copying a file into itself
    try {
      // return the file we are copying
      result = n.getChild(o.getFileSystemObjectName());
      // if we are not copying the file into itself
    } catch (NonExistantChildError e) {
      // return the directory we are copying to
      result = n;
    }
    return result;
  }

  /**
   * returns the documentation of the Cp
   *
   * @return man The documentation for Cp
   */
  public static String getDoc() {
    String man = "";
    man += "Cp\n\n";
    man += "NAME\n";
    man +=
        "\tcp - copy the given OLDPATH to the NEWPATH without deleting the"
        + "OLDPATH\n";
    man += "SYNOPSIS\n";
    man += "\tcp OLDPATH NEWPATH\n\n";
    man += "\twhere OLDPATH is the path that we want to copy";
    man += "\twhere NEWPATH is the path that we want to copy to";
    return man;
  }
}
