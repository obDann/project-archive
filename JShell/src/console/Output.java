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

import filesystem.*;
import javafx.util.Pair;

public class Output {

  private FileSystem fs;
  private String mode = "";
  private String outputPath = "";
  private String outputFileName = "";
  private String originalPath = "";
  private File outputFile;

  public Output(FileSystem fileSys) {
    fs = fileSys;
  }

  /**
   * Validates the redirection.
   *
   * @param direct is an array that is either empty or has its first element
   *               as the string of either ">" or ">>"
   */
  public void validateDirection(String direct[]) throws InvalidDirectionError {
    // first check if the array is empty
    if (direct.length == 0) {
      // our mode is at print
      mode = "print";
    } else if (direct.length >= 2) {
      // save the first two elements (this is done in the event of refactoring)
      String direction = direct[0];
      originalPath = direct[1];

      // check if the direction is ">"
      if (direction.equals(">")) {
        // our mode is to overwrite
        mode = "overwrite";
      } else {
        // otherwise our mode is to append
        mode = "append";
      }
      // now we want to get the directory path and the file path, assigning
      // outputPath and outputFileName
      separatePathAndFile(originalPath);
      validator();
    } else {
      throw new InvalidDirectionError("No path to redirect to");
    }
  }

  /**
   * Executes redirection of any output from the output queue
   *
   * @param opQ the output queue from a command
   */
  public void outputMe(OutputQueue opQ) {
    // go through the output queue
    while (!opQ.isEmpty()) {
      // get the string and its determining variable for stdout
      Pair<String, Boolean> holder = opQ.dequeue();
      String theOutput = holder.getKey();
      boolean determinant = holder.getValue();
      // by priority, we check if the message is standard out OR if the
      // message just needs to be printed
      if (!determinant || mode.equals("print")) {
        // then we just print the output
        System.out.println(theOutput);
      } else {
        // otherwise we have to check if the mode is "overwrite"
        if (mode.equals("overwrite")) {
          // we just overwrite the file with the output queue's output
          outputFile.overwriteFile(theOutput + "\n");
          // and the mode changes to append to avoid any bugs
          mode = "append";
        }
        // otherwise, the mode is to append
        else if (mode.equals("append")) {
          // then we just append the file with the output queue's output
          outputFile.addFileContents(theOutput + "\n");
        }
      }
    }
    // after the execution is done, we just reset our local variables
    resetLocals();
  }


  /**
   * Separates the String; assigning echoPath a String that is supposedly a
   * directory the passed file that the file is in i.e.
   * the original path is: a/b/c
   * outputPath = a/b
   * outputFileName = c
   *
   * @param originalFile the original path the user put in
   */
  private void separatePathAndFile(String originalFile) {
    // what we want to do is get the directory before the indicated file

    // we have to determine if its an absolute path or relative because we
    // have to get the director/*  *//*y before the actual file, but using
    // lastIndexOf will cause QA errors
    String firstChar = originalFile.substring(0, 1);
    if (firstChar.equals("/")) {
      // we just set the echo path to "/" for now
      outputPath += firstChar;
    }

    // from here, we want to split the path by "/"
    String allDirs[] = originalFile.split("/");
    // and get the last element because this is what we may potentially
    // append/overwrite to
    String lastEle = allDirs[allDirs.length - 1];
    outputFileName = lastEle;

    // we go through a for loop
    for (String dir : allDirs) {
      // we check if the name of the directory is not the file name
      // this is done by reference, not by value, hence the assignment for
      // lastEle
      if (dir != lastEle && !dir.isEmpty()) {
        // just append the echoPath with the looped dir with a "/"
        outputPath += dir + "/";
      }
    }
    // if for some reason, the echoPath is blank, it's just a current
    // directory
    if (outputPath.equals("")) {
      outputPath = ".";
    }
  }

  /**
   * Populates local variables if the path is valid, otherwise throw an
   * error if the path is invalid for direction
   *
   * @throws InvalidDirectionError when the path is invalid
   */
  private void validator() throws InvalidDirectionError {
    // we assume that seperatePathAndFile are populated via separatePathAndFile

    // attempt to get the directory
    try {
      Directory validDirectory = fs.getDirectoryByPath(outputPath);
      // we attempt to get the file
      try {
        FileSystemObject attemptFile = validDirectory.getChild(outputFileName);
        // then we have to check if the potential file is a file
        if (attemptFile instanceof File) {
          // we just set our output file to be the attempt to get the file
          outputFile = (File) attemptFile;
        } else {
          // if it isn't a file, we have to throw an error because it's a
          // directory that exists
          String savePath = originalPath;
          resetLocals();
          throw new InvalidDirectionError(savePath + " is a directory"
              + " and cannot be used in the redirection of output");
        }
      } catch (NonExistantChildError e) {
        try {
          // if we run into this line, then we make a file
          outputFile = new File(outputFileName);
          // then we add the file to the directory
          validDirectory.storeNewFileSystemObject(outputFile);
        } catch (BadFileSystemObjectNameError er) {
          resetLocals();
          throw new InvalidDirectionError(er.toString());
        } catch (DuplicateFileSystemObjectNameError er) {
          // this line should not run since we already checked for it
        }
      }
    } catch (InvalidPathError | UnexpectedFsoError e) {
      String savePath = originalPath;
      resetLocals();
      throw new InvalidDirectionError("Invalid Path: " + savePath);
    }
  }

  /**
   * Reset local variables for a fresh instance of an output
   */
  private void resetLocals() {
    mode = "";
    outputPath = "";
    outputFileName = "";
    originalPath = "";
    outputFile = null;
  }

}

