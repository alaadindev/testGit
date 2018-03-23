<?php
required 'main.php';
if ($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['username'])
    &&isset($_POST['password'])
    &&isset($_POST['phone'])){
      username = $_POST['username'];
      password = $_POST['password'];
      phone = $_POST['phone'];
      if(check::checkuser(username, password, phone)){
        $sql = "INSERT INTO "




      }else{

      }

    }else{

    }
}else{

}

?>
