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
import filesystem.FileSystem;

public class Pwd extends Command {
  private FileSystem pwdFs;
  private String pwdCmd[];

  /**
   * creates a default constructor, the purpose of this is for Man command
   * to call getDoc
   */
  public Pwd() {
  }

  /**
   * Creates a pwd object
   *
   * @param mainConsole the main console
   * @param command     An array of the initial pwd command parsed by spaces
   */
  public Pwd(Console mainConsole, String[] command) {

    // we just get the file system
    pwdFs = mainConsole.getFileSystem();
    // and then set the command
    pwdCmd = command;
  }


  /**
   * Prints the current working directory
   */
  public void execute() {
    // we just check if the command is being used properly
    if (pwdCmd.length != 1 || !pwdCmd[0].equals("pwd")) {
      // just throw an error
      try {
        throw new InvalidCmdUseError("Please see the man page of pwd");
      } catch (InvalidCmdUseError e) {
        // just enqueue the error
        cmdOPQ.enqueue(e.toString(), false);
        //then we want to get out of execute
        return;
      }
    }
    String curPath = pwdFs.printCurrentPath();

    // then just enqueue it to our output queue
    cmdOPQ.enqueue(curPath, true);
  }

  /**
   * The man page of Pwd
   *
   * @return the man page of pwd
   */
  public static String getDoc() {
    String man = "";
    man += "PWD\n\n";
    man += "NAME\n";
    man += "\tpwd - print the absolute path for the current working directory";
    man += " to the shell\n";
    man += "SYNOPSIS\n";
    man += "\tpwd";
    return man;
  }
}
