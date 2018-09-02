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
import console.Console;
import filesystem.*;

public class TestSetUp {
  private Console setUpConsole1;

  /**
   * The purpose of this class is to set up a file system for further
   * testing of our commands
   */

  /* The file system is structured as follows:
   * /
   *     one
   *         level2one
   *                   level3one
   *                             level4
   *                   level3two
   *         level2two
   *         level2three
   *         someFile
   *     two
   *         level2four
   *         level2five
   *     three
   *     fileWithContents
   * */
  public TestSetUp() {
    try {

      // level 1
      Directory lvl1pt1 = new Directory("one");
      Directory lvl1pt2 = new Directory("two");
      Directory lvl1pt3 = new Directory("three");
      File fwContents = new File("fileWithContents", "This file has contents");


      // level 2 pt 1
      Directory lvl2pt1 = new Directory("level2one");
      Directory lvl2pt2 = new Directory("level2two");
      Directory lvl2pt3 = new Directory("level2three");
      File lvl2file = new File("someFile", "more contents");
      lvl1pt1.storeNewFileSystemObject(lvl2pt1);
      lvl1pt1.storeNewFileSystemObject(lvl2pt2);
      lvl1pt1.storeNewFileSystemObject(lvl2pt3);
      lvl1pt1.storeNewFileSystemObject(lvl2file);

      // level 2 pt 2
      Directory lvl2pt4 = new Directory("level2four");
      Directory lvl2pt5 = new Directory("level2five");
      lvl1pt2.storeNewFileSystemObject(lvl2pt4);
      lvl1pt2.storeNewFileSystemObject(lvl2pt5);


      // level 3
      Directory lvl3pt1 = new Directory("level3one");
      Directory lvl3pt2 = new Directory("level3two");
      lvl2pt1.storeNewFileSystemObject(lvl3pt1);
      lvl2pt1.storeNewFileSystemObject(lvl3pt2);

      // level 4
      Directory lvl4 = new Directory("level4");
      lvl3pt1.storeNewFileSystemObject(lvl4);

      FileSystem root = FileSystem.createFileSystemInstance();
      Console cons = new Console(root);

      // get the directory
      Directory ourDir = root.getRootDir();
      // put in the directory one two and three
      ourDir.storeNewFileSystemObject(lvl1pt1);
      ourDir.storeNewFileSystemObject(lvl1pt2);
      ourDir.storeNewFileSystemObject(lvl1pt3);
      ourDir.storeNewFileSystemObject(fwContents);

      setUpConsole1 = cons;
    } catch (BadFileSystemObjectNameError e) {
      System.out.println(e);
    } catch (DuplicateFileSystemObjectNameError e) {
      System.out.println(e);
    }

  }

  /**
   * Returns the sample console
   *
   * @return
   */
  public Console getConsole1() {
    return setUpConsole1;
  }
}
