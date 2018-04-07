  GNU nano 2.9.3                    createnote.php
<?php
require 'main.php';
if ($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['title'])
    &&isset($_POST['content'])
    &&isset($_POST['lat'])
    &&isset($_POST['lng'])){
      $title = $_POST['title'];
      $content = $_POST['content'];
      $lat = $_POST['lat'];
	$lng = $_POST['lng'];
      if(check::checknote($title, $content, $lat, $lng)){
        if($stmt = $conn->prepare("INSERT INTO note (title, content,
lat, lng) values (?,?,?,?)")){


        $stmt->bind_param('sssss',$title, $content, $lat, $lng);
        $stmt->execute();
        $stmt->close();
        $conn->close();
      }
      else{
        var_dump($this->$stmt->error);

      }



      }else{

      }

    }else{

    }
}else{

}

?>
