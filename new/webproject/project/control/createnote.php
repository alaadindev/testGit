  GNU nano 2.9.3                    createnote.php
<?php
require 'main.php';
if ($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['title'])
    &&isset($_POST['content'])
    &&isset($_POST['lat'])
    &&isset($_POST['at'])){
      $title = $_POST['title'];
      $content = $_POST['content'];
      $lat = $_POST['lat'];
	$at = $_POST['at'];
      if(check::checknote($title, $content, $lat, $at)){
        $stmt = $conn->prepare("INSERT INTO user (username, password, 
phonenumber) value (?,?,?)");
        $stmt->bind_param('sssdd',$title, $content, $lat, $at);
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



