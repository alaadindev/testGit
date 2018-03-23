<?php
  $server = "localhost";
  $user = "alaa";
  $password = "alaa";
  $db = "serverdb";

  $conn = new mysqli($server, $user, $password, $db);
  if($conn->connect_error){
    die("Connection failed");
  }
?>
