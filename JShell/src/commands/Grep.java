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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import console.Console;
import filesystem.Directory;
import filesystem.File;
import filesystem.FileSystem;
import filesystem.FileSystemObject;
import filesystem.InvalidPathError;
import filesystem.UnexpectedFsoError;

public class Grep extends Command {
  private FileSystem userFileSystem;
  private String[] commandArgument;

  /**
   * Creates a Grep object
   *
   * @param console the main console
   * @param command the original grep command parsed by spaces
   */
  public Grep(Console console, String[] command) {
    commandArgument = command;
    userFileSystem = console.getFileSystem();
  }

  /**
   * Passes all lines of a file or the files in a directory that matches the
   * regex to the output classes
   */
  @Override
  public void execute() {
    try {
      // check if command argument is less than 3
      this.verifyNumInput(commandArgument);
      String[] paths = {};
      String regex;
      int rFlag = 0;
      // set rflag on if -R is present in commandArgument
      if (commandArgument[1] == "-R") {
        rFlag = 1;
        // regex is the 3rd element from commandArgument
        regex = commandArgument[2];
        // get the list of paths
        paths = Arrays.copyOfRange(commandArgument, 3,
            commandArgument.length);
      }
      // else -R is not present in the commandArgument
      else {
        // regex is the 2nd element from commandArgument
        regex = commandArgument[1];
        // get the list of paths
        paths = Arrays.copyOfRange(commandArgument, 2,
            commandArgument.length);
      }
      List<String> allMatchedLines = new ArrayList<String>();
      for (String path : paths) {
        List<String> matchedLines = new ArrayList<String>();
        matchedLines =
            this.processPath(path, regex, rFlag, this.userFileSystem);
        // copy lines from matchedLines to allMatchedLines
        allMatchedLines.addAll(matchedLines);
      }
      // insert message to be sent to output if no lines matches the regex
      if (allMatchedLines.isEmpty()) {
        allMatchedLines.add("No lines match the regex");
      }
      // pass all matched lines to the output queue
      for (String line : allMatchedLines) {
        cmdOPQ.enqueue(line, true);
      }
    } catch (InvalidNumberOfArgumentsException e) {
      cmdOPQ.enqueue(
          e.toString() + ": grep requires a minimum of 3 user" + " inputs",
          false);
    } catch (MissingRException e) {
      cmdOPQ.enqueue(
          e.toString() + ": -R is required if path is a directory" + " path",
          false);
    } catch (PathDoesNotExistException e) {
      cmdOPQ.enqueue(e.toString() + ": path does not exist in filesystem",
          false);
    } catch (InvalidPathError e) {
      cmdOPQ.enqueue(e.toString() + ": not a valid path", false);
    } catch (UnexpectedFsoError e) {
      cmdOPQ.enqueue(e.toString(), false);
    } catch (IOException e) {
      cmdOPQ.enqueue(e.toString(), false);
    }
  }

  /**
   * Gets the lines from a file or from files in a directory that match the
   * regex
   *
   * @param path the file or directory path
   * @param regex the regex
   * @param rFlag indicates if -R is given or not by the user
   * @param fileSystem the user filesystem
   * @return lines from a file or files in a directory that match the regex
   * @throws PathDoesNotExistException
   * @throws InvalidPathError
   * @throws MissingRException
   * @throws UnexpectedFsoError
   * @throws IOException
   */
  public List<String> processPath(String path, String regex, int rFlag,
      FileSystem fileSystem) throws PathDoesNotExistException,
      InvalidPathError, MissingRException, UnexpectedFsoError, IOException {
    List<String> ret = new ArrayList<String>();
    // verify if path exists
    this.verifyPath(fileSystem, path);
    // if -R not given and path is a directory then throw exception
    if ((rFlag == 0) && userFileSystem.getFsoByPath(path).getClass()
        .getName() == "filesystem.Directory") {
      throw new MissingRException();
    }
    // if path is a file then process file
    else if (userFileSystem.getFsoByPath(path).getClass()
        .getName() == "filesystem.File") {
      File file = this.userFileSystem.getFileByPath(path);
      String fileContents = file.getFileContents();
      ret = this.processFile(fileContents, path, regex);
    }
    // else -R is present then process directory
    else {
      FileSystemObject fileSystemObject = fileSystem.getFsoByPath(path);
      ret =
          this.processDirectory(fileSystem, fileSystemObject, path, regex,
              ret);
    }
    return ret;
  }

  /**
   * Verify if the number of arguments given is at least 3
   *
   * @param commandArgument the command arguments
   * @throws InvalidNumberOfArgumentsException
   */
  public void verifyNumInput(String[] commandArgument)
      throws InvalidNumberOfArgumentsException {
    if (commandArgument.length < 3) {
      throw new InvalidNumberOfArgumentsException();
    }
  }

  /**
   * Verify if the given path exists in the file system
   *
   * @param userFileSystem the main file system
   * @param regexAndPath the string array containing the regex and path
   * @throws PathDoesNotExistException
   */
  public void verifyPath(FileSystem userFileSystem, String path)
      throws PathDoesNotExistException {
    if ((userFileSystem.isExistPath(path) == false) && (path != "/")) {
      throw new PathDoesNotExistException();
    }
  }

  /**
   * Gets all lines from a file that matches the regex
   *
   * @param fileContents the contents of the file
   * @param regexAndPath the string array containing the regex and path
   * @return the list of lines that match the regex
   * @throws UnexpectedFsoError
   * @throws InvalidPathError
   * @throws IOException
   */
  public List<String> processFile(String fileContents, String path,
      String regex) throws UnexpectedFsoError, InvalidPathError,
  IOException {
    List<String> ret = new ArrayList<String>();
    // checks if each line from fileContents matches the pattern
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(fileContents);
    String[] lines = fileContents.split("\n");
    for (String line : lines) {
      matcher.reset(line);
      if (matcher.find()) {
        ret.add(line);
      }
    }
    return ret;
  }

  /**
   * Gets lines from all files in the directory that matches the regex
   *
   * @param fileSystem the main file system
   * @param fileSystemObject a file or directory
   * @param regexAndPath the string arrray containing the regex and path
   * @param ret the list of lines that match the regex
   * @return the list of lines that match the regex
   * @throws UnexpectedFsoError
   * @throws InvalidPathError
   * @throws IOException
   */
  public List<String> processDirectory(FileSystem fileSystem,
      FileSystemObject fileSystemObject, String path, String regex,
      List<String> ret)
      throws UnexpectedFsoError, InvalidPathError, IOException {
    List<String> fileContentsList = new ArrayList<String>();
    /*
     * if fileSystemObject is a directory then process each file in the
     * directory and recursive call processDirectory on all sub directories
     */
    Directory directory = (Directory) fileSystemObject;
    // traverse through each sub-directory and file in directory
    for (int i = 0; i < directory.getChildren().size(); i++) {
      FileSystemObject fileOrDirectory = directory.getChildren().get(i);
      // if the file system object is a file then process the file
      if (fileOrDirectory.getClass().getName() == "filesystem.File") {
        File file = (File) fileOrDirectory;
        String fileContents = file.getFileContents();
        fileContentsList = this.processFile(fileContents, path, regex);
        for (String content : fileContentsList) {
          ret.add(fileSystem.printFsoPath(fileOrDirectory) + ":" + content);
        }
      }
      /*
       * else the file system object is a directory so recursive call
       * processDirectory and pass in the directory
       */
      else {
        this.processDirectory(fileSystem, fileOrDirectory, path, regex, ret);
      }
    }
    return ret;
  }

  /**
   * The man page of Grep
   *
   * @return the man page of grep
   */
  public static String getDoc() {
    String grep = "";
    grep += "GREP\n\n";
    grep += "NAME\n";
    grep += "\tgrep - print lines of a file/s that match a regex or "
        + "print lines of files in a directory that match a regex.\n"
        + "\t\tIf no lines match a regex then the message 'No lines"
        + " match the regex' is returned.\n";
    grep += "SYNOPSIS\n";
    grep += "\tgrep [-R] REGEX PATH...\n\n";
    grep += "\twhere -R indicates to recursively traverse the directory"
        + " if a directory path is given. If -R and a file path\n"
        + "\t\tare given then print the lines of the file that match the"
        + " regex.\n";
    grep += "\twhere REGEX is a regex\n";
    grep += "\twhere PATH is a file or directory\n";
    grep += "\twhere ... means multiple files or directories\n";
    grep += "\twhere [] means optional\n";
    return grep;
  }

}
