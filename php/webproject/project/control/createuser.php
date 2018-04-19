<?php
require 'main.php';
?>
<?php
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
          $res =array();
          $res['success']="true";
          $out = json_encode($res);
          echo $out;

        }
        else{
         echo $conn->error;
        }

      }else{

      }

    }else{

    }
}else{

}

?>
