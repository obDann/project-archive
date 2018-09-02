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

public class Cat extends Command {

  private FileSystem catFs;
  private String catCmd[];
  private String catFiles[];

  /**
   * creates a default constructor, the purpose of this is for Man command
   * to call getDoc
   */
  public Cat() {
  }

  /**
   * Creates a Cat object
   *
   * @param mainConsole the main console
   * @param command     An array of strings of the initial cat command, parsed
   *                    by spaces
   */
  public Cat(Console mainConsole, String command[]) {
    // get the filesystem
    catFs = mainConsole.getFileSystem();
    // assign the cat command
    catCmd = command;
  }

  /**
   * Shows the names of the passed File System Objects, dictated by the
   * passed arguments of Cat
   */
  public void execute() {
    // we have to determine if the command is being used properly
    if (validate()) {
      catFiles = Arrays.copyOfRange(catCmd, 1, catCmd.length);
      // get the last element for appropriate printing
      String lastEle = catFiles[catFiles.length - 1];
      // we make a string for the contents
      String contents;
      // and we make a boolean for the direction
      boolean direction;

      // then we want to go through the files
      for (String files : catFiles) {
        try {
          // what we now want to do is get the file
          File ourFile = catFs.getFileByPath(files);
          // in the assumption that we get the file, we append the contents to
          // the string we want to output
          contents = ourFile.getFileContents();
          // and this is a standard out
          direction = true;
        } catch (UnexpectedFsoError e) {
          // if this is caught, the file we are trying to get is a directory,
          // so we just inform the user
          contents = "cat: '" + files + "' is a Directory";
          // and its direction is standard err
          direction = false;
        } catch (InvalidPathError e) {
          // if this is caught, the Path is bad, so we inform the user
          contents = "cat: '" + files + "' is an invalid path";
          // and its direction is std err
          direction = false;
        }
        // we enqueue the contents and direction
        cmdOPQ.enqueue(contents, direction);
        // as instructed, we need something to break in between lines
        cmdOPQ.enqueue("\n", direction);
      }
    }
  }

  /**
   * Determines if the cat command is used properly
   *
   * @return true iff the command is being used properly
   */
  private boolean validate() {
    // first we have to check if the command is being used properly
    if (catCmd.length < 2 || !catCmd[0].equals("cat")) {
      // if it is, we throw an error
      try {
        throw new InvalidCmdUseError("Please see the man page of cat");
      } catch (InvalidCmdUseError e) {
        // then enqueue the error message
        cmdOPQ.enqueue(e.toString(), false);
        // then return false
        return false;
      }
    }
    return true;
  }

  /**
   * The man page of Cat
   *
   * @return the man page of cat
   */
  public static String getDoc() {
    String man = "";
    man += "CAT\n\n";
    man += "NAME\n";
    man += "\tcat - concatenate files to a standard output\n";
    man += "SYNOPSIS\n";
    man += "\tcat FILE...\n\n";
    man += "\twhere FILE is a file in the file system\n";
    man += "\twhere ... means multiple files separated by spaces";
    return man;
  }
}
