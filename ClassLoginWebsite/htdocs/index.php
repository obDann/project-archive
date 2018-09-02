<!DOCTYPE html>

<!-- CHANGE THIS NAME TO index.php LATER -->
<!-- Start a session. Note: This must be included in every file -->
<?php
  include("config.php");
  session_start();

  if ($_SERVER["REQUEST_METHOD"] == "POST") {
      // Recieve the username sent from the form.

      $myusername = $conn->real_escape_string($_POST["username"]);
      $mypassword = $conn->real_escape_string($_POST["password"]);

      $sql = "SELECT account_type,first_name,last_name FROM accounts WHERE utorid = '$myusername' AND password = '$mypassword'";
      $result = $conn->query($sql);
      $row = $result->fetch_assoc();
      $count = $result->num_rows;

      // If the result matched up to the username and password, then we must have
      // a table with only one row.

      if ($count == 1) {
          $_SESSION["login_user"] = $myusername;
          $_SESSION["user_type"] = $row["account_type"];
          $_SESSION["first_name"] = $row["first_name"];
          $_SESSION["last_name"] = $row["last_name"];
          header("Location: home.php");
      } else {
          echo "<script>alert('Incorrect Login Information');</script>";
      }
  }
?>
<html>

<head>
  <meta charset="utf-8" />
  <title>CSCB20: Introduction to Databases and Web Applications</title>
  <link rel="stylesheet" href="css/login.css">
</head>

<body>
  <!--Create a header. We want this on top of the website.-->
  <div class="header_block">
    <h1 class="header">
      <a href="">
        CSCB20: Introduction to Databases and Web Applications
      </a>
    </h1>
  </div>

  <form action="" method="post">
    <div class="container">
      <label for="username"><b>Username</b></label>
      <input type="text" name="username" placeholder="Enter username" required="required" autofocus="autofocus" /><br />

      <label for="password"><b>Password</b></label>
      <input type="password" name="password" placeholder="Enter password" required="required" /><br />
      <input type="submit" value=" Login " name="login" />
    </div>
  </form>

  <div class="container">
    <button type="button"><a href="create_account.php">Create account</a></button>
  </div>
</body>

</html>
