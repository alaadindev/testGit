<?php
require 'main.php';

$res = array();
$res['success']="false";
$res['hasowned']="false";
$res['hasexplored']="false";
$res['error']="0";
if ($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['username'])
    &&isset($_POST['password'])
    &&isset($_POST['phone'])){
      $username = $_POST['username'];
      $password = $_POST['password'];
      $phone = $_POST['phone'];
      if(check::checkuser($username, $password, $phone)){
        if($stmt = $conn->prepare("INSERT INTO user (username, password, phonenumber) values (?,?,?)")){
          $stmt->bind_param('sss',$username, $password, $phone);
          $stmt->execute();
          $stmt->close();
          $conn->close();
          $res['success']="true";


        }
        else{
          $res['error']="sql_level";
        }

      }else{
          $res['error']="invalid_input";
      }

    }else{
      $res['error']="invalid_input";
    }
}else{
  $res['error']="php_level";
}
$out = json_encode($res);
echo $out;
?>
