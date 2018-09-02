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
  <link rel="stylesheet" href="css/instructor.css">
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
      We'll just handle that with a separate package
      -->
      <?php
        include 'navigation_package.php';
      ?>
    </div>
    <!--End of Navigation-->

    <!--Content Page.-->
    <div class="content">
      <div id="pg_title">Remark Requests</div>
      <!--
      In this portion, we're handling with data of the schema
      remark_req(req_num_id, utorid, mat_type, why)
      
      So, what we want to show is
      UTORid:
      Student: first_name last_name
      material: 
      mark:
      out of:
      why:
      -->
      <?php
        // use the config file
        include 'config.php';
        // what we want to go through all rows 
        $sql = "SELECT *";
        $sql = $sql . " FROM remark_req;";
        // run a query
        $result = $conn->query($sql);
        // go through all rows
        
        // we want to index each student
        $index = 1;
        
        while ($row = $result->fetch_assoc()){
          
          // we want a line for visual purposes
          if ($index != 1){
            echo "<div class=\"remark_sep\"></div>";
          }

          // define our main three variables from the remark requests
          $utorid = $row['utorid'];
          $mat = $row['mat_type'];
          $why = $row['why'];

          // what we want to do is to find the first name and last name
          // from the accounts table, so we have to run a query
          $sql = "SELECT *";
          $sql = $sql . " FROM accounts";
          $sql = $sql . " WHERE utorid='" . $utorid . "'";
          // run the query using a new variable to not disrupt
          // our current loop
          $for_names = $conn->query($sql);
          // we should be obtaining one row otherwise its invalid
          $single = $for_names->fetch_assoc();
          // and we should be getting our names
          $first_name = $single['first_name'];
          $last_name = $single['last_name'];
            
          // now we just want to get the mark
          $sql = "SELECT *";
          $sql = $sql . " FROM student_marks";
          $sql = $sql . " WHERE utorid='" . $utorid . "'";
          $sql = $sql . " AND mat_type='" . $mat . "'";
          // run the query
          $for_mark = $conn->query($sql);
          //obtain one row
          $single = $for_mark->fetch_assoc();
          // and get the mark
          $mark = $single['mark'];
            
          // and we want to get the out of mark
          $sql = "SELECT *";
          $sql = $sql . " FROM course_materials";
          $sql = $sql . " WHERE mat_type='" . $mat . "'";
          // run the query
          $for_out_of = $conn->query($sql);
          //obtain one row
          $single = $for_out_of->fetch_assoc();
          // and get the mark
          $out_of = $single['out_of'];
          // and we want the percentage of that mark
          $percentage = number_format($mark / $out_of * 100, 2);
          
          //Now we're ready display the requests, not much style is needed
          echo "<div class=\"from_student\"> Student " . $index . ":</div>";
          echo " UTORid: " . $utorid . "</br>";
          echo " Name: " . $first_name . " " . $last_name . "</br>";
          echo " Material: " . $mat . "</br>";
          echo " Mark: " . $mark . " / " . $out_of . " (" . $percentage . "%) " . "</br>";
          echo " Reason for remark:</br>";
          echo "<p class=\"why\">" . $why . "</p>";


          $index++;

        }
      ?>

    </div>
    <!--End of Content-->
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
