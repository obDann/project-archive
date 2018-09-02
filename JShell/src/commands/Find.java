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
import filesystem.FileSystemObject;
import filesystem.InvalidPathError;

public class Find extends Command {

  private String[] commandArgument;
  private Console c;
  private int length;
  private FileSystem fs;
  private String type;
  private String name;
  private String[] paths;


  /**
   * creates a Find command
   *
   * @param console The main Console
   * @param argument The argument of the command line
   */
  public Find(Console console, String[] argument) {
    // store console, argument, length of the argument, name of the object
    c = console;
    commandArgument = argument;
    fs = c.getFileSystem();
    length = argument.length;
    // if the argument is in the right format, then store the argument
    // information
    if ((commandArgument[0].equals("find")) || (length == 6)
        || (commandArgument[length - 4].equals("-type"))
        || (commandArgument[length - 2].equals("-name"))
        || ((commandArgument[length - 3].equals("d"))
            && (commandArgument[length - 3].equals("f")))) {
      // find and store the name of the object we are looking for
      name = commandArgument[length - 1].replace("\"", "");
      // find and store the type of the object
      if (commandArgument[length - 3].equals("f")) {
        type = "File";
      } else if (commandArgument[length - 3].equals("d")) {
        type = "Directory";
      }
      // find an store the number of the path given
      int numPath = length - 5;
      paths = new String[numPath];
      // find and store the path given
      System.arraycopy(commandArgument, 1, paths, 0, numPath);
    }
  }

  /**
   * returns the name of the object we are looking for if the object exists in
   * the given path(s), returns an error if such object does not exist
   */
  public void execute() {
    // check the format of the argument
    if ((!commandArgument[0].equals("find")) || (length < 6)
        || (!commandArgument[length - 4].equals("-type"))
        || (!commandArgument[length - 2].equals("-name"))
        || ((!commandArgument[length - 3].equals("d"))
            && (!commandArgument[length - 3].equals("f")))) {
      // throw an error if given an improper format
      cmdOPQ.enqueue("Invalid Command Use: Please see the man page of find",
          false);
    } else {
      // loop through the current absolute or relative path
      for (String i : paths) {
        try {
          // initialize the return statement
          FileSystemObject result = new FileSystemObject();
          // find the address of the given path
          FileSystemObject p = fs.getFsoByPath(i);
          // search for the object we are looking for starting from the given
          // path
          FileSystemObject find = fs.findObj(p, name);
          // find the type of object and check if its exactly the object we are
          // looking for
          String t = find.getClass().getName();
          if (t.equals("filesystem." + type)) {
            // if the found object is the one we want, then set the return
            // result to the found object
            result = find;
          }
          // print the found object
          printResult(result.getFileSystemObjectName(), i);
        }
        // print an error if the path given is not valid
        catch (InvalidPathError e) {
          cmdOPQ.enqueue("find: " + i + ": no such file or directory",false);
        }
      }
    }
  }

  /**
   * returns the result of finding, given the name of the found FileSystemObject
   * and the name of the FileSystemObject that we are looking for
   */
  private void printResult(String f, String i) {
    if (f != null) {
      cmdOPQ.enqueue(f,true);
    }
    // print an error if such object is not found
    else {
      cmdOPQ.enqueue("find: " + name + " : no such " + type + " in " + i,false);
    }
  }

  /**
   * returns the documentation of the Find class
   *
   * @return instruction The documentation for Find class
   */
  public static String getDoc() {
    // initialize and create a return string
    String instruction = "";
    instruction += ("FIND:\nNAME\n\treturns the name of the file or directory"
        + " we are looking for in multiple paths if \n"
        + "\tsuch object exists. returns an error if such file or directory"
        + " does not exist\n"
        + "SYNOPSIS\n \tfind PATH... -type [f|d] -name expression\n"
        + "EXAMPLE:\n \t>>> find /a1 /a1 -type f -name \"cr\"" + "\n\tcr\n\tcr"
        + "\n\t>>>find A1 A2 -type d -name \"user1\"" + "\n\tuser1\n\tfind:"
        + " user1: no such Directory in A2");
    return instruction;

  }
}
