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

import javafx.util.Pair;

import java.util.ArrayList;

public class OutputQueue {

  //we want an array lists to hold our entries
  private ArrayList<String> forOutput = new ArrayList<String>();
  private ArrayList<Boolean> forStdOut = new ArrayList<Boolean>();


  /**
   * Adds a string to the Output Queue
   *
   * @param output   The string that is to be outputted in the shell
   * @param isStdOut True if and only if the output is intended for standard
   *                 output
   */
  public void enqueue(String output, boolean isStdOut) {
    // just add the string output
    forOutput.add(output);
    // and add the variable to determine if its going to be towards standard
    // output
    forStdOut.add(isStdOut);
  }

  /**
   * Respectively returns a string and a boolean from the output queue,
   * removing the queued entries of output (if there are any)
   *
   * @return Respectively returns a string and a boolean from the output queue
   */
  public Pair<String, Boolean> dequeue() {
    // check if this queue is empty
    if (!this.isEmpty()) {
      // if it isn't, then we want to pop the output and the forStdOut
      String holdingStr = forOutput.remove(0);
      boolean holdingBool = forStdOut.remove(0);
      // then return a pair
      return new Pair<String, Boolean>(holdingStr, holdingBool);
    }
    return null;
  }

  /**
   * Determines if the output queue is empty
   *
   * @return True if the output queue is empty
   */
  public boolean isEmpty() {
    // since we enqueue/dequeue the objects at the same time, either of the
    // two array lists is acceptable
    return forOutput.isEmpty();
  }

  /**
   * Returns all output enqueued in this output queue (every entry ends with
   * a new line)
   *
   * @return all output enqueued in this output queue
   */
  public String getAllOutput() {
    // create an initial string
    String initial = "";
    // and create a queue so we still hold its output
    OutputQueue newOpq = new OutputQueue();

    // then we want to go through this output queue
    while (!this.isEmpty()) {
      // get its content
      Pair<String, Boolean> holder = this.dequeue();
      // get the content
      String tempContent = holder.getKey();
      // and since we do not want to "mutate" this output queue, we want to
      // hold its boolean value as well
      boolean tempBool = holder.getValue();
      // we enqueue the dequeued items to the new output queue
      newOpq.enqueue(tempContent, tempBool);
      // then add the string along with a "\n" value
      initial += tempContent + "\n";
    }
    // then we have to set this object to the new output queue so that its
    // not mutated
    this.forOutput = newOpq.forOutput;
    this.forStdOut = newOpq.forStdOut;
    return initial;
  }

  /**
   * Returns only the standard output enqueued in the output queue (every
   * entry ends with a new line)
   *
   * @return the standard output enqueued
   */
  public String getOnlyStdOut() {
    // we need to have an initial string that is going to be returned
    String initial = "";
    // then we need an output queue so that we don't mutate this output queue
    OutputQueue newOPQ = new OutputQueue();

    // we want to go through the output queue
    while (!this.isEmpty()) {
      // hold the values that is dequeued
      Pair<String, Boolean> holder = this.dequeue();
      String tempContent = holder.getKey();
      boolean tempBool = holder.getValue();
      // enqueue the values to the new output queue
      newOPQ.enqueue(tempContent, tempBool);
      // then we want to check if the boolean is true (meaning it's standard
      // output)
      if (tempBool) {
        // if it is, then append it to our initial string
        initial += tempContent + "\n";
      }
    }
    // then we have to reset our output queue to the new output queue so
    // that we don't mutate the original output queue
    this.forOutput = newOPQ.forOutput;
    this.forStdOut = newOPQ.forStdOut;
    // then return the returning string
    return initial;
  }

  /**
   * Returns only the standard error enqueued in the output queue (every
   * entry ends with a new line)
   *
   * @return the standard error enqueued
   */
  public String getOnlyStdErr() {
    String ret = "";
    // we want to create an output queue so that we don't mutate this output
    // queue
    OutputQueue newOPQ = new OutputQueue();

    // then we want to go through this output queue
    while (!this.isEmpty()) {
      // so we dequeue the output queue
      Pair<String, Boolean> holder = this.dequeue();
      // then we want to keep our contents/boolean
      String tempContent = holder.getKey();
      boolean tempBool = holder.getValue();

      // we want to enqueue the content that was dequeued
      newOPQ.enqueue(tempContent, tempBool);

      // then we want to check if the output is "bad"/ is std err
      if (!tempBool) {
        // if it is, then append it to our returning String
        ret += tempContent + "\n";
      }
    }
    // then we want to just reset our output queue's arraylists so that we
    // don't mutate our output queue
    this.forOutput = newOPQ.forOutput;
    this.forStdOut = newOPQ.forStdOut;
    // then return our string
    return ret;
  }
}
