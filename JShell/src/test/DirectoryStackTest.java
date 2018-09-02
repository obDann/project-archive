package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.DirectoryStack;
import commands.PopFromEmptyStackError;
import console.Console;
import filesystem.BadFileSystemObjectNameError;

public class DirectoryStackTest {

  Console console;
  DirectoryStack stack;

  @Before
  public void setUp() {
    TestSetUp holder = new TestSetUp();
    console = holder.getConsole1();
    stack = new DirectoryStack();
  }

  /**
   * Test directory stack's ability push directory onto stack
   * 
   * @throws BadFileSystemObjectNameError
   * @throws PopFromEmptyStackError
   */
  @Test
  public void testPushDir()
      throws BadFileSystemObjectNameError, PopFromEmptyStackError {
    console.getFileSystem().getStack().pushDir(console.getFileSystem());
    String actual = "/";
    String expected = console.getFileSystem().getStack().getStack().get(0)
        .getFileSystemObjectName();
    assertEquals(expected, actual);
  }

  /**
   * Test directory stack's ability to pop directory from stack
   * 
   * @throws BadFileSystemObjectNameError
   * @throws PopFromEmptyStackError
   */
  @Test
  public void testPopDir()
      throws BadFileSystemObjectNameError, PopFromEmptyStackError {
    console.getFileSystem().getStack().pushDir(console.getFileSystem());
    String actual = "/";
    String expected = console.getFileSystem().getStack()
        .popDir(console.getFileSystem()).getFileSystemObjectName();
    assertEquals(expected, actual);
  }

}
