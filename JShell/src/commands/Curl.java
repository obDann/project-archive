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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import console.Console;
import filesystem.BadFileSystemObjectNameError;
import filesystem.Directory;
import filesystem.DuplicateFileSystemObjectNameError;
import filesystem.File;
import filesystem.FileSystem;

public class Curl extends Command {
  private FileSystem userFileSystem;
  private String[] commandArgument;

  /**
   * Creates a Curl object
   *
   * @param console the main console
   * @param command the original curl command parsed by spaces
   */
  public Curl(Console console, String[] command) {
    commandArgument = command;
    userFileSystem = console.getFileSystem();
  }

  /**
   * Gets the file at the URL and add it to the current working directory
   */
  @Override
  public void execute() {
    try {
      // verify if correct number of inputs is given
      this.verifyNumInput(commandArgument);
      URL urlLink = new URL(commandArgument[1]);
      String url = urlLink.toString();
      // check if given URL is a URL
      this.validateURL(urlLink, url);
      // create URL connection
      HttpURLConnection connection =
          (HttpURLConnection) urlLink.openConnection();
      // verify if the URL is a working URL
      this.verifyURL(connection.getResponseCode(), urlLink, url);
      // check if the file is a txt or html file
      this.checkFileExtension(urlLink, url);
      // get the file from the URL and add it to the current working directory
      this.makeFile(urlLink, connection);
    } catch (MalformedURLException e) {
      cmdOPQ.enqueue(e.toString() + " Invalid URL", false);
    } catch (IOException e) {
      cmdOPQ.enqueue(e.toString(), false);
    } catch (BadFileSystemObjectNameError e) {
      cmdOPQ.enqueue(e.toString(), false);
    } catch (InvalidURLException e) {
      cmdOPQ.enqueue(e.toString() + ": Invalid URL", false);
    } catch (ConnectionErrorException e) {
      cmdOPQ.enqueue(e.toString() + ": Can not connect to URL", false);
    } catch (InvalidFileException e) {
      cmdOPQ.enqueue(e.toString() + ": File must be txt or html file", false);
    } catch (InvalidNumberOfArgumentsException e) {
      cmdOPQ.enqueue(e.toString() + ": Refer to Curl man page", false);
    } catch (DuplicateFileSystemObjectNameError e) {
      cmdOPQ.enqueue(e.toString(), false);
    }
  }

  /**
   * Verify if the number of arguments given is at least 3
   *
   * @param commandArgument the given command arguments
   * @throws InvalidNumberOfArgumentsException
   */
  public void verifyNumInput(String[] commandArgument)
      throws InvalidNumberOfArgumentsException {
    if (commandArgument.length != 2) {
      throw new InvalidNumberOfArgumentsException();
    }
  }

  /**
   * Checks if the given URL is a valid URL
   *
   * @param urlLink the URL that is of reference type URL
   * @param url the URL that is of refeference type String
   * @throws InvalidURLException
   */
  public void validateURL(URL urlLink, String url) throws InvalidURLException {
    if (!url.startsWith("http://") && (!url.startsWith("https://"))) {
      throw new InvalidURLException();
    }
  }

  /**
   * Verifies if the URL can be connected to
   *
   * @param connectionCode the URL status code
   * @param urlLink the URL that is of reference type URL
   * @param url the URL that is of reference type String
   * @throws ConnectionErrorException
   */
  public void verifyURL(int connectionCode, URL urlLink, String url)
      throws ConnectionErrorException {
    if (connectionCode != HttpURLConnection.HTTP_OK) {
      throw new ConnectionErrorException();
    }
  }

  /**
   * Verifies if the file is a txt or html file
   *
   * @param urlLink the URL that is of reference type URL
   * @param url the URL that is of referecne type String
   * @throws InvalidFileException
   */
  public void checkFileExtension(URL urlLink, String url)
      throws InvalidFileException {
    if (!url.endsWith(".txt") && !url.endsWith(".html")) {
      throw new InvalidFileException();
    }
  }

  /**
   * Gets the file from URL and add it to the current working directory
   *
   * @param urlLink the URL that is of reference type URL
   * @param connection the connection to the URL
   * @throws BadFileSystemObjectNameError
   * @throws IOException
   * @throws DuplicateFileSystemObjectNameError
   */
  public void makeFile(URL urlLink, HttpURLConnection connection)
      throws BadFileSystemObjectNameError, IOException,
      DuplicateFileSystemObjectNameError {
    String fileContents = "";
    BufferedReader br =
        new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String row;
    // add each line from URL file to file contents
    while ((row = br.readLine()) != null) {
      fileContents += row;
      fileContents += "\n";
    }
    String[] absoluteURLFileName = urlLink.getFile().split("/");
    String fileName = absoluteURLFileName[absoluteURLFileName.length - 1];
    /*
     * create the file with the same contents as the URL file and add it to the
     * current working directory
     */
    File newFile = new File(fileName, fileContents);
    Directory pwd = (Directory) userFileSystem.getCurrentPath();
    pwd.storeNewFileSystemObject(newFile);;
  }

  /**
   * The man page of Curl
   *
   * @return the man page of curl
   */
  public static String getDoc() {
    String curl = "";
    curl += "CURL\n\n";
    curl += "NAME\n";
    curl += "\tcurl - retrieve file from URL and add it to the current working"
        + " directory\n";
    curl += "SYNOPSIS\n";
    curl += "\tcurl URL\n\n";
    curl += "\twhere URL is the web address of the file\n";
    return curl;
  }

}
