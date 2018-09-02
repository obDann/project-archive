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
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import commands.Man;
import console.Console;
import console.OutputQueue;
import filesystem.FileSystem;

public class ManTest {
  // Set up for file system
  TestSetUp su;
  Console c;
  Man myMan;
  FileSystem fs;
  OutputQueue cmdOPQ;

  @Before
  public void setup() {
    // Set up for file system
    TestSetUp su = new TestSetUp();
    c = su.getConsole1();
    fs = c.getFileSystem();
  }

  /**
   * Captures the print statements to the shell
   *
   * @return
   */
  public OutputStream header() {
    OutputStream os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);
    System.setOut(ps);
    return os;
  }

  /**
   * Restores output to normal
   */
  public void footer() {
    PrintStream originalOut = System.out;
    System.setOut(originalOut);
  }

  @Test
  public void testCatMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "cat"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "CAT\n" +
        "\n" +
        "NAME\n" +
        "\tcat - concatenate files to a standard output\n" +
        "SYNOPSIS\n" +
        "\tcat FILE...\n" +
        "\n" +
        "\twhere FILE is a file in the file system\n" +
        "\twhere ... means multiple files separated by spaces\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testCdMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "cd"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "CD\n\n";
    man += "NAME\n";
    man += "\tcd - change the current filesystem to be in a\n";
    man += "\tdifferent directory\n";
    man += "SYNOPSIS\n";
    man += "\tcd DIRECTORY\n\n";
    man += "\twhere DIRECTORY is a directory in the file system\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testCpMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "cp"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "Cp\n\n";
    man += "NAME\n";
    man +=
        "\tcp - copy the given OLDPATH to the NEWPATH without deleting the"
        + "OLDPATH\n";
    man += "SYNOPSIS\n";
    man += "\tcp OLDPATH NEWPATH\n\n";
    man += "\twhere OLDPATH is the path that we want to copy";
    man += "\twhere NEWPATH is the path that we want to copy to\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testCurlMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "curl"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String curl = "";
    curl += "CURL\n\n";
    curl += "NAME\n";
    curl += "\tcurl - retrieve file from URL and add it to the current working"
        + " directory\n";
    curl += "SYNOPSIS\n";
    curl += "\tcurl URL\n\n";
    curl += "\twhere URL is the web address of the file\n\n";
    assertEquals(curl, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testEchoMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "echo"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "ECHO\n" +
        "\n" +
        "NAME\n" +
        "\techo - outputs a string\n" +
        "SYNOPSIS\n" +
        "\techo \"STRING\"\n" +
        "\twhere STRING is a string that must be in quotations\n" +
        "\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testFindMan() {
    String[] manCommand = {"man", "find"};
    Man myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String instruction = "";
    instruction += ("FIND:\nNAME\n\treturns the name of the file or"
        + " directory we are looking for in multiple paths if \n"
        + "\tsuch object exists. returns an error if such file or directory"
        + " does not exist\n"
        + "SYNOPSIS\n \tfind PATH... -type [f|d] -name expression\n"
        + "EXAMPLE:\n \t>>> find /a1 /a1 -type f -name \"cr\"" + "\n\tcr\n\tcr"
        + "\n\t>>>find A1 A2 -type d -name \"user1\""
        + "\n\tuser1\n\tfind: user1: no such Directory in A2\n");
    assertEquals(instruction, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testGrepMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "grep"};
    Man myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String grep = "";
    grep += "GREP\n\n";
    grep += "NAME\n";
    grep += "\tgrep - print lines of a file/s that match a regex or "
        + "print lines of files in a directory that match a regex.\n"
        + "\t\tIf no lines match a regex then the message 'No lines"
        + " match the regex' is returned.\n";
    grep += "SYNOPSIS\n";
    grep += "\tgrep [-R] REGEX PATH...\n\n";
    grep += "\twhere -R indicates to recursively traverse the directory"
        + " if a directory path is given. If -R and a file path\n"
        + "\t\tare given then print the lines of the file that match the"
        + "regex.\n";
    grep += "\twhere REGEX is a regex\n";
    grep += "\twhere PATH is a file or directory\n";
    grep += "\twhere ... means multiple files or directories\n";
    grep += "\twhere [] means optional\n\n";
    assertEquals(grep, cmdOPQ.getOnlyStdOut());
    footer();
  }

  // TODO add getDoc to History
  @Test
  public void testHistoryMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "history"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "HISTORY\n\n";
    man += "NAME\n";
    man += "\thistory - if no number are given, then print out the most"
        + " recent commands in the shell\n";
    man += "\nIf a number k greater or equal to zero is given, then it will"
        + " print out the most recent k\n";
    man += "commands in the shell, one command per line";
    man += "SYNOPSIS\n";
    man += "\thistory [number]\n";
    man += "\twhere [number] is number greater or equal to zero\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testLsMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "ls"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "LS\n\n";
    man += "NAME\n";
    man += "\tls - print the file name itself or the contents of a\n";
    man += "\tdirectory\n";
    man += "SYNOPSIS\n";
    man += "\tls [-R] [FILE]... [DIRECTORY]...\n\n";
    man += "\twhere -R is a token to view the path's contents and it's sub";
    man += "directories' contents\n";
    man += "\twhere FILE is a file in the current file system\n";
    man += "\twhere DIRECTORY is a directory in the current file system\n";
    man += "\twhere [] means optional\n";
    man += "\twhere ... means multiple (files or directories)\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testManMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "man"};
    Man myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String instruction = "";
    instruction +=
        ("MAN:\nNAME:\n\treturns the documentation of the given command"
            + "SYNOPSIS\n\tman COMMAND\n");
    assertEquals(instruction, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testMkdirMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "mkdir"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "MKDIR\n\n";
    man += "NAME\n";
    man += "\tmkdir - makes directories\n";
    man += "SYNOPSIS\n";
    man += "\tmkdir PATH...\n\n";
    man += "\twhere PATH is a directory that is to be made\n";
    man += "\twhere ... means multiple\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testMvMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "mv"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "Mv\n\n";
    man += "NAME\n";
    man +=
        "\tmv - copy the given OLDPATH to the NEWPATH and deleting the"
        + "OLDPATH\n";
    man += "SYNOPSIS\n";
    man += "\tmv OLDPATH NEWPATH\n\n";
    man += "\twhere OLDPATH is the path that we want to copy";
    man += "\twhere NEWPATH is the path that we want to copy to\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }


  @Test
  public void testPopdMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "popd"};
    Man myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "POPD\n\nName\n     popd - changes the" + "current working"
        + " directory to the top most directory\n"
        + "     in the directory stack\nSynopsis\n     Removes the"
        + "top most directory in the directory stack and sets it as\n"
        + "     the current working directory. If the directory stack"
        + "is empty then an\n     error message is the output.\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testPushdMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "pushd"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "PUSHD\n\nName\n     pushd - saves the " + "current working"
        + " directory and creates a new\n     "
        + "current working directory\nSynopsis\n     "
        + "The current working directory is pushed onto a directory"
        + " stack and\n     the current working directory is changed"
        + " to the given input which may\n     be in the form of the"
        + " directory name, the absolute or relative path\n     "
        + "of the directory.\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
  }

  @Test
  // TODO: add getDoc to pwd
  public void testPwdMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "pwd"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "PWD\n\n";
    man += "NAME\n";
    man += "\tpwd - print the absolute path for the current working"
        + " directory to the shell\n";
    man += "SYNOPSIS\n";
    man += "\tpwd\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

  @Test
  public void testTreeMan() {
    OutputStream os = header();
    String[] manCommand = {"man", "tree"};
    myMan = new Man(c, manCommand);
    cmdOPQ = myMan.getOutputQueue();

    myMan.execute();
    String man = "";
    man += "TREE\n\nName\n     tree - display the " + "entire file"
        + " system as a tree\nSYNOPSIS\n     Starting from "
        + "the root directory, display the entire file system as\n     "
        + "a tree, where every level of the tree is indented.\n";
    assertEquals(man, cmdOPQ.getOnlyStdOut());
    footer();
  }

}
