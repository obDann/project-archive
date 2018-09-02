<!DOCTYPE html>

<?php
  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // first we include our config file
    include 'config.php';
    //then we define our variables
    $utorid = $_POST['utorid'];
    $password = $_POST['password'];
    $first_name = $_POST['first_name'];
    $last_name = $_POST['last_name'];
    $account_type = $_POST['account_type'];
    // the only thing that we can check is if the user
    // already has a utorid under what is inserted
    $sql = "SELECT *";
    $sql = $sql . " FROM accounts";
    $sql = $sql . " WHERE utorid='" . $utorid . "'";
    // then we connect 
    $result = $conn->query($sql);
    // we have to see if the result has one row
    if ($result->num_rows == 1){
      // if there exists an account, we notify the user
      echo "<script>alert(\"There already exists an account under that UTORid\")</script>";
    }
    // we have to check if the user enters null values
    elseif ($utorid == "" || $password == "" || $first_name == "" || $last_name == ""){
      // we have to notify the user that (s)he will not include null values
      echo "<script>alert(\"You cannot enter blank values. Please try again\")</script>";
    }
    // otherwise, we don't see anything wrong
    else{
      echo "<script>alert('Account has been created.')</script>";
      // we can now enter the values
      $sql = "INSERT INTO accounts";
      $sql = $sql . " VALUES ('" . $utorid . "', '" . $first_name . "', '" . $last_name . "', '" . $account_type . "', '" . $password ."')";
      //connect and query
      $conn->query($sql);
      // and alert the user
    }
    
    
  }
?>



<html>

<head>
  <meta charset="utf-8">
  <title>CSCB20: Introduction to Databases and Web Applications</title>
  <link rel="stylesheet" href="css/a2css.css">
  <link rel="stylesheet" href="css/login.css">
</head>

<body>
    <div id="create_title">Create Account</div>
    <div id="create_container">
     <form onsubmit="create_account.php" method="post">
        <div> Your UTORid: <input type="text" name="utorid"/></div>
        <div> Password: <input type="password" name="password"/></div>
        <div> First name: <input type="text" name="first_name"/></div>
        <div> Last name: <input type="text" name="last_name"/></div>
        Account Type:
         <select name="account_type">
           <option>student</option>
           <option>TA</option>
           <option>instructor</option>
         </select>
         <input type="submit" name="Submit"/>
      </form>
      <!--End of form control-->
      <!--We want a login button-->
    </div>
    <button><a href="index.php">Go to login page</a></button>
</body>

</html>
