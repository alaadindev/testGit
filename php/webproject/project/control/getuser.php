<?php
require "main.php";
$res=array();
$res['success']="false";
if($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['username'])&&isset($_POST['password'])){
    $username = $_POST['username'];
    $password = $_POST['password'];
    if(check::checklogin($username, $password)){

          $sql="SELECT * FROM user WHERE username='".$username."' AND password='".$password."';";
          if($result = $conn->query($sql)){
            if($result->num_rows>0){
              $res['success']="true";
            }else{

            }
          }else{

          }
      }else{

      }
    }else{

    }
  }else{

  }

$out = json_encode($res);
echo $out;
?>
