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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import commands.Command;
import java.util.Hashtable;

public class Man extends Command {

  private String[] commandArgument;
  private Hashtable<String, String> cmdNames;
  private String name;

  /**
   * creates a Man command
   *
   * @param console The main Console
   * @param argument The argument of the command line
   */
  public Man(Console console, String[] argument) {
    commandArgument = argument;
    cmdNames = console.getCmdHashtable();
  }

  /**
   * returns the documentation of the given command
   */
  public void execute() {
    // check the format of the argument
    if ((commandArgument.length != 2) || (!commandArgument[0].equals("man"))) {
      // print out an error if given improper format
      cmdOPQ.enqueue("Invalid Command Use: Please see the man page of man",
          false);
    } else {
      try {
        name = commandArgument[1];
        String capitalName = cmdNames.get(name);
        // check if the given name is valid or not
        if (capitalName == null) {
          // print the output if the given name is does not exist
          cmdOPQ.enqueue("man: " + name + ": no such command",false);
        }
        // if the given command name exist
        else {
          // find the command class using its name
          Class<?> newCmd = Class.forName(capitalName);
          // get the documentation of the command
          Method method = newCmd.getDeclaredMethod("getDoc");
          Object output = method.invoke(null);
          // output the documentation to the shell
          cmdOPQ.enqueue(output.toString(),true);
        }
      } catch (ClassNotFoundException | NoSuchMethodException
            | SecurityException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException e) {
        // TODO Change this later; just wanted a print message
        cmdOPQ.enqueue("Whoopsie daisy",false);
      }
    }
  }

  /**
   * returns the documentation of the Man class
   *
   * @return instruction The documentation for Man class
   */
  public static String getDoc() {
    // initialize and create a return string
    String instruction = "";
    instruction += ("MAN:\nNAME:\n\treturns the documentation" + " of the given"
        + " command" + "SYNOPSIS\n\tman COMMAND");
    return instruction;
  }
}
