<?php
require 'main.php';

$res = array();
$res['success']="false";
$res['hasowned']="false";
$res['hasexplored']="false";
$res['error']="0";
if($_SERVER['REQUEST_METHOD']==="POST"){
  if(isset($_POST['username'])&&
      isset($_POST['password'])&&
      isset($_POST['title'])&&
      isset($_POST['contents'])&&
      isset($_POST['lat'])&&
      isset($_POST['lng'])){
      $username = $_POST['username'];
      $password = $_POST['password'];
      $title = $_POST['title'];
      $contents = $_POST['contents'];
      $lat = (float)$_POST['lat'];
	    $lng = (float)$_POST['lng'];
      if(check::checknote($username, $password, $title, $contents, $lat, $lng)){
        $sql="SELECT * FROM user WHERE username='".$username."' and password='".$password."';";
        $result = $conn->query($sql);
        if($result->num_rows>0){
          if ($stmt = $conn->prepare("INSERT INTO notes (title ,dates ,contents, lat, lng) values (?,now(),?,?,?)")){
            $stmt->bind_param('ssdd',$title, $contents, $lat, $lng);
            $stmt->execute();
            $noteID=(int)$conn->insert_id;

            $sql = "INSERT INTO owner (username, noteID) VALUES ('$username', '$noteID')";
            if($conn->query($sql)){
              $res['success']="true";

            }
            $stmt->close();
            $conn->close();
          }else{
            //echo $conn->error . "h";
          }
        }else{
          //echo "wrong password or username";
          //$conn->close();
        }
      }else{
          //echo "error";
          //echo $title . "" . $contents . $lat . $lng;
      }

    }else{
    //echo "fill the fields plz";
    }
}else{

}

$out = json_encode($res);
echo $out;
?>
