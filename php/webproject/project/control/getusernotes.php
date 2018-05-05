<?php
require "main.php";
$res=array();
$res['success']="false";
$res['hasowned']="false";
$res['hasexplored']="false";
$res['user']="";
$res['pass']="";
$data1=null;
$data2=null;
if($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['username'])&&isset($_POST['password'])){
    $username = $_POST['username'];
    $password = $_POST['password'];
    if(check::checklogin($username, $password)){

          $sql="SELECT * FROM user WHERE username='".$username."' AND password='".$password."';";
          if($result = $conn->query($sql)){
            if($result->num_rows>0){
              $sql="SELECT notes.noteID, notes.title, notes.dates,
               notes.contents, notes.lat, notes.lng ,owner.username
              FROM notes INNER JOIN owner ON notes.noteID=owner.noteID
              where owner.username ='".$username."'";
              $result = $conn->query($sql);
              while($row=$result->fetch_assoc()){
                $data1[] = $row;
              }
              $sql="SELECT notes.noteID, notes.title, notes.dates,
               notes.contents, notes.lat, notes.lng ,explored.username
              FROM notes INNER JOIN explored ON notes.noteID=explored.noteID
              where explored.username ='".$username."'";
              $result =$conn->query($sql);
              while($row=$result->fetch_assoc()){
                $data2[] = $row;
              }
              $res["success"] ="true";

              if($data1!=null)
                $res["hasowned"]="true";
              if($data2!=null)
                $res["hasexplored"]="true";

              $res["owned"]=$data1;
              $res["explored"]=$data2;
              $res['user']=$username;
              $res['pass']=$password;
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
