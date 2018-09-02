<?php
  session_start();
  // Clear the session
  unset($_SESSION["login_user"]);
  unset($_SESSION["user_type"]);
  unset($_SESSION["first_name"]);
  unset($_SESSION["last_name"]);
  // Go back to login page.
  header("Refresh: 0, URL = index.php"); // REMEMBER TO CHANGE login.php TO index.php
?>
