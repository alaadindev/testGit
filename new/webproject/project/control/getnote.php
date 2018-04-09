<?php
require('main.php');
if($_SERVER['REQUEST_METHOD']==="POST"){
    if(isset($_POST ['username'])&&
        isset($_POST['password'])&&
        isset($_POST['lat'])&&
        isset($_POST['lng'])){
          $username = $_POST['username'];
          $password = $_POST['password'];
          $lat = (float)$_POST['lat'];
          $lng = (float)$_POST['lng'];
          if(checkgetnote($username, $password, $lat, $lng)){
              $sql = "SELECT * FROM user WHERE username = $username AND password=$password";
              $result = $conn->query($sql);
              if($result->num_rows>0){
                $sql="SELECT getDistance(lat,lng,$lat,$lng) as distance
FROM notes";
              }else{
                echo "wrong password or username";
              }
          }else{
            echo "invalid input";
          }
        }else{
          echo "fill the fields";
        }
}
?>
