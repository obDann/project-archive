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

import java.util.List;
import java.util.ArrayList;
import commands.HistoryContainer;
import commands.InvalidRangeError;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HistoryContainerTest {

  // Our initial variables
  private HistoryContainer histCont;
  private List<String> expectedCmdList;
  private List<Integer> expectedNumList;

  /**
   * Set up a history container.
   */
  @Before
  public void setUp() {
    histCont = new HistoryContainer();
    expectedCmdList = new ArrayList<>();
    expectedNumList = new ArrayList<>();
  }

  /**
   * Test getting command count.
   */
  @Test
  public void testGetCommandCount() {
    // Test on zero commands. Command count is initially 0 + 1.
    assertEquals(0, histCont.getCmdCount());
    // Test on arbitrary number of commands (expect 10 + 1).
    for (int i = 0; i < 10; i++) {
      histCont.push(String.valueOf(i));
    }
    assertEquals(10, histCont.getCmdCount());
  }

  /**
   * Test getting command count on an overcapacity history container.
   */
  @Test
  public void testGetCommandCountOverCapacity() {
    // Test on exactly 1000 commands. Expect 1001.
    for (int i = 0; i < 1000; i++) {
      histCont.push(String.valueOf(i));
    }
    assertEquals(1000, histCont.getCmdCount());

    // Test on more than 1000 commands (1500). Expect 1501.
    for (int i = 1000; i < 1500; i++) {
      histCont.push(String.valueOf(i));
    }
    assertEquals(1500, histCont.getCmdCount());
  }

  /**
   * Test getting max size of the history container.
   */
  @Test
  public void testGetMaxSize() {
    // Max size is 1000 commands.
    assertEquals(1000, histCont.getMaxSize());
  }

  /**
   * Test getting various ranges of the history container.
   */
  @Test
  public void testGetHistCmdAndGetHistNum() {
    // Put in 100 commands.
    for (int i = 0; i < 100; i++) {
      histCont.push(String.valueOf(i));
    }

    try {
      // Test getting -1 commands.
      histCont.getHistCmd(-1);
      histCont.getHistNum(-1);
    } catch (InvalidRangeError ire) { /* Passes */ }

    try {
      // Test getting 0 commands.
      assertEquals(expectedCmdList, histCont.getHistNum(0));
      assertEquals(expectedNumList, histCont.getHistNum(0));

      // Test getting 1 command.
      expectedCmdList.add("99");
      expectedNumList.add(100);
      assertEquals(expectedCmdList, histCont.getHistCmd(1));
      assertEquals(expectedNumList, histCont.getHistNum(1));

      // Test getting 10 commands.
      for (int i = 98; i > 89; i--) {
        expectedCmdList.add(0, String.valueOf(i));
        expectedNumList.add(0, i + 1);
      }
      assertEquals(expectedCmdList, histCont.getHistCmd(10));
      assertEquals(expectedNumList, histCont.getHistNum(10));

      // Test getting 100 commands.
      for (int i = 89; i > -1; i--) {
        expectedCmdList.add(0, String.valueOf(i));
        expectedNumList.add(0, i + 1);
      }
      assertEquals(expectedCmdList, histCont.getHistCmd(100));
      assertEquals(expectedNumList, histCont.getHistNum(100));

      // Test getting more than 100 commands.
      assertEquals(expectedCmdList, histCont.getHistCmd(1000));
      assertEquals(expectedNumList, histCont.getHistNum(1000));

    } catch (InvalidRangeError ire) {
      fail("This shouldn't be failing");
    }
  }

  /**
   * Test pushing command to an overcapacity history container. Expect
   * everything to shift by one.
   */
  @Test
  public void testPushMaxCapacity() {
    // Max size is 1000 commands, so push one more.
    for (int i = 0; i < 1000; i++) {
      histCont.push(String.valueOf(i));
      expectedCmdList.add(String.valueOf(i));
      expectedNumList.add(i + 1); // Since history number starts counting at 1.
    }

    histCont.push(String.valueOf("1000"));

    expectedCmdList.remove(0);
    expectedNumList.remove(0);
    expectedCmdList.add("1000");
    expectedNumList.add(1001);

    // Check expected values.
    try {
      assertEquals(expectedCmdList, histCont.getHistCmd(1000));
      assertEquals(expectedNumList, histCont.getHistNum(1000));
    } catch (InvalidRangeError ire) {
      fail("This shouldn't be failing.");
    }
  }

}

