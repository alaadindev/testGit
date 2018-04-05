<?php
$servername = "localhost:3306";
$username = "root";
$password = "root";
$db ="serverDB";
$conn = new mysqli($servername, $username, $password);
if ($conn->connect_error){
  die("connection failed" . $conn->connect_error);
}
?>
