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
package console;

// JDK imports.
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.Hashtable;
import javafx.util.Pair;
import java.util.Collections;

// Project imports.
import filesystem.*;
import commands.*;

public class Console {

  // Instance var for FileSystem.
  private final FileSystem fileSys;
  // Instance var for HistoryContainer.
  private final HistoryContainer history = new HistoryContainer();
  // Instance var for the hashtable to hold command keywords and command class
  // names.
  private final Hashtable<String, String> cmdHashtable
      = new Hashtable<String, String>();
  // Instance var for ValidData? Does ValidData need args?
  private final ValidData validator;
  // Instance var for Output class.
  private final Output outputter;

  /**
   * Constructor for Console. Takes in no parameters.
   */
  public Console(FileSystem fileSys) {
    this.fileSys = fileSys;
    outputter = new Output(fileSys);
    // Populate hashtable with all valid command keywords and according class
    // names.
    populateHashtable();
    // Give the hashtable to validator as well
    validator = new ValidData(cmdHashtable);
  }

  /**
   * Returns this console's file system.
   *
   * @return A reference to this console's filesystem.
   */
  public FileSystem getFileSystem() {
    return fileSys;
  }

  /**
   * Returns this console's history container.
   *
   * @return A reference to this console's history container.
   */
  public HistoryContainer getHistoryContainer() {
    return history;
  }

  /**
   * Returns this console's hashtable containing the mappings command string to
   * full command class name.
   *
   * @return A reference to this console's command keywords to command class
   *         names hashtable.
   */
  public Hashtable<String, String> getCmdHashtable() {
    return cmdHashtable;
  }

  /**
   * Starts the input loop for user's to enter commands into the console.
   */
  public void startInputLoop() {
    // Initialize a class to read from standard input.
    Scanner inputReader = new Scanner(System.in);
    String currWorkingDir;

    do {
      // Print out a prompt.
      // Want to show current working directory in prompt.
      currWorkingDir = fileSys.printCurrentPath();
      System.out.printf("%s# ", currWorkingDir);
      String input = inputReader.nextLine();

      // If no input, reprompt user.
      if (validator.isNoInput(input)) {
        continue;
      }

      // Parse the input into an ArrayList.
      ArrayList<String> result = validator.parseString(input);

      try {
        // Perform any neccessary command substitutions.
        Pair<String, ArrayList<String>> pair = substituteCommand(input, result);
        input = pair.getKey();
        result = pair.getValue();

        // Store the input string in the history container. Also trim the
        // leading whitespace. This will not happen if the !substitution is
        // invalid.
        history.push(input.trim());

        // Execute command based on the parsed input.
        executeCommand(result);

        // If substitution fails, we must catch these errors and deal with them.
      } catch (InvalidCommandError ice) {
        System.out.printf("%s\n", ice.getMessage());
      } catch (InvalidRangeError ire) {
        System.out.printf("%s\n", ire.getMessage());
      }

    } while (true);  // This loop will break once the exit command is typed.
    // The next line must be commented out for Exit's System.exit to work.
    // inputReader.close();
  }

  /**
   * Given a command in array form, executes the command if it is a valid
   * command and prints any output given by command.
   *
   * @param result The string input from entered by a user parsed into an
            ArrayList.
   */
  private void executeCommand(ArrayList<String> result) {
    // Then need to perform any command substitutions neccessary.
    // Then parse the result list into two lists by the first ">" or ">>"; the
    // first half will contain all arguments before the first ">" or ">>"
    ArrayList<List<String>>
        pair = validator.splitListByRedirectionToken(result);
    List<String> firstHalf = pair.get(0);
    List<String> redirection = pair.get(1);

    // Result should never be empty as for our purposes, input should always
    // contain at least one non-whitespace character.
    assert !result.isEmpty();

    // Validate the command given (i.e. if command keyword is valid).
    try {
      // Verify that the command exists. Error will be thrown if not.
      validator.commandExists(result);

      // Verify that redirection works. Error will be thrown if not. Convert
      // into array first.
      String[] redirectionArray = new String[redirection.size()];
      redirectionArray = redirection.toArray(redirectionArray);
      outputter.validateDirection(redirectionArray);

      // Execute the command.
      Command currCommand = assignCommand(firstHalf);
      currCommand.execute();

      // Then output the command's results.
      OutputQueue opq = currCommand.getOutputQueue();
      outputter.outputMe(opq);

    // If the command doesn't exist, an error is thrown; catch it.
    } catch (InvalidCommandError ice) {
      System.out.printf("%s\n", ice.getMessage());
    // If the redirection is not valid, an error is thrown; catch it.
    } catch (InvalidDirectionError ide) {
      System.out.printf("%s\n", ide.getMessage());
    }
  }

  /**
   * Takes in  a command string and its parsed array form and substitues all
   * !number instances with their appropriate command substitution. number
   * refers to the history number of a command in history. Returns the resulting
   * commands with the substituions in place.
   *
   * @param  input String form of a command.
   * @param  parsedInput Array of string form of a command.
   * @return A pair containing the substituted input and parsedInput.
   * @throws InvalidCommandError If number in !number is not a number.
   * @throws InvalidRangeError If number in !number is not found in history.
   */
  private Pair<String, ArrayList<String>> substituteCommand(String input,
      ArrayList<String> parsedInput)
      throws InvalidCommandError, InvalidRangeError {
    // Iterate through the parsed input looking for strings starting with '!'.
    for (String arg : parsedInput) {
      if (arg.startsWith("!")) {

        // Check if string is of form !<integer>
        if (arg.matches("![0-9]+")) {
          // Get the number part from the string.
          int historyNumber = Integer.parseInt(arg.substring(1));
          // Try to get the history entry of that number. This will throw an
          // error if it is not possible.
          String entry = history.getSingleCmd(historyNumber);
          // Replace the arg substring in input with entry.
          input = input.replace(arg, entry);
          // Replace it in the array as well.
          Collections.replaceAll(parsedInput, arg, entry);

        // Otherwise, we throw an error for invalid use of '!'
        } else if (!arg.matches("!")) {
          throw new InvalidCommandError(arg + " is invalid syntax");
        }

      }
    }

    return new Pair<>(input, parsedInput);
  }

  /**
   * Given an arraylist of arguments from the command line (including the
   * command keyword), assigns the appropriate command object to a command
   * reference and returns it. NOTE Assumes that command keyword is correct.
   *
   * @param  arguments Command line arguments, each one an element.
   * @return A reference to the appropriate ommand object.
   */
  private Command assignCommand(List<String> arguments) {
    // Get the command keyword from arguments.
    String commandKeyword = arguments.get(0);

    // Now want to convert this into an array
    String[] argumentsArray = new String[arguments.size()];
    argumentsArray = arguments.toArray(argumentsArray);

    // Initialize and declare a Command to return.
    Command commandObj = null;

    // Now attempt to create a class from argument keyword.
    try {
      // Get the string of the class name from the hashtable.
      // NOTE that this needs the hashtable to be populated.
      String cmdClassString = cmdHashtable.get(commandKeyword);

      Class<?> cmdClass = Class.forName(cmdClassString);

      // Need to specify the argument types of the command class constructor.
      Constructor<?> cmdConstructor
          = cmdClass.getConstructor(this.getClass(), argumentsArray.getClass());

      // Then need to get the command object itself.
      commandObj = (Command) cmdConstructor.newInstance(this, argumentsArray);

    // Bunch of exception handling.
    } catch (ClassNotFoundException cnfe) {
      System.out.println(cnfe.getMessage());
    } catch (NoSuchMethodException nsme) {
      System.out.println(nsme.getMessage());
    } catch (InstantiationException ie) {
      System.out.println(ie.getMessage());
    } catch (IllegalAccessException iae) {
      System.out.println(iae.getMessage());
    } catch (InvocationTargetException ite) {
      System.out.println(ite.getMessage());
    }

    return commandObj;
  }

  /**
   * Populates the command hashtable of Console with all of the appropriate
   * command classes.
   */
  private void populateHashtable() {
    // Add the appropriate command keywords and command class names to the
    // command hashtable.
    String packageName = "commands.";
    cmdHashtable.put("cat", packageName + "Cat");
    cmdHashtable.put("echo", packageName + "Echo");
    cmdHashtable.put("ls", packageName + "Ls");
    cmdHashtable.put("mkdir", packageName + "Mkdir");
    cmdHashtable.put("pushd", packageName + "Pushd");
    cmdHashtable.put("popd", packageName + "Popd");
    cmdHashtable.put("pwd", packageName + "Pwd");
    cmdHashtable.put("cd", packageName + "Cd");
    cmdHashtable.put("history", packageName + "History");
    cmdHashtable.put("man", packageName + "Man");
    cmdHashtable.put("tree", packageName + "Tree");
    cmdHashtable.put("find", packageName + "Find");
    cmdHashtable.put("cp", packageName + "Cp");
    cmdHashtable.put("mv", packageName + "Mv");
    cmdHashtable.put("curl", packageName + "Curl");
    cmdHashtable.put("grep", packageName + "Grep");
    cmdHashtable.put("exit", packageName + "Exit");
  }

}

