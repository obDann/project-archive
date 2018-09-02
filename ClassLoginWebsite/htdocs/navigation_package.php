<?php
  // WILL NEED POST VARIABLE SO FOR NOW, THIS IS GOING TO BE FORMATTED HORRIBLY ###################################################################################################
  $account_type = $_SESSION["user_type"];
  // students should be able to view their marks
  // submit a remark request
  // provide anonymous feedback
  if ($account_type == "student"){
    echo "<div class=\"p_nav_button\"><a href=\"student_marks.php\">View my marks</a></div>";
    echo "<div class=\"p_nav_button\"><a href=\"create_remark_request.php\">Remark Request</a></div>";
    echo "<div class=\"p_nav_button\"><a href=\"student_anon_feedback.php\">Provide Anonymous Feedback</a></div>";
  }
  //TAs and instructors are allowed to view remark requests
  // and enter marks
  if ($account_type == "instructor" || $account_type == "TA"){
    echo "<div class=\"p_nav_button\"><a href=\"remark_requests.php\">View Remark Requests</a></div>";
    echo "<div class=\"p_nav_button\"><a href=\"enter_marks.php\">Enter Marks</a></div>";
  }
  // and instructors are allowed to view marks and feedback
  if ($account_type == "instructor"){
    echo "<div class=\"p_nav_button\"><a href=\"view_feedback.php\">View Feedback</a></div>";
    echo "<div class=\"p_nav_button\"><a href=\"all_marks.php\">View All Marks</a></div>";
  }
  echo "<div class=\"p_nav_button\"><a href=\"logout.php\">Sign Out</a></div>";
?>