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

import java.util.List;
import java.util.ArrayList;

public class HistoryContainer {

  // **********************************************************
  // Attirbutes
  // **********************************************************

  // Need an attribute to keep track of the command number of a command.
  // This will initially be 1 upon creation of the history container. This will
  // be used in conjunction with maxSize to find the command number of a command
  // in the HistoryContainer
  private int commandCount = 1;

  // Need an attribute to keep track of the max size of the container.
  // This is hardcoded for now (use 1000 similarily to linux).
  private int maxSize = 1000; // TESTING Use 10

  // Need some sort of way to store the various commands.
  private ArrayList<String> historyCmd = new ArrayList<String>();

  // Need some sort of way to store the various history numbers of each
  // command.
  private ArrayList<Integer> historyNum = new ArrayList<Integer>();

  // **********************************************************
  // Methods
  // **********************************************************

  /**
   * Returns the total number of commands entered in console.
   *
   * @return The total number of commands entered in console.
   */
  public int getCmdCount() {
    return commandCount - 1; // Since command count starts off at 1.
  }

  /**
   * Returns the number of commands currently stored in this history container.
   *
   * @return The number of commands currently stored in this history container.
   */
  public int getSize() {
    // The size of our command array and the array that holds the command number
    // should be the same.
    assert historyCmd.size() == historyNum.size();
    return historyCmd.size();
  }

  /**
   * Return the max capacity of this history container.
   *
   * @return THe maximum capacity of this history container.
   */
  public int getMaxSize() {
    return maxSize;
  }

  // Private method that checks if max size has been exceeded or reached.
  /**
   * Checks if we are at or have exceeded the maximum size of this history
   * container.
   *
   * @return True iff we are at or have exceeded maximum capacity of this
   *         history container.
   */
  private boolean exceededMaxSize() {
    return getCmdCount() >= getMaxSize();
  }

  /**
   * Pushes a new command into the end of the history container. If container
   * is full, first entry is pushed out.
   *
   * @param command The command to push into the container.
   */
  public void push(String command) {
    // We need to verify that the two arrays we have in our composition are
    // indeed the same size
    assert historyCmd.size() == historyNum.size();
    // Check if our command count exceeds (or reaches) the max size. If so, we
    // need to shift all elements by one to accomadate the next command we want
    // to add.
    if (exceededMaxSize()) {
      shiftByOne();
    }
    // Add the command to the end of the list. And add the command number to the
    // command number list.
    historyCmd.add(command);
    historyNum.add(commandCount);
    // Need to increment commandCount for each command we add to the history
    // container.
    commandCount += 1;
  }

  /**
   * Shifts all commands and command numbers in this history container by one.
   * In other words, remove the first entry, and move all other entries to fill
   * up the empty first entry.
   */
  private void shiftByOne() {
    // This will never be called on an empty history container.
    assert !historyCmd.isEmpty();
    assert !historyNum.isEmpty();
    // We just need to remove the first element from the array.
    historyCmd.remove(0);
    historyNum.remove(0);
  }

  /**
   * Returns a list of the last range command in this history container.
   * Returns whole list if range is greated than the number of commands stored
   * in this history container.
   *
   * @param  range The last number of command to return.
   * @return A list of the last range command.
   * @throws InvalidRangeError If range is less than 0.
   */
  public List<String> getHistCmd(int range) throws InvalidRangeError {
    // If range is invalid i.e. less than 0, throw an error.
    if (range < 0) {
      throw new InvalidRangeError("range must be 0 or greater");
    }
    // Simply need to return a sublist that is range elements from the end.
    // Need to choose range to be min of getSize() and range.
    int startIndex = getSize() - Math.min(getSize(), range);
    return historyCmd.subList(startIndex, getSize());
  }

  /**
   * Returns a list of the last range command numbers in this history container.
   * Returns whole list if range is greated than the number of commands stored
   * in this history container.
   *
   * @param  range The last number of command numbers to return.
   * @return A list of the last range command numbers.
   * @throws InvalidRangeError If range is less than 0.
   */
  public List<Integer> getHistNum(int range) throws InvalidRangeError {
    // If range is invalid i.e. less than 0, throw an error.
    if (range < 0) {
      throw new InvalidRangeError("range must be 0 or greater");
    }
    // Simply need to return a sublist that is range elements from the end.
    // Need to choose range to be min of getSize() and range.
    int startIndex = getSize() - Math.min(getSize(), range);
    return historyNum.subList(startIndex, getSize());
  }

  /**
   * Returns the histNum'th command in this history container.
   *
   * @param  histNum The number of the histNum'th command in history.
   * @return The histNum'th command.
   * @throws InvalidRangeError If histNum'th is not stored in this history
  *          container.
   */
  public String getSingleCmd(int histNum) throws InvalidRangeError {
    // Verify that the number given is within valid range of the history
    // container. NOTE Not sure that the following works
    if (historyNum.get(0) > histNum
        || historyNum.get(getSize() - 1) < histNum) {
      throw new InvalidRangeError("history number not found");
    }
    // Otherwise, return the command with specified history number.
    return historyCmd.get(historyNum.indexOf(histNum));
  }

}

