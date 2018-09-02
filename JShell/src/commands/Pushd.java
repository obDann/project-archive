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

public class Pushd extends Command {

  private FileSystem userFileSystem;
  private DirectoryStack directoryStack;
  private String[] cmds;

  private static String doc = "PUSHD\n\nName\n     pushd - saves the "
      + "current working directory and creates a new\n     "
      + "current working directory\nSynopsis\n     "
      + "The current working directory is pushed onto a directory"
      + " stack and\n     the current working directory is changed"
      + " to the given input which may\n     be in the form of the"
      + " directory name, the absolute or relative path\n     "
      + "of the directory.";

  /**
   * Creates a pushd object
   *
   * @param console the main console used in the shell
   * @param command the initial command for pushd parsed by spaces
   */
  public Pushd(Console console, String[] command) {
    cmds = command;
    userFileSystem = console.getFileSystem();
    directoryStack = userFileSystem.getStack();
  }

  /**
   * pushes old current working directory to stack and changes given
   * directory to new current working directory
   */
  public void execute() {
    // Try to execute the command.
    try {
      // Get the second argument from command.
      String directoryName = verifyCommand();
      // if that directory exists in file system
      // get the directory that is given by the user
      FileSystemObject directory;
      directory = userFileSystem.getDirectoryByPath(directoryName);
      // push current working directory to stack
      directoryStack.pushDir(userFileSystem);
      // change current working directory to the given directory name
      userFileSystem.setCurrentObj(directory);

    // Otherwise, we have errors we need to catch.
    // We could have a InvalidCmdUseError.
    } catch (InvalidCmdUseError icue) {
      cmdOPQ.enqueue(icue + ". Please see man page for pushd", false);
    // We could have an UnexpectedFsoError
    } catch (UnexpectedFsoError ufse) {
      cmdOPQ.enqueue(ufse + ". Please see man page for pushd" , false);
    // We could have an InvalidPathError
    } catch(InvalidPathError ipe) {
      cmdOPQ.enqueue(ipe + ". Please see man page for pushd" , false);
    }

  }

  /**
   * Return the length of the commmand given, where length is just
   * number of arguments given (including the command keyword).
   *
   * @return Second argument in command input
   * @throws InvalidCmdUseError if command length is not 2.
   */
  public String verifyCommand() throws InvalidCmdUseError {
    // Verify that the input is in fact of correct length, 2.
    if (cmds.length == 2) {
      return cmds[1];
    }
    // Otherwise, throw an error.
    else {
      throw new InvalidCmdUseError("Please see man page for pushd");
    }
  }

  /**
   * Returns documentation for the pushd command
   *
   * @return pushd command documentation
   */
  public static String getDoc() {
    return doc;
  }
}
