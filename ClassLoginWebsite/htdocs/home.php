<!DOCTYPE html>

<?php
  session_start();
  // Prevent access if not logged in.
  if (!isset($_SESSION['login_user'])) {
      header("location:index.php"); // CHANGE THIS TO index.php LATER
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
      
      <!-- Personalized welcome message REMEMBER TO ADD IN THE BUTTTON -->
      <?php
        $spec_msg = "<div id=\"pg_title\">Welcome " . $_SESSION["first_name"] . " " . $_SESSION["last_name"] . "!</div>";
        // Add even more personalized messages according to user type
        if ($_SESSION["user_type"] == "student") {
          $spec_msg = $spec_msg . "<div id=\"submsg\">Click on the side to view your marks</div><br />";
        } else if ($_SESSION["user_type"] == "TA") {
          $spec_msg = $spec_msg . "<div id=\"submsg\">Click on the side to view remark requests</div><br />";
        } else if ($_SESSION["user_type"] == "instructor") {
          $spec_msg = $spec_msg . "<div id=\"submsg\">Click on the side to view student marks</div><br />";
        }
        echo $spec_msg;
      ?>
      
      <h2 class="heading1">Course Overview</h2>
      <br />
      <p class="overview">
        Welcome to CSCB20! The text here should be related to CSCB20 in some
        way! This is more text. Filler text. Filler text. Filler text.
        Filler text. Filler text. Filler text. Filler text. Filler text.
      </p>
      <br />
      <p class="success">
        Some keys to success are:
        <br />
        <br />
        <ul class="advice">
          <li>Practice</li>
          <li>Practice</li>
          <li>Practice</li>
          <li>Practice</li>
          <li>Google and StackOverflow</li>
        </ul>
      </p>
      <br />
      <p class="syllabus">
        Here is the <a href="">syllabus</a> which contains
        all relevant course info.
      </p>
      <br />
      <br />

      <h2 class="heading1">Course Team</h2>
      <br />

      <!--First div table for instructor.-->
      <div class="divTable">
        <div class="divTableBody">
          <div class="divTableRow">
            <div class="divTableCell" id="row_name">&nbsp;Instructor:</div>
            <div class="divTableCell" id="name">&nbsp;Abbas Attarwala</div>
          </div>
      <div class="divTableRow">
        <div class="divTableCell" id="row_name">&nbsp;Email:</div>
        <div class="divTableCell">&nbsp;abbas.attarwala@utsc.utoronto.ca</div>
      </div>
      <div class="divTableRow">
        <div class="divTableCell" id="row_name">&nbsp;Office:</div>
        <div class="divTableCell">&nbsp;&lt;fakeoffice&gt;</div>
      </div>
        </div>
      </div>
      <br />

      <!--Div tables for TAs.-->
      <div class="divTable">
        <div class="divTableBody">
          <div class="divTableRow">
            <div class="divTableCell" id="row_name">&nbsp;<b>TA:</b></div>
            <div class="divTableCell" id="name">&nbsp;TA1 Last1</div>
            <div class="divTableCell" id="name">&nbsp;TA2 Last2</div>
            <div class="divTableCell" id="name">&nbsp;TA3 Last3</div>
          </div>
      <div class="divTableRow">
        <div class="divTableCell" id="row_name">&nbsp;Email:</div>
        <div class="divTableCell">&nbsp;ta1.last1@mail.utoronto.ca</div>
        <div class="divTableCell">&nbsp;ta2.last2@mail.utoronto.ca</div>
        <div class="divTableCell">&nbsp;ta3.last3@mail.utoronto.ca</div>
      </div>
      <div class="divTableRow">
        <div class="divTableCell" id="row_name">&nbsp;Office:</div>
        <div class="divTableCell">&nbsp;&lt;fakeoffice1&gt;</div>
        <div class="divTableCell">&nbsp;&lt;fakeoffice2&gt;</div>
        <div class="divTableCell">&nbsp;&lt;fakeoffice3&gt;</div>
      </div>
        </div>
      </div>
      <br />
      <br />

      <!--Course News-->
      <h2 class="heading1">Course News</h2>
      <br />
      <p class="news">
        Filler Text.Filler Text.Filler Text.Filler Text.Filler Text.
        Filler Text.Filler Text.Filler Text.Filler Text.Filler Text.
        Filler Text.Filler Text.Filler Text.Filler Text.Filler Text.
      </p>
      
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

