<?php
require 'main.php';

$res = array();
$res['success']="false";
$res['hasowned']="false";
$res['hasexplored']="false";
$res['user']="";
$res['pass']="";

if($_SERVER['REQUEST_METHOD']==="POST"){
  if(isset($_POST['username'])&&isset($_POST['owner'])&&isset($_POST['noteID'])){
      $username = $_POST['username'];
      $owner = $_POST['owner'];
      $noteID = (int)$_POST['noteID'];
      if(check::checkexplore($username,$noteID)){

        $sql="SELECT * FROM explored WHERE username='".$owner."' AND noteID='".$noteID."'";
        $result = $conn->query($sql);
          if($result->num_rows==0){
            $sql="SELECT * FROM owner WHERE username='".$username."' AND noteID='".$noteID."'";
            $result = $conn->query($sql);
              if($result->num_rows==0){
            $sql = "INSERT INTO explored (username, noteID) VALUES ('$username', '$noteID')";
           if($conn->query($sql)){
             $sql="SELECT username, noteID, title, contents, dates, lat,lng FROM notes,user WHERE noteID='".$noteID."';";
             if($result = $conn->query($sql))
             if($row=$result->fetch_assoc()){
              $res['success']="true";
              $res['data']=$row;
            }
           }
          }else{

          }
        }else{

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
