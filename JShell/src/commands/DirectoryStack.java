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

import java.util.Stack;
import filesystem.BadFileSystemObjectNameError;
import filesystem.FileSystem;
import filesystem.FileSystemObject;

public class DirectoryStack {

  private Stack<FileSystemObject> stack
  = new Stack<FileSystemObject>();

  public Stack<FileSystemObject> getStack() {
    return stack;
  }

  /**
   * push current directory to the stack
   */
  public void pushDir(FileSystem fileSystem) {
    // push current working directory to stack
    stack.push(fileSystem.getCurrentPath());
  }

  /**
   * Pop top most directory from directory stack
   *
   * @throws BadFileSystemObjectNameError
   * @throws PopFromEmptyStackError
   */
  public FileSystemObject popDir(FileSystem fileSystem)
      throws BadFileSystemObjectNameError, PopFromEmptyStackError {
    // check if stack is empty
    if (stack.isEmpty()) {
      String errorMessage = "popd: directory stack empty";
      throw new PopFromEmptyStackError(errorMessage);
    }
    /**
     * pop top most directory from directory stack and set it as the
     * current working directory
     */
    return stack.pop();
  }

  /**
   * checks if directory stack is empty
   */
  public boolean isEmpty() {
    return this.stack.empty();
  }

}
