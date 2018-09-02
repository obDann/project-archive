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

public class Tree extends Command {
  private String[] commandArgument;
  private FileSystem userFileSystem;
  private static String doc = "TREE\n\nName\n     tree - display the "
      + "entire file system as a tree\nSYNOPSIS\n     Starting from "
      + "the root directory, display the entire file system as\n     "
      + "a tree, where every level of the tree is indented.";

  /**
   * Creates a Tree object
   *
   * @param console the main console used in the shell
   * @param command the inital command for tree parsed by spaces
   */
  public Tree(Console console, String command[]) {
    userFileSystem = console.getFileSystem();
    commandArgument = command;
  }

  /**
   * Executes tree traversal method
   */
  public void execute() {
    if (commandArgument.length != 1) {
      System.out.println("tree: requires no user input");
    }
    else {
      this.treeTraversal(userFileSystem.getRootDir(), 0);
    }
  }

  /**
   * Prints the entire file system
   *
   * @param fileSystemObject
   * @param treeLevel
   */
  public void treeTraversal(FileSystemObject fileSystemObject,
      int treeLevel) {
    // determine how many indents are needed based on the tree level
    String space = "";
    for (int i = 0; i < treeLevel * 4; i++) {
      space += " ";
    }
    treeLevel++;
    System.out.println(space +
        fileSystemObject.getFileSystemObjectName());
    // recall treeTraversal method for each child that is a directory
    if (fileSystemObject.getClass().getName()
        == "filesystem.Directory") {
      Directory directory = (Directory) fileSystemObject;
      for (int i = 0; i < directory.getChildren().size(); i++) {
        treeTraversal(directory.getChildren().get(i), treeLevel);
      }

    }

  }

  /**
   * Returns documentation of tree command
   *
   * @return tree command documentation
   */
  public static String getDoc() {
    return doc;
  }

}
