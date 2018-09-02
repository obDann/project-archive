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

public class Echo extends Command {

  private String echoPath = "";
  private String echoFileName;
  private String echoContents;
  private FileSystem echoFs;
  private String mode = "print";
  private String echoCmd[];

  /**
   * creates a default constructor, the purpose of this is for Man command
   * to call getDoc
   */
  public Echo() {
  }

  /**
   * Creates an Echo object
   *
   * @param mainConsole the main console
   * @param command     An array of strings of the initial echo command, parsed
   *                    by spaces
   */
  public Echo(Console mainConsole, String[] command) {
    // we assign the file system
    echoFs = mainConsole.getFileSystem();
    // then we assign the command
    echoCmd = command;
  }

  /**
   * Executes the Echo Command accordingly to either append, overwrite, make
   * a file or just print to the shell
   */
  public void execute() {
    // first validate the command
    boolean isValid = validate();
    // if it is valid, then just enqueue the string
    if (isValid) {
      // we can safely assume that there are two elements
      // the second element is the string, so we remove the quotations
      int forSubstring = echoCmd[1].length() - 1;
      String subber = echoCmd[1].substring(1, forSubstring);
      // then enqueue it
      cmdOPQ.enqueue(subber, true);
    }
  }

  /**
   * Validates the echo command prior to execution
   *
   * @return True iff the command is valid
   */
  private boolean validate() {
    // check the amount of arguments passed; if it's not two, then we should
    // return an error
    if (echoCmd.length != 2) {
      seeMan();
      return false;
    } else if (echoCmd[1].length() < 1) {
      // if for some reason, if the string is empty, then we just want the
      // user to see the man page
      seeMan();
      return false;
    } else {
      // otherwise, we can assume that there is at least two elements in the
      // array, where the second element is the supposed string

      // get the first character of the string
      String firstChar = echoCmd[1].substring(0, 1);
      // get the last character of the string
      String lastChar =
          echoCmd[1].substring(echoCmd[1].length() - 1, echoCmd[1].length());

      // there's three cases that we have to make sure of
      // the first case is if the first character is not a quotation
      boolean firstCase = !firstChar.equals("\"");
      // the second case is if the last character is not a quotation
      boolean secondCase = !lastChar.equals("\"");
      // the third case is if both cases are true but the string only has 1
      // character
      boolean thirdCase =
          !firstCase && !secondCase && echoCmd[1].length() == 1;
      // if either of the three cases do fail, we return an error
      if (firstCase || secondCase || thirdCase) {
        // then we just return an error
        seeMan();
        return false;
      }
    }
    // if it passes the validation tests, then just return true
    return true;
  }


  /**
   * Conveniently enqueues an error to the output queue
   */
  private void seeMan() {
    try {
      // just throw an error
      throw new InvalidCmdUseError("Please see the man page of echo");
    } catch (InvalidCmdUseError e) {
      // catch it then enqueue a message
      cmdOPQ.enqueue(e.toString(), false);
    }
  }

  /**
   * The man page of Echo
   *
   * @return the man page of echo
   */
  public static String getDoc() {
    String man = "";
    man += "ECHO\n\n";
    man += "NAME\n";
    man += "\techo - outputs a string\n";
    man += "SYNOPSIS\n";
    man += "\techo \"STRING\"\n";
    man += "\twhere STRING is a string that must be in quotations\n";
    return man;
  }
}
