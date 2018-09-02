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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Curl;
import console.Console;
import filesystem.File;
import filesystem.NonExistantChildError;

public class CurlTest {
  Console console;
  Curl curl;

  @Before
  public void setUp() {
    TestSetUp holder = new TestSetUp();
    console = holder.getConsole1();
  }

  /**
   * test curl's ability to create new file with same name as the file from the
   * URL
   */
  @Test
  public void testCurlTxtFileName() {
    String[] command = {"curl", "https://keybase.pub/rdl/helloworld.txt"};
    curl = new Curl(console, command);
    curl.execute();
    String expected = "helloworld.txt";
    boolean actual = console.getFileSystem().isExistPath(expected);
    assertTrue(actual);
  }

  /**
   * Test curl's ability to create file with same contents as the file from the
   * URL
   * 
   * @throws NonExistantChildError
   */
  @Test
  public void testCurlTxtFileContents() throws NonExistantChildError {
    String[] command =
        {"curl", "http://www.cs.toronto.edu/~bianca/cscb09w17/posted_labs/"
            + "lab1/solns.txt"};
    curl = new Curl(console, command);
    curl.execute();
    String expected = "Solutions to Warm-up exercises:\n" + "\n"
        + "1. head -n 3 hockey_stats.txt\n" + "2. wc -c hockey_stats.txt \n"
        + "3. sort -k5nr hockey_stats.txt    \n"
        + "4. grep TOR hockey_stats.txt \n"
        + "5. cut -f 1 hockey_stats.txt > players.txt\n" + "\n"
        + "Solutions to Exercises with pipes:\n" + "\n"
        + "1. sort hockey_stats.txt | cut -f 1     \n"
        + "2. head -n 10 hockey_stats.txt | grep TOR | wc -l\n"
        + "3. head -n 20 hockey_stats.txt | tail -n 10\n"
        + "4. sort -k5nr hockey_stats.txt | head -n 1 | cut -f 3\n"
        + "5. cut -f 2 hockey_stats.txt | sort | uniq | wc -l\n"
        + "6. ps aux |grep sshd | grep -v grep |wc -l\n"
        + "7. ps aux |grep sshd | cut -f 1 -d \" \" | sort | uniq |wc -l\n"
        + "\n" + "Notes: \n"
        + "-- cut and sort use different delimiters (by default tab for cut,"
        + " while sort uses either space or tab).\n"
        + "-- for some pipes the commands could be arranged in a different"
        + " order, e.g. for exercise 1) this would also work: cut -f 1"
        + " hockey_stats.txt | sort";
    File file =
        (File) console.getFileSystem().getRootDir().getChild("solns.txt");
    String actual = file.getFileContents();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

  /**
   * Test curl's ability to catch error when a URL has a connection error
   */
  @Test
  public void testCurlUrlConnectionError() {
    String[] command = {"curl", "http://www.ub.edu/gilcub/SIMPLE/simple.html"};
    curl = new Curl(console, command);
    curl.execute();
    String expected =
        "commands.ConnectionErrorException: Can not connect to URL";
    String actual = curl.getOutputQueue().getAllOutput();
    actual = actual.replace("\n", "");
    assertEquals(expected, actual);
  }

  /**
   * Test curl's ability to catch error when a URL that is not a txt or html
   * file is given
   */
  @Test
  public void testCurlNotTxtOrHtmlUrl() {
    String[] command = {"curl",
        "https://www.utsc.utoronto.ca/~bharrington/cscb58/index.shtml"};
    curl = new Curl(console, command);
    curl.execute();
    String expected =
        "commands.InvalidFileException: File must be txt or html file";
    String actual = curl.getOutputQueue().getAllOutput();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

  /**
   * Test curl's ability to catch error when the given input is not a URL
   */
  @Test
  public void testCurlInvalidUrl() {
    String[] command = {"curl", "someURL"};
    curl = new Curl(console, command);
    curl.execute();
    String expected =
        "java.net.MalformedURLException: no protocol: someURL Invalid URL";
    String actual = curl.getOutputQueue().getAllOutput();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

  /**
   * Test curl's ability to catcher error when invalid number of arguments is
   * given
   */
  @Test
  public void testCurlInvalidNumberOfArguments() {
    String[] command = {"curl"};
    curl = new Curl(console, command);
    curl.execute();
    String expected =
        "commands.InvalidNumberOfArgumentsException: Refer to Curl man page";
    String actual = curl.getOutputQueue().getAllOutput();
    actual = actual.trim();
    assertEquals(expected, actual);
  }

  /**
   * Test curl's ability to create file with same name as the file from the URL
   */
  @Test
  public void testCurlHtmlFileName() {
    String[] command =
        {"curl", "https://www.w3.org/TR/WD-html40-970917/htmlweb.html"};
    curl = new Curl(console, command);
    curl.execute();
    String expected = "htmlweb.html";
    boolean actual = console.getFileSystem().isExistPath(expected);
    assertTrue(actual);
  }

  /**
   * Test curl's ability to create file with same contents as file from URL
   * 
   * @throws NonExistantChildError
   */
  @Test
  public void testCurlHtmlFileContents() throws NonExistantChildError {
    String[] command = {"curl", "http://curly.cs.toronto.edu/b09/hello.html"};
    curl = new Curl(console, command);
    curl.execute();
    String expected = "<html>\n" + "<body>\n" + "<h1> Hello! </h1>\n"
        + "Hello, world\n" + "</body>\n" + "</html>";
    File file =
        (File) console.getFileSystem().getRootDir().getChild("hello.html");
    String actual = file.getFileContents();
    actual = actual.trim();
    assertEquals(expected, actual);
  }
}
