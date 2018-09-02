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
import console.OutputQueue;

public abstract class Command {

  private String[] commandArgument;
  private Console c;
  protected OutputQueue cmdOPQ = new OutputQueue();

  /**
   * creates a default constructor, the purpose of this is for Man command to
   * call getDoc
   */
  public Command() {
  }

  /**
   * Create a constructor for the parent command class
   */
  public Command(Console console, String[] argument) {
    c = console;
    commandArgument = argument;
  }

  /**
   * Executes the abstract command
   */
  public abstract void execute();

  /**
   * Returns the OutputQueue of the command
   * @return the OutputQueue of the command
   */
  public OutputQueue getOutputQueue(){
    // just return the output command
    return cmdOPQ;
  }
}

