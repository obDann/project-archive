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
import commands.HistoryContainer;
import java.util.List;

public class History extends Command {

  // Need instance for at least the history container and cmds from console.
  private HistoryContainer histCont;
  private String[] cmds;

  /**
   * creates a default constructor, the purpose of this is for Man command to
   * call getDoc
   */
  public History() {
  }

  /**
   * Constructor for History that takes in a console object and a string array
   * of commands. NOTE that the first element in command is assumed to be
   * 'history'.
   */
  public History(Console mainConsole, String[] command) {
    histCont = mainConsole.getHistoryContainer();
    cmds = command;
  }

  /**
   * Executes the history command with either no arguments or a non-negative int
   * argument to print out a history of entered commands. Output is printed to
   * shell.
   */
  public void execute() {
    // Declare the things to be enqueued into the OutputQueue.
    String contents = "";
    boolean direction = true;

    // Try to execute the command.
    try {
      // Try to find the length of the command if it is valid.
      int cmdLength = verifyCommand();

      // If the length of command is 1, set number of commands to display to
      // that.
      // Otherwise the length is 2, so parse the second arg to an int and set
      // number of commands to display. NOTE This needs to be the minimum of the
      // integer parsed and the size of the command container.
      int histContSize = histCont.getSize();
      int displaySize
          = (cmdLength == 1) ? histContSize : Math.min(histContSize,
              Integer.parseInt(cmds[1]));

      // Try to get the list of cmds and cmd numbers both of same size of
      // commands to display.
      List<String> prevCmds = histCont.getHistCmd(displaySize);
      List<Integer> prevCmdNums = histCont.getHistNum(displaySize);
      // Enqueue the history cmd lists s.t. the most cmds are displayed at the
      // end and the numbers come before the command.
      for (int i = 0; i < displaySize; i++) {
        contents
            += String.format("%d\t%s\n", prevCmdNums.get(i), prevCmds.get(i));
      }
    // Or we don't have a valid argument count for the command given.
    } catch (InvalidCmdUseError ivue) {
      contents = "Please see man page for history";
      direction = false;
    // Or argument given for number is not a valid int.
    } catch (NumberFormatException nfe) {
      contents = "Please see man page for history";
      direction = false;
    // Or we don't have a valid number of commands argument given.
    } catch (InvalidRangeError ire) {
      contents = "Please see man page for history";
      direction = false;
    }

    // Enqueue the output of the history command.
    cmdOPQ.enqueue(contents, direction);
  }

  /**
   * Return the length of the commmand given, where length is just number of
   * arguments given (including the command keyword).
   *
   * @return Number of arguments
   * @throws InvalidCmdUseError if command length is not 1 or 2.
   */
  public int verifyCommand() throws InvalidCmdUseError {
    // Verify that the input is in fact of correct length, either 1 or 2
    if (cmds.length == 1 || cmds.length == 2) {
      return cmds.length;
    }
    // Otherwise, throw an error.
    else {
      throw new InvalidCmdUseError("Please see man page for history");
    }
  }

  /**
   * The man page of History
   *
   * @return the man page of history
   */
  public static String getDoc() {
   String man = "";
    man += "HISTORY\n\n";
    man += "NAME\n";
    man += "\thistory - if no number are given, then print out the most recent";
    man += " commands in the shell\n";
    man += "\nIf a number k greater or equal to zero is given, then it will";
    man += " print out the most recent k\n";
    man += "commands in the shell, one command per line";
    man += "SYNOPSIS\n";
    man += "\thistory [number]\n";
    man += "\twhere [number] is number greater or equal to zero";
    return man;
  }

}

