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

public class Exit extends Command {

  /**
   * creates a default constructor, the purpose of this is for Man command to
   * call getDoc
   */
  public Exit() {}

  /**
   * Constructor for Exit that takes in a console object and a string array
   * of commands. NOTE that the first element in command is assumed to be
   * 'exit'.
   */
  public Exit(Console mainConsole, String[] command) {}

  /**
   * Exits the program.
   */
  @Override
  public void execute() {
    System.exit(0);
  }

  /**
   * The man page of exit
   *
   * @return the man page of exit
   */
  public static String getDoc() {
   String man = "";
    man += "Exit\n\n";
    man += "NAME\n";
    man += "\texit - exit the current shell\n";
    man += "SYNOPSIS\n";
    man += "\texit [...]\n";
    man += "\twhere [...] are any arguments after exit";
    return man;
  }

}

