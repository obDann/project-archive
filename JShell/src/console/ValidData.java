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

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

public class ValidData {

  // Need instance variables for allowed commands.
  private final Hashtable<String, String> cmdHashtable;

  // The following regex basically says the following: 'Look for strings that
  // have 0 or more trailing white spaces (\s*); look for strings that are
  // one or more non-whitespace characters (\S+); or strings that enclosed by
  // opening and closing double quotes and may contain quotes themselves.
  // Well, that's not the whole story; to check out how it actually works, go
  // to this website: https://www.regextester.com/3269
  // and insert the below regex without backquotes into the regular expression
  // bar. Regex w/out backquotes: \s*("(([^"]|"")*)"\S*|S+)\s*
  private String delimitRegex = "\\s*(\"(([^\"]|\"\")*)\"\\S*|\\S+)\\s*";
  private Pattern pattern;
  private Matcher matcher;

  /**
   * Constructor for ValidData. Takes in an arraylist of valid commands.
   */
  public ValidData(Hashtable<String, String> commands) {
    cmdHashtable = commands;
  }

  /**
   * Parses a string and seperates the string into it's seperate arguments.
   *
   * @param  strToParse The string command to be parsed.
   * @return An arraylist of the string commands.
   */
  public ArrayList<String> parseString(String strToParse) {
    // Parse the string by using regex matching. Want to delimit by spaces, but
    // not if the space is within double quotes.
    ArrayList<String> parsedInput = new ArrayList<String>();
    pattern = Pattern.compile(delimitRegex);
    matcher = pattern.matcher(strToParse);

    while (matcher.find()) {
      // Add the arguments with quotes an all (but without trailing/leading
      // whitespace).
      parsedInput.add(matcher.group(1));
    }

    return parsedInput;
  }

  /**
   * Takes a command argument list and returns two lists, the first of which is
   * the first half of listToSplit to just before the first occurrence of '>'
   * or '>>' and the second list is the first occurence of '>' '>>' to the end
   * of the list. NOTE that either of the lists can be empty.
   *
   * @param  listToSplit The command list to split in half.
   * @return A list containing the two lists, split by '>' or '>>' from
   *         listtoSplit
   */
  public ArrayList<List<String>> splitListByRedirectionToken(
      ArrayList<String> listToSplit) {
    // If the ">" or ">>" is not in the list, then we want their indices to be
    // the size of the arraylist so that when we split the list, we get the full
    // list and an empty list.
    int singleArrowIndex = listToSplit.indexOf(">");
    singleArrowIndex
        = (singleArrowIndex > -1) ? singleArrowIndex : listToSplit.size();
    int doubleArrowIndex = listToSplit.indexOf(">>");
    doubleArrowIndex
        = (doubleArrowIndex > -1) ? doubleArrowIndex : listToSplit.size();

    // Shoulde be guaranteed that the mininum is positive, due to above code.
    int splitLocation = Math.min(singleArrowIndex, doubleArrowIndex);

    // Split the list and return both in a new ArrayList. The first split of the
    // list shouldn't contain ">", ">>", and the second list should be empty or
    // start with ">>" or ">".
    ArrayList<List<String>> pair = new ArrayList<>(2);
    List<String> firstHalf = listToSplit.subList(0, splitLocation);
    List<String> secondHalf = listToSplit.subList(
        splitLocation, listToSplit.size());
    pair.add(firstHalf);
    pair.add(secondHalf);

    return pair;
  }

  /**
   * Verifies if the command keyword (first element in arraylist of commands)
   * is a valid command keyword. Throw error if not.
   *
   * @param  commandArgs The arraylist of command arguments.
   * @throws InvalidCommandError if command keyword is invalid.
   */
  public void commandExists(ArrayList<String> commandArgs)
      throws InvalidCommandError {
    // Check if the first element in commandArgs is a valid command.
    assert !commandArgs.isEmpty(); // Should assert be used here? Yes.
    // Throw an error if the command does not exist.
    //boolean doesContain = this.commandList.contains(commandArgs.get(0));
    String key = cmdHashtable.get(commandArgs.get(0));
    if (key == null) {
      throw new InvalidCommandError("Invalid command, please try again");
    }
  }

  /**
   * Checks if a string str matches a regex regex.
   *
   * @param  regex The regex to be checked against.
   * @param  str   The str to check the regex against.
   * @return True iff the str matches the regex.
   */
  private boolean isRegex(String regex, String str) {
    // Compile regex into a pattern and see if str matches it.
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(str);
    return matcher.matches();
  }

  /**
   * Checks if a string is 0 or more whitespace characters.
   *
   * @param  str The string to check.
   * @return True iff the string matches, false otherwise.
   */
  public boolean isNoInput(String str) {
    return isRegex("\\s*", str);
  }

}

