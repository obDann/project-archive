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
import filesystem.*;

import java.util.Arrays;
import java.util.ArrayList;

public class Ls extends Command {
  private FileSystem lsFs;
  private String lsPath[];
  private String lsCmd[];
  private String mode;
  private boolean justLs = false;

  /**
   * creates a default constructor, the purpose of this is for Man command
   * to call getDoc
   */
  public Ls() {
  }

  /**
   * Creates an Ls object
   *
   * @param mainConsole the main console
   * @param command     an array of the original ls command parsed by spaces
   */
  public Ls(Console mainConsole, String command[]) {
    // set the current file system
    lsFs = mainConsole.getFileSystem();
    // and assign the ls command
    lsCmd = command;
  }

  /**
   * Shows the contents of the indicated path; if the path is a file, the
   * name of the file will only be shown
   */
  public void execute() {
    // check for validation
    if (validate()) {
      // then we can assign our private variables
      assignViaBaseCase();
      // from here, we want to show the contents for each path, but it depends
      // on the mode
      for (String path : lsPath) {
        if (mode.equals("regular")) {
          // show the contents of the path
          showContents(path);
        } else {
          recursiveMode(path);
        }
      }
    }
  }

  /**
   * Given a path, recurisveMode will view the contents of the path and its
   * subdirectories (if there are any)
   *
   * @param path
   */
  private void recursiveMode(String path) {
    // first we want to show the contents of this current path
    showContents(path);
    // then we want to get the file system object of this path
    try {
      // get the fso of this path
      FileSystemObject checker = lsFs.getFsoByPath(path);
      // then we check if the checker is a directory
      if (checker instanceof Directory) {
        // if it is, then we want to down cast the fso
        Directory recurDir = (Directory) checker;
        // then we want its children
        ArrayList<FileSystemObject> children = recurDir.getChildren();
        // go through the children
        for (FileSystemObject child : children) {
          // we get the name of the child
          String childName = child.getFileSystemObjectName();
          // then we check if the child is a directory
          if (child instanceof Directory) {
            // we check if the path ends with a "/"
            String lastChar = path.substring(path.length() - 1, path.length());
            if (!lastChar.equals("/")) {
              // if it doesn't, add the "/"
              path += "/";
            }
            // then add the parent name and the child name
            String recursiveStep = path + childName;
            recursiveMode(recursiveStep);
          }
        }
      }
    } catch (InvalidPathError e) {
      // we don't need to do anything if this runs because showContents
      // already shows whether the path is valid or not
    }
  }


  /**
   * Returns true iff the ls command is being used properly
   */
  private boolean validate() {
    // we just want to make sure that the command is used properly
    if (lsCmd.length == 0 || !lsCmd[0].equals("ls")) {
      // so we throw an error
      try {
        throw new InvalidCmdUseError("Please see the man page of ls");
      } catch (InvalidCmdUseError e) {
        // enqueue it to our output queue
        cmdOPQ.enqueue(e.toString(), false);
      }
      return false;
    }
    return true;
  }

  /**
   * Assigns the private variables varying on the possible cases for ls
   */
  private void assignViaBaseCase() {
    // if the length is 1, then the path is the current directory
    if (lsCmd.length == 1) {
      String tempPath[] = {"."};
      lsPath = tempPath;
      mode = "regular";
      // and we're only assigning just ls
      justLs = true;
    }
    // otherwise, we check if there is a "-R" token
    else if (lsCmd[1].equals("-R")) {
      // we check if there exists the "-R" token
      // we set our mode to recursive
      mode = "recursive";
      // and then we check if the length of the lsCmd is only 2
      if (lsCmd.length == 2) {
        // if it is only 2, then lsPath is the current path
        String tempPath[] = {"."};
        lsPath = tempPath;
      } else {
        // if it's not equal to 2, then we get the paths past the -R token
        String tempPath[] = Arrays.copyOfRange(lsCmd, 2, lsCmd.length);
        lsPath = tempPath;
      }
    } else {
      // so we get the paths past "ls"
      String tempPath[] = Arrays.copyOfRange(lsCmd, 1, lsCmd.length);
      lsPath = tempPath;
      // and our mode is regular
      mode = "regular";
    }
  }

  /**
   * Enqueues all file system objects in a path to the output queue
   *
   * @param path the path that is to be
   */
  private void showContents(String path) {
    // make a string to print
    String toPrint = "";
    // and we have to determine whether or not the path is a directory
    boolean isDir;
    try {
      // get the fso by the path
      FileSystemObject fsoGetter = lsFs.getFsoByPath(path);
      // check if the fso is a directory
      if (fsoGetter instanceof Directory) {
        // we now know it is a directory
        isDir = true;
        // down cast it and get the children
        Directory theDir = (Directory) fsoGetter;
        ArrayList<FileSystemObject> children = theDir.getChildren();
        // loop through the children
        for (FileSystemObject child : children) {
          // just append the children to the string to print to
          toPrint += child.getFileSystemObjectName() + " ";
        }
      }
      // otherwise, the fso is a file
      else {
        isDir = false;
        // so we just print its path
        toPrint = path;
      }
      // check if the string is empty
      if (!toPrint.isEmpty()) {
        // check if the string ends with a space
        String ending =
            toPrint.substring(toPrint.length() - 1, toPrint.length());
        if (ending.equals(" ")) {
          // if it does, then remove it
          toPrint = toPrint.substring(0, toPrint.length() - 1);
        }
      }
      // from here, we know that our path is good, so we enqueue our path
      // to stdOut if it is a directory
      if (isDir && ! justLs) {
        cmdOPQ.enqueue(path + ":", true);
      }
      // and we enqueue the file(s) the path contains and a line in-between
      cmdOPQ.enqueue(toPrint + "\n", true);

    } catch (InvalidPathError e) {
      // otherwise, we just state that the path is an invalid path
      String msg =
          "ls: cannot access '" + path + "': No such file or directory\n";
      // then enqueue it to our output
      cmdOPQ.enqueue(msg, false);
    }
  }

  /**
   * The man page of Ls
   *
   * @return the man page of Ls
   */

  public static String getDoc() {
    String man = "";
    man += "LS\n\n";
    man += "NAME\n";
    man += "\tls - print the file name itself or the contents of a\n";
    man += "\tdirectory\n";
    man += "SYNOPSIS\n";
    man += "\tls [-R] [FILE]... [DIRECTORY]...\n\n";
    man += "\twhere -R is a token to view the path's contents and it's sub";
    man += "directories' contents\n";
    man += "\twhere FILE is a file in the current file system\n";
    man += "\twhere DIRECTORY is a directory in the current file system\n";
    man += "\twhere [] means optional\n";
    man += "\twhere ... means multiple (files or directories)";
    return man;
  }
}
