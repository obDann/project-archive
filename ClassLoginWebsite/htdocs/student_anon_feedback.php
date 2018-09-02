<!DOCTYPE html>

<?php
  session_start();
  if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    include 'config.php';
    $last_name = $_POST['last_name'];
    $fq1 = $_POST['fq1'];
    $fq2 = $_POST['fq2'];
    // we just need to check if the feedback is null
    if ($fq1 == "" || $fq2 == ""){
      echo "<script>alert('The feedback cannot be blank')</script>";
    }
    //otherwise 
    else {
      // get the utorid of the instructor... so we run a sql command
      $sql = "SELECT *";
      $sql = $sql . " FROM accounts";
      $sql = $sql . " WHERE last_name='" . $last_name . "'";
      // connect and query
      $result = $conn->query($sql);
      // get the row
      $row = $result->fetch_assoc();
      // then we specifically want the utorid
      $utorid = $row['utorid'];
      
      // now we can insert values using an sql command
      $sql = "INSERT INTO anon_feedback (utorid, last_name, fq1, fq2)";
      $sql = $sql . " VALUES ('" . $utorid . "', ";
      $sql = $sql . "'" . $last_name . "', ";
      $sql = $sql . "'" . $fq1 . "', ";
      $sql = $sql . "'" . $fq2 . "')";
      //connect and query
      $conn->query($sql);
      // return that the feedback has been entered
      echo "<script>alert(\"Feedback has been submitted.\")</script>";
    }
  }
?>
<html>

<head>
  <meta charset="utf-8">
  <title>CSCB20: Introduction to Databases and Web Applications</title>
  <link rel="stylesheet" href="css/a2css.css">
  <!-- Adjust the css according to the user. -->
  <?php
    echo "<link rel=\"stylesheet\" href=\"css/" . $_SESSION["user_type"] . ".css\">";
  ?>
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
      <div class="p_nav_button nav_collapse"><a href="https://markus.utsc.utoronto.ca/cscb20w18">MarkUs</a></div>

      <!--We want to show Piazza.-->
      <div class="p_nav_button nav_collapse"><a href="https://piazza.com/">Piazza</a></div>

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
      We'll just handle that with a seperate package
      -->
      <?php
        include 'navigation_package.php';
      ?>
    </div>
    <!--End of Navigation-->

    <!--Content Page.-->
    <div class="content">
      <!-- Personalized welcome message REMEMBER TO ADD IN THE BUTTTON 
      <?php
        echo "<div id=\"pg_title\">Welcome " . $_SESSION["first_name"] . " " . $_SESSION["last_name"] . "!</div>";
      ?>
      -->
              <h1 id="pg_title">Anonymous Feedback</h1>
      <p>The purpose anonymous feedback in this course is to obtain vital criticism from any CSCB20 community member, making the overall CSCB20 experince better. If you have a general question, and would like a response, please refer to the Piazza board as many community members would be happy to help you. If you have personal issues in regards to the course, it is highly recommended that you e-mail the instructor or go to office hours to discuss such matters. The purpose of this page is if you do not fall under these categories and you wish to speak to an instructor without a personal tag.</p>
      <!--We want to make a form so that something happens when the user hits submit -->
       <form onsubmit="student_anon_feedback.php" method="post">
         <div class="feedback">
           Instructor's last name:
           <select name="last_name">
             <!-- 
             <option>LEC 01: Prof. Harrington</option>
             -->
             <?php
                // first include our config
                include 'config.php';
                // now we want to run a sql command of the instructors
                $sql = "SELECT *";
                $sql = $sql . " FROM accounts";
                $sql = $sql . " WHERE account_type='instructor'";
                // connect and query
                $result = $conn->query($sql);
                // we assume that there are instructors
                while ($row = $result->fetch_assoc()){
                    $ins_lastname = $row['last_name'];
                    // now we have to show all options
                    echo "<option>" . $ins_lastname . "</option>";
                }
             ?>
             
            </select>
          </div>
          <div class="feedback">What do you dislike about this instructor?<br></div>
          <textarea cols="60" rows="10" id="make_empty" name="fq1"></textarea>
          <div class="feedback">What would you like to change about this course?<br></div>
          <textarea cols="60" rows="10" id="make_empty" name="fq2"></textarea>
          <br>
          <input type="Submit">
		  </form>
      
      
      
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

