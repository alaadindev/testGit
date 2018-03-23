<?php
  $server = "localhost";
  $user = "alaa";
  $password = "alaa";

  $conn = new mysqli($server, $user, $password);
  if($conn->connect_error){
    die("Connection failed");
  }
?>
