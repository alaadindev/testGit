<?php
require 'main.php';
if ($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['username'])
    &&isset($_POST['password'])
    &&isset($_POST['phone'])){
      $username = $_POST['username'];
      $password = $_POST['password'];
      $phone = $_POST['phone'];
      if(check::checkuser($username, $password, $phone)){
        $stmt = $conn->prepare("INSERT INTO user (username, password, phonenumber) value (?,?,?)");
        $stmt->bind_param('sss',$username, $password, $phone);
        $stmt->execute();
        $stmt->close();
        $conn->close();



      }else{

      }

    }else{

    }
}else{

}

?>
