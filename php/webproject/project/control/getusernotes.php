<?php
require "main.php";
$res=array();
$res['success']="false";
$res['hasowned']="false";
$res['hasexplored']="false";
$res['user']="";
$res['pass']="";
if($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['username'])&&isset($_POST['password'])){
    $username = $_POST['username'];
    $password = $_POST['password'];
    if(check::checklogin($username, $password)){

          $sql="SELECT * FROM user WHERE username='".$username."' AND password='".$password."';";
          if($result = $conn->query($sql)){
            if($result->num_rows>0){
              $sql="SELECT * FROM owner where username='".$username."'";
              $result = $conn->query($sql);
              while($row=$result->fetch_assoc()){
                $data1[] = $row;
              }
              $sql="SELECT * FROM explored where username='".$username."'";
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
