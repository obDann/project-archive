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
package test;

import console.OutputQueue;

import static org.junit.Assert.*;

import javafx.util.Pair;
import org.junit.*;

public class OutputQueueTest {

  private OutputQueue testQueue;
  private OutputQueue faultyOPQ;

  @Before public void setUp() {
    // set up our output queues
    testQueue = new OutputQueue();
    faultyOPQ = new OutputQueue();
    // we make a faulty output queue and add different items directive items
    // to it
    faultyOPQ.enqueue("bad error", false);
    faultyOPQ.enqueue("bad output queue", true);
    faultyOPQ.enqueue("stderr", false);
  }

  /**
   * Test if the Queue is empty upon initialization
   */
  @Test public void testEmpty() {
    // upon initialization, we expect that the testQueue is empty
    boolean actual = testQueue.isEmpty();
    assertTrue(actual);
  }

  /**
   * Test if the Queue is not empty when an entry is added
   */
  @Test public void testNonEmpty() {
    // we want to make sure that the queue can enqueue, so to do this, we
    // will enqueue and test if the output queue is not empty
    String testOutput = "hello";
    boolean testBoolean = true;
    testQueue.enqueue(testOutput, testBoolean);

    boolean actual = testQueue.isEmpty();
    assertFalse(actual);
  }

  /**
   * Test if dequeue works properly; checking its return value
   */
  @Test public void testDequeueOutput() {
    // we want to make sure that the dequeue functionality works properly too
    // so we we will enqueue
    String expectedOutput = "hello";
    boolean expectedBoolean = true;
    testQueue.enqueue(expectedOutput, expectedBoolean);

    // then dequeue
    Pair<String, Boolean> holder = testQueue.dequeue();
    String actualOutput = holder.getKey();
    boolean actualBoolean = holder.getValue();
    assertEquals(expectedOutput, actualOutput);
    assertEquals(expectedBoolean, actualBoolean);
  }

  /**
   * Test if dequeue works properly; check if the queue mutates as documented
   */
  @Test public void testDequeueToEmpty() {
    // we want to make sure that dequeuing to an empty queue works, so we
    // will enqueue twice
    String testOutput = "hello";
    boolean testBoolean = true;
    testQueue.enqueue(testOutput, testBoolean);
    testQueue.enqueue(testOutput, testBoolean);

    // then dequeue twice
    testQueue.dequeue();
    testQueue.dequeue();

    // then check if the queue is empty
    boolean actual = testQueue.isEmpty();
    assertTrue(actual);
  }

  /**
   * Test to make sure that queueing order does matter
   */
  @Test public void testMultipleEntriesButDequeueOne() {
    // add one entry
    String expectedOutput = "THIS IS EXPECTED";
    boolean expectedBoolean = false;
    testQueue.enqueue(expectedOutput, expectedBoolean);

    // then add another entry
    String trivialOutput = "trivial";
    boolean trivialBoolean = true;
    testQueue.enqueue(trivialOutput, trivialBoolean);

    // then dequeue the queue once
    Pair<String, Boolean> holder = testQueue.dequeue();
    // and get its contents
    String actualOutput = holder.getKey();
    boolean actualBoolean = holder.getValue();

    assertEquals(expectedOutput, actualOutput);
    assertEquals(expectedBoolean, actualBoolean);

    // also test if the Queue is not empty
    boolean notEmpty = testQueue.isEmpty();
    assertFalse(notEmpty);
  }

  /**
   * Test to make sure that queueing order does matter
   */
  @Test public void testMultipleEntriesAndDequeueBoth() {
    // add one entry
    String trivialOutput = "trivial";
    boolean trivialBoolean = true;
    testQueue.enqueue(trivialOutput, trivialBoolean);

    // then add another entry
    String expectedOutput = "THIS IS EXPECTED";
    boolean expectedBoolean = false;
    testQueue.enqueue(expectedOutput, expectedBoolean);

    // then dequeue the queue twice
    testQueue.dequeue();
    Pair<String, Boolean> holder = testQueue.dequeue();
    // and get its contents
    String actualOutput = holder.getKey();
    boolean actualBoolean = holder.getValue();

    assertEquals(expectedOutput, actualOutput);
    assertEquals(expectedBoolean, actualBoolean);

    // also test if the Queue is empty
    boolean isEmpty = testQueue.isEmpty();
    assertTrue(isEmpty);
  }

  /**
   * Test to make sure that a user can get all output conveniently
   */
  @Test public void testAllOutput() {
    // create an expected string
    String expected = "bad error" + "\n";
    expected += "bad output queue" + "\n";
    expected += "stderr" + "\n";

    // then get the actual output
    String actual = faultyOPQ.getAllOutput();

    // then check all output
    assertEquals(expected, actual);
  }

  /**
   * Test to make sure that a user can get all stdout conveniently
   */
  @Test public void testAllStdOut() {
    // create an expected string
    String expected = "bad output queue" + "\n";

    // then get the actual output
    String actual = faultyOPQ.getOnlyStdOut();

    // then check stdout
    assertEquals(expected, actual);
  }


  /**
   * Test to make sure that a user can get all stderr conveniently
   */
  @Test public void testAllStdErr() {
    // create an expected string
    String expected = "bad error" + "\n";
    expected += "stderr" + "\n";

    // then get the actual output
    String actual = faultyOPQ.getOnlyStdErr();

    // then check stderr
    assertEquals(expected, actual);
  }

  /**
   * Test to make sure that there aren't any mutations for the getAllOutput
   * method
   */
  @Test public void testNoMutationAllOutput() {
    // get all the output
    faultyOPQ.getAllOutput();

    // then let's dequeue the items
    String actual = "";

    while (!faultyOPQ.isEmpty()) {
      Pair<String, Boolean> holder = faultyOPQ.dequeue();
      actual += holder.getKey() + "\n";
    }

    // then let's make our expected string
    String expected = "bad error\n";
    expected += "bad output queue\n";
    expected += "stderr\n";

    assertEquals(expected, actual);
  }

  /**
   * Test to make sure that there aren't any mutations for the getOnlyStdOut
   * method
   */
  @Test public void testNoMutationAllStdOut() {
    // get all the output
    faultyOPQ.getOnlyStdOut();

    // then let's dequeue the items
    String actual = "";

    while (!faultyOPQ.isEmpty()) {
      Pair<String, Boolean> holder = faultyOPQ.dequeue();
      actual += holder.getKey() + "\n";
    }

    // then let's make our expected string
    String expected = "bad error\n";
    expected += "bad output queue\n";
    expected += "stderr\n";

    assertEquals(expected, actual);
  }

  /**
   * Test to make sure that there aren't any mutations for the getOnlyStdErr
   * method
   */
  @Test public void testNoMutationAllStdErr() {
    // get all the output
    faultyOPQ.getOnlyStdErr();

    // then let's dequeue the items
    String actual = "";

    while (!faultyOPQ.isEmpty()) {
      Pair<String, Boolean> holder = faultyOPQ.dequeue();
      actual += holder.getKey() + "\n";
    }

    // then let's make our expected string
    String expected = "bad error\n";
    expected += "bad output queue\n";
    expected += "stderr\n";

    assertEquals(expected, actual);
  }
}
