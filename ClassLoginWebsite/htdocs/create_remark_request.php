<!DOCTYPE html>

<?php
  session_start();
  // Prevent access if not logged in.
  if (!isset($_SESSION['login_user'])) {
      header("location:login.php"); // CHANGE THIS TO index.php LATER
  }
  if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // First we include our config file.
    include "config.php";
    // Then we define our variables
    $mat_type = $_POST["mat_type"];
    $why = $_POST["why"];
    $utorid = $_SESSION["login_user"];
    // we have to check if the user has a mark under the material type
    // so we run a sql command
    $sql = "SELECT *";
    $sql = $sql . " FROM student_marks";
    $sql = $sql . " WHERE mat_type='" . $mat_type . "'";
    $sql = $sql . " AND utorid='" . $utorid . "'";
    // connect and query
    $result = $conn->query($sql);
    
    // Check if they actually wrote a message.
    if ($why == "") {
      echo "<script>alert('Please enter a meaningful reason')</script>";
    }
    // check if the user has not provided obtained a mark for it
    elseif ($result->num_rows == 0){
      echo "<script>alert('You have not obtained a mark for " . $mat_type . " yet')</script>";
    }
    else {
      $sql = "INSERT INTO remark_req(utorid, mat_type, why) VALUES ('" . $utorid . "','" . $mat_type . "','" . $why . "');";
      echo "<script>alert('Request has been submitted.')</script>";
      // We can now enter the values
      $conn->query($sql);
    }
  }
?>
<html>

<head>
  <meta charset="utf-8">
  <title>CSCB20: Introduction to Databases and Web Applications</title>
  <link rel="stylesheet" href="css/a2css.css">
  <link rel="stylesheet" href="css/remark_req.css">
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
      <div id="pg_title">Submit a Remark Request</div>
      <form action="" method="post">
        <fieldset>
          <legend>Remark Information:</legend>
          <p>
            <label for="material">For what material would you like a remark?</label>
            <select id="material" name="mat_type">
              <option>Assignment 1</option>
              <option>Assignment 2</option>
              <option>Final Exam</option>
              <option>Lab 1</option>
              <option>Lab 2</option>
              <option>Lab 3</option>
              <option>Term Test 1</option>
              <option>Term Test 2</option>
            </select>
          </p>
          <br />
          <p>
            <label for="reason">Why do you want a remark?</label>
            <br />
            <textarea id="reason" name="why"></textarea>
          </p>
          <br />
          <input type="submit" value="Submit Remark Request" />
        </fieldset>
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
