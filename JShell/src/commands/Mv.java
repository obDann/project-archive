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

import console.Console;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystemObject;
import filesystem.InvalidPathError;

public class Mv extends Cp {

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
  String cmd = "move";

  /**
   * creates a Mv command
   *
   * @param console The main Console
   * @param argument The argument of the command line
   */
  public Mv(Console console, String[] argument) {
    // store console, fileSystem, command Name, commandArgument, and
    // commandArgument length
    super(console, argument);
    c = console;
    cmdArg = argument;
    fs = c.getFileSystem();
    argLength = cmdArg.length;
    cmdName = cmdArg[0];
    // if the command argument given is in the right format
    if ((cmdArg.length == 3) && (cmdName.equals("mv"))) {
      // find the String representation of the OldPath and NewPath
      oldPath = cmdArg[1];
      newPath = cmdArg[2];
      // check if the existence of the OldPath and NewPath
      validOldPath = fs.isExistPath(oldPath);
      validNewPath = fs.isExistPath(newPath);
    }
  }

  /**
   * move the OldPath to NewPath and deleting the OldPath, where OldPath and
   * NewPath could be a directory or a file
   */
  public void execute() {
    // check the format of the argument
    if ((argLength != 3) || (!cmdName.equals("mv"))) {
      // print error message if given a invalid argument
      cmdOPQ.enqueue("Invalid Command Use: Please see the man page of mv",
          false);
    } else {
      try {
        // find the NewPath and OldPath address using the string representation
        // of the path
        o = fs.getFsoByPath(oldPath);
        n = fs.getFsoByPath(newPath);
        // check if we are moving the object to its self
        if (o.equals(n)) {
          // does not move and print out the error message
          cmdOPQ.enqueue(cmdName + ": " + oldPath + ": can not copy to itself",
              false);
          // check if we are copying the root directory
        } else if (o != fs.getRootDir()) {
          // check if the NewPath given is inside the NewPath
          if (checkFsoParents(o, n)) {
            // check if we are moving the OldPath to its parent
            boolean remove = (o.getParent() == n);
            // copy the oldObj to newObj recursively
            notChildConditionHelper(o, n);
            // if we are not moving the OldPath to its parent
            removeOldPathHelper(remove);
          } else {
            cmdOPQ.enqueue(
                cmdName + ": " + oldPath + ": can not " + cmd + " to child",
                false);
          }
          // if the NewPath is a child of the NewPath, print the error message
        } else {
          cmdOPQ.enqueue(cmdName + ": can not " + cmd + " root directory",
              false);
        }
        // if we are moving the root directory, then print the error message
      } catch (InvalidPathError e) {
        printInvalidPathError();
      }
    }
  }

  /**
   * help remove the oldPath for move when we are not moving the oldPath to
   * itself or to its parent, does nothing if it's copying to itself or to its
   * parent
   * 
   * @param remove Boolean indicate whether we should remove or not
   */
  private void removeOldPathHelper(boolean remove) {
    // if we are not copying to itself or its parent
    if (remove == false) {
      // remove the OldPath
      ((Directory) o.getParent()).getChildren().remove(o);
    }
  }

  /**
   * returns the documentation of the Mv
   *
   * @return man The documentation for Mv
   */
  public static String getDoc() {
    String man = "";
    man += "Mv\n\n";
    man += "NAME\n";
    man +=
        "\tmv - copy the given OLDPATH to the NEWPATH and deleting the"
        + "OLDPATH\n";
    man += "SYNOPSIS\n";
    man += "\tmv OLDPATH NEWPATH\n\n";
    man += "\twhere OLDPATH is the path that we want to copy";
    man += "\twhere NEWPATH is the path that we want to copy to";
    return man;
  }
}
