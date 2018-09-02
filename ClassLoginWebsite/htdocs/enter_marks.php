<?php
  session_start();
  // Prevent access if not logged in.
  if (!isset($_SESSION['login_user'])) {
      header("location:index.php"); // CHANGE THIS TO index.php LATER
  }
  if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // first we include our config file
    include 'config.php';
    //then we define our variables
    $utorid = $_POST['utorid'];
    $mat = $_POST['material'];
    $mark = $_POST['mark'];
    
    //one thing that can possibly go wrong is if the number is "invalid"
    // i.e. the mark entered is higher than the material it's out of
    // so we have to run one sql command to find what the material is out of
    $sql = "SELECT *";
    $sql = $sql . " FROM course_materials";
    $sql = $sql . " WHERE mat_type='" . $mat ."'";
    $result = $conn->query($sql);
    // there should only be one row since the dropdown only has valid options
    $row = $result->fetch_assoc();
    $out_of = $row['out_of'];

    // now, we want to check if there isn't anybody under that UTORid
    // so we run a sql command
    $sql = "SELECT *";
    $sql = $sql . " FROM accounts";
    $sql = $sql . " WHERE utorid='" . $utorid ."'";
    $result = $conn->query($sql);

    //there should be only one result because utorids are unique
    if ($result->num_rows != 1){
      echo "<script>alert(\"Invalid UTORid\")</script>";
    }

    // we have to check if the marker put in a number...
    elseif (!is_numeric($mark)){
      echo "<script>alert(\"Mark inserted is not a number\")</script>";
    }

    // and check if the mark inserted is higher than what the mark is out of
    elseif ($mark > $out_of){
      echo "<script>alert(\"You must insert a number below or equal to ". $out_of ." for " . $mat . " \")</script>";
    }

    else{
      // Now we have to make sure that UTORid is associated with a student
      // account
      $row = $result->fetch_assoc();
      $account_type = $row["account_type"];

      // It is invalid if it is not a student account
      if ($account_type != "student"){
        echo "<script>alert(\"This person is not a student, please enter a student UTORid.\")</script>";
      }

      else{
        // now, we have to check if there is an entry by the UTORid and the material in the 
        // student marks table. So we have to run another sql command
        $sql = "SELECT *";
        $sql = $sql . " FROM student_marks";
        $sql = $sql . " WHERE utorid='" . $utorid ."'";
        $sql = $sql . " AND mat_type='" . $mat . "'";
        // renew the result
        $result = $conn->query($sql);
        // if there isn't any entries, then we can insert it into our database

        if ($result->num_rows == 0){
          // we have to run another sql command to insert values
          $sql = "INSERT INTO student_marks";
          $sql = $sql . " VALUES ('" . $utorid . "', '" . $mat . "', " . $mark .")";
          //connect and query
          $conn->query($sql);
          // return that the mark has been entered
          echo "<script>alert(\"Mark has been entered.\")</script>";
        }

        else{
            // we get their old mark to let the marker know that their
            // mark has been changed
            $row = $result->fetch_assoc();
            $old_mark = $row['mark'];
            $sql = "UPDATE student_marks";
            $sql = $sql . " SET mark=" . $mark;
            $sql = $sql . " WHERE utorid='" . $utorid ."'";
            $sql = $sql . " AND mat_type='" . $mat . "'";
            //connect and run the sql command
            $conn->query($sql);
            echo "<script>alert(\"Mark has been changed from " . $old_mark . " to " . $mark . ".\")</script>";
        }
      }
    }
  }
?>

<!DOCTYPE html>

<html>

<head>
  <meta charset="utf-8">
  <title>CSCB20: Introduction to Databases and Web Applications</title>
  <link rel="stylesheet" href="css/instructor.css">
  <link rel="stylesheet" href="css/a2css.css">
</head>

<body>
  <!--Create a header. We want this on top of the website.-->
  <div class="header_block">
    <h1 class="header">
      <a href="home.php">
        CSCB20: Introduction to Databases and Web Applications
      </a>
    </h1>
  </div>

  <!--Create a container to contain the nav bar and the content page.-->
  <div class="main">
    <!--We want one part of the screen telling us what's available on the
     screen-->
    <div class="navigation">
      <!--Home button.-->
      <div class="p_nav_button" id="top">
        <!--Home is a special primary button whose size is bigger than
         secondary buttons.-->
        <a href="home.php">Home</a>
      </div>

      <!--Course Materials heading.-->
      <!-- Course materials is a secondary button, along with
       lecture/labs.-->
      <div class="p_nav_button nav_collapse">Course Materials</div>
      <div class="s_nav_button">
        <ul>
          <li><a href="lecture.php">Lecture</a></li>
          <li><a href="lab.php">Labs</a></li>
        </ul>
      </div>


      <!--We want to show Markus.-->
      <div class="p_nav_button"><a href="https://markus.utsc.utoronto.ca/cscb20w18">MarkUs</a></div>

      <!--We want to show Piazza.-->
      <div class="p_nav_button"><a href="https://piazza.com/">Piazza</a></div>

      <!--Term Work heading.-->
      <!--We want to show what a student can be working on, so this involves
       assignments and tests.-->
       <div class="p_nav_button nav collapse">Term Work</div>
      <div class="s_nav_button">
        <ul>
          <li><a href="assignments.php">Assignments</a></li>
          <li><a href="exercises.php">Exercises</a></li>
          <li><a href="tests.php">Tests</a></li>
        </ul>
      </div>
      
      <!-- show the navigation bar according account,
      We'll just handle that with a separate package
      -->
      <?php
        include 'navigation_package.php';
      ?>

    </div>

    <!--Content Page.-->
    <div class="content">
    
      <div id="pg_title">Marking</div>
      
       <form onsubmit="enter_marks.php" method="post">
       <div class="mark_styling">
           Material Type:
           <select name="material">
             <option>Lab 1</option>
             <option>Lab 2</option>
             <option>Lab 3</option>
             <option>Assignment 1</option>
             <option>Assignment 2</option>
             <option>Term Test 1</option>
             <option>Term Test 2</option>
             <option>Final Exam</option>
            </select>
          <div>UTORid of Student: <input type="text" name="utorid"/></div>
          <div>Mark: <input type="text" name="mark"/></div>
          <input type="submit" name="Submit"/>
		  </form>
        </div>
    </div>
  </div>


  <!--Footer at the bottom.-->
  <div class="footer">
    <!--Footer text.-->
    <div class="reg_hyper">
      <ul>
        <!--With the UTSC CS faculty page.-->
        <li>
          <a href="https://www.utsc.utoronto.ca/cms/faculty-of-computer-science">
            UTSC CS Faculty
          </a>
        </li>
        <!--What sessional dates exist.-->
        <li>
          <a href="http://hive.utsc.utoronto.ca/public/registrar/Calendar%20Sessional%20Dates%20for%20RO1%20website.pdf">
            Sessional Dates
          </a>
        </li>
        <!--And a UTSC CS course list for prospective students who wish to go
         into CS.-->
        <li>
          <a href="https://utsc.calendar.utoronto.ca/section/computer-science">
            UTSC CS Course List
          </a>
        </li>
    </div>

    <!--Footer pictures.-->
    <div class="footer_pics">
      <ul>
        <!-- MarkUs logo taken from actual website
         https://markus.utsc.utoronto.ca/-->
        <li>
          <a href="https://markus.utsc.utoronto.ca/">
            <img src="images/markus_logo_2.png" width="25px" height="25px" />
          </a>
        </li>
        <!--Piazza Logo taken from
        https://commons.wikimedia.org/wiki/File:Piazza_logo.png-->
        <li>
          <a href="https://piazza.com">
            <img src="images/piazza_logo.png" width="25px" height="30px" />
          </a>
        </li>
      </ul>
    </div>

    <!--Footer blurb.-->
    <div class="blurb">Made by: Sibo Dong and Dann Sioson</div>
  </div>
</body>

</html>
