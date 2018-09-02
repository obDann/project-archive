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

public class Popd extends Command {
  private FileSystem userFileSystem;
  private FileSystemObject topMostDirectory;
  private DirectoryStack directoryStack;
  private String[] commandArgument;
  private static String doc = "POPD\n\nName\n     popd - changes the"
      + "current working directory to the top most directory\n"
      + "     in the directory stack\nSynopsis\n     Removes the"
      + "top most directory in the directory stack and sets it as\n"
      + "     the current working directory. If the directory stack"
      + "is empty then an\n     error message is the output.";

  /**
   * Creates a popd object
   *
   * @param console the main console used in the shell
   * @param command the initial command for popd parsed by spaces
   */
  public Popd(Console console, String[] command) {
    commandArgument = command;
    userFileSystem = console.getFileSystem();
    directoryStack = userFileSystem.getStack();
  }

  /**
   * pops top most directory from directory stack and makes that directory
   * stack the current working directory
   */
  public void execute() {
    if (commandArgument.length != 1) {
      cmdOPQ.enqueue("popd: requires no user input", true);
    } // pop top most directory from directory stack
    else {
      try {
        topMostDirectory = directoryStack.popDir(userFileSystem);
        // make topMostDirectory to current working directory
        userFileSystem.setCurrentObj(topMostDirectory);
      } catch (PopFromEmptyStackError | BadFileSystemObjectNameError e) {
        cmdOPQ.enqueue(e.toString() + ": popd: directory stack empty", false);
      }
    }
  }

  /**
   * Returns documentation for the popd command
   * 
   * @return popd command documentation
   */
  public static String getDoc() {
    return doc;
  }

}
