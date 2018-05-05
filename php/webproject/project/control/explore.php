<?php
require 'main.php';

$res = array();
$res['success']="false";
if($_SERVER['REQUEST_METHOD']==="POST"){
  if(isset($_POST['username'])&&isset($_POST['noteID'])){
      $username = $_POST['username'];
      $noteID = (int)$_POST['noteID'];
      if(check::checkexplore($username,$noteID)){
         $sql = "INSERT INTO owner (username, noteID) VALUES ('$username', '$noteID')";
        if($conn->query($sql)){
          $res['success']="true";
        }
        $conn->close();
      }else{
            //echo $conn->error . "h";
          }

    }else{
    //echo "fill the fields plz";
    }
}else{

}

$out = json_encode($res);
echo $out;
?>
