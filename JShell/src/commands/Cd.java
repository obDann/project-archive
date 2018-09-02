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

import filesystem.*;
import console.Console;

public class Cd extends Command {

  private FileSystem cdFs;
  private String cdDir;
  private String cdCmd[];

  /**
   * creates a default constructor, the purpose of this is for Man command
   * to call getDoc
   */
  public Cd() {
  }

  /**
   * Creates a Cd object
   *
   * @param mainConsole the main console
   * @param command     An array of strings of the initial cd command, parsed
   *                    by spaces
   */
  public Cd(Console mainConsole, String[] command) {
    // set the Cd's filesystem
    cdFs = mainConsole.getFileSystem();
    // then we want to obtain the array of commands
    cdCmd = command;
  }

  /**
   * Changes the directory of the File System
   */
  public void execute() {
    // we first have to validate the initial array given to us
    boolean isValidArgs = validateInitialArgs();

    if (isValidArgs){
      // next we have to get the directory the user wishes to go into
      cdDir = cdCmd[1];
      try {
        // get the directory by the path passed
        Directory dirGetter = cdFs.getDirectoryByPath(cdDir);
        // if all is well, set the directory of the file system
        cdFs.setCurrentObj(dirGetter);
      } catch (UnexpectedFsoError e) {
        // just throw an invalid command use error forward since the fso is
        // not a directory
        try{
          String message = "The path '" + cdDir + "' is not a directory";
          throw new InvalidCmdUseError(message);
        }
        catch (InvalidCmdUseError er){
          // just enqueue the error message
          cmdOPQ.enqueue(er.toString(), false);
        }
      } catch (InvalidPathError e) {
        // just enqueue the message of an invalid path error
        cmdOPQ.enqueue(e.toString(), false);
      }
    }
  }

  /**
   * Returns true iff the command is being used properly
   *
   * @return true iff the command is being used properly
   */
  private boolean validateInitialArgs(){
    // check if the command "fails" by not having two arguments or having
    // its first argument not be cd
    if (cdCmd.length != 2 || !cdCmd[0].equals("cd")) {
      // we want to enqueue an invalid command use error
      try {
        throw new InvalidCmdUseError("Please see the man page of cd");
      }
      catch (InvalidCmdUseError e){
        // just enqueue the error message
        cmdOPQ.enqueue(e.toString(), false);
        // then return false
        return false;
      }
    }
    return true;
  }

  /**
   * The man page of Cd
   *
   * @return the man page of Cd
   */
  public static String getDoc() {
    String man = "";
    man += "CD\n\n";
    man += "NAME\n";
    man += "\tcd - change the current filesystem to be in a\n";
    man += "\tdifferent directory\n";
    man += "SYNOPSIS\n";
    man += "\tcd DIRECTORY\n\n";
    man += "\twhere DIRECTORY is a directory in the file system";
    return man;
  }
}

