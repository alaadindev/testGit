<?php
require 'main.php';
if($_SERVER['REQUEST_METHOD']==="POST"){
    if(isset($_POST ['username'])&&
        isset($_POST['password'])&&
        isset($_POST['lat'])&&
        isset($_POST['lng'])){
          $username = $_POST['username'];
          $password = $_POST['password'];
          $lat = (float)$_POST['lat'];
          $lng = (float)$_POST['lng'];
          if(check::checkgetnote($username, $password, $lat, $lng)){
              $sql = "SELECT * FROM user WHERE username ='".$username."' AND password='".$password."';";
              $result = $conn->query($sql);
              if($result->num_rows>0){
                $sql="SELECT *, (6371 * acos (cos ( radians($lat) )".
                "* cos( radians( lat ) )* cos( radians( lng ) - radians($lng) )".
                "+ sin ( radians($lat) )* sin( radians( lat ) ))) AS distance FROM notes HAVING distance > 0 ORDER BY distance LIMIT 0 , 20";
                $result = $conn->query($sql);
                header('content-type: application/json');
                $data = array();
                while($row=$result->fetch_assoc()){
                  $data[] = $row;
                }
                echo json_encode($data);
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
