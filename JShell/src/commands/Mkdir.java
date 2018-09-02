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

import java.util.Arrays;

public class Mkdir extends Command {
  private String mkdirCmd[];
  private FileSystem mkdirFs;
  private String mkdirName;
  private String mkdirPath = "";

  /**
   * creates a default constructor, the purpose of this is for Man command
   * to call getDoc
   */
  public Mkdir() {
  }

  /**
   * Creates a Mkdir object
   *
   * @param mainConsole the main console
   * @param command     the original mkdir command parsed by spaces
   */
  public Mkdir(Console mainConsole, String command[]) {
    // assign the file system
    mkdirFs = mainConsole.getFileSystem();
    // and assign the commands
    mkdirCmd = command;
  }

  /**
   * Makes directory/directories accordingly to the command line arguments
   */
  public void execute() {
    if (validate()) {
      // next, we have to go through the list
      String dir[] = Arrays.copyOfRange(mkdirCmd, 1, mkdirCmd.length);
      // then we go through the directory
      for (String directory : dir) {
        createDirectory(directory);
      }
    }
  }

  /**
   * Returns true iff the mkdir command is being used properly
   *
   * @return
   */
  private boolean validate() {
    // we have to make sure that the directory is being used properly
    // so first, we have to make sure that the command has at least two
    // elements
    if (mkdirCmd.length < 2 || !mkdirCmd[0].equals("mkdir")) {
      try {
        // just throw an error if this were to happen
        throw new InvalidCmdUseError("Please see the man page for mkdir");
      } catch (InvalidCmdUseError e) {
        // then catch it so that it's enqueued in our output queue
        cmdOPQ.enqueue(e.toString(), false);
      }
      // then return false since the command is not being used properly
      return false;
    }
    // otherwise, just return true
    return true;
  }

  /**
   * creates a single directory
   *
   * @param directoryName the name of a new directory in the filesystem
   */
  private void createDirectory(String directoryName) {
    // from here, we have to check if we can separate the name of the
    // directory and the directory that we want to make
    separatePathAndDirectory(directoryName);
    // now we have mkdirInDir and mkDirToMake
    try {
      // we want to get the directory for the directory we want to make
      Directory ourDir = mkdirFs.getDirectoryByPath(mkdirPath);
      // then we want to make the directory indicated
      Directory toMake = new Directory(mkdirName);
      // then we want to store the directory
      ourDir.storeNewFileSystemObject(toMake);
    } catch (UnexpectedFsoError e) {
      // if this is ran, then it means that a file is returned
      String msg = "mkdir: cannot create directory '" + directoryName + "': "
          + "File exists";
      cmdOPQ.enqueue(msg, false);
    } catch (InvalidPathError e) {
      // if this is ran, then it means that it is an invalid path
      cmdOPQ.enqueue(e.toString(), false);
    } catch (BadFileSystemObjectNameError e) {
      // if this is ran, this is in fault of the user since it is a bad fso
      // name
      cmdOPQ.enqueue(e.toString(), false);
    } catch (DuplicateFileSystemObjectNameError e) {
      // if this is ran, there is just another fso with the same name
      cmdOPQ.enqueue(e.toString(), false);
    }
  }

  /**
   * Separates the Path from the directory to make
   * i.e. we want to make the directory a/b/c
   * mkdirInDir = a/b
   * mkdirName = c
   *
   * @param originalPath the original path to separate
   */
  private void separatePathAndDirectory(String originalPath) {
    mkdirName = "";
    mkdirPath = "";
    // what we want to do is get the directory before the indicated directory

    // we have to determine if its an absolute path or relative because we
    // have to get the director/*  *//*y before the actual file, but using
    // lastIndexOf will cause QA errors
    String firstChar = originalPath.substring(0, 1);
    if (firstChar.equals("/")) {
      // we just set the echo path to "/" for now
      mkdirPath += firstChar;
    }

    // from here, we want to split the path by "/"
    String allDirs[] = originalPath.split("/");
    // and get the last element because this is what we may potentially
    // append/overwrite to
    String lastEle = allDirs[allDirs.length - 1];
    mkdirName = lastEle;

    // we go through a for loop
    for (String dir : allDirs) {
      // we check if the name of the directory is not the file name
      // this is done by reference, not by value, hence the assignment for
      // lastEle
      if (dir != lastEle && !dir.isEmpty()) {
        // just append the mkdirInDir with the looped dir with a "/"
        mkdirPath += dir + "/";
      }
    }
    // if for some reason, the mkdirInDir is blank, it's just a current
    // directory
    if (mkdirPath.equals("")) {
      mkdirPath = ".";
    }
  }

  /**
   * The man page of mkdir
   *
   * @return the man page of mkdir
   */
  public static String getDoc() {
    String man = "";
    man += "MKDIR\n\n";
    man += "NAME\n";
    man += "\tmkdir - makes directories\n";
    man += "SYNOPSIS\n";
    man += "\tmkdir PATH...\n\n";
    man += "\twhere PATH is a directory that is to be made\n";
    man += "\twhere ... means multiple";
    return man;
  }
}
