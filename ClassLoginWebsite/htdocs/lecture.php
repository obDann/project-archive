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
      
      <!--Lecture Material heading.-->
      <h1 id="pg_title">Lecture Material</h1>

      <!--Week 1 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start1">
          <div class="title"> Week 1: Introduction</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 2 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start2">
          <div class="title"> Week 2: Schema Diagrams, Relational Algebra</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 3 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start3">
          <div class="title"> Week 3: SQL and MySQL</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 4 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start4">
          <div class="title"> Week 4: MySQL Queries, Creating and Editing Tables</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 5 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start5">
          <div class="title"> Week 5: MySQL Outer Joins, Views, NULL, HTML</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 6 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start6">
          <div class="title"> Week 6: HTML, CSS</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 7 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start7">
          <div class="title"> Week 7: PHP</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 8 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start8">
          <div class="title"> Week 8: PHP and SQL</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 9 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start9">
          <div class="title"> Week 9: JavaScript and jQuery</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
      </div>

      <!--Week 10 box.-->
      <div class="material_grouping">
        <div class="title_block" id="lec_start10">
          <div class="title"> Week 10: jQuery, AJAX, JSON</div>
        </div>
        <ul>
          <li><a href="" class="slides">slides</a></li>
          <li><a href="" class="handout">handout</a></li>
        </ul>
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
