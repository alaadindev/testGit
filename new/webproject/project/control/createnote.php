  GNU nano 2.9.3                    createnote.php
<?php
require 'main.php';
if ($_SERVER['REQUEST_METHOD']==='POST'){
  if(isset($_POST['title'])
    &&isset($_POST['contents'])
    &&isset($_POST['lat'])
    &&isset($_POST['lng'])){
      $title = $_POST['title'];
      $contents = $_POST['contents'];
      $lat = (float)$_POST['lat'];
	    $lng = (float)$_POST['lng'];
      if(check::checknote($title, $contents, $lat, $lng)){
        if ($stmt = $conn->prepare("INSERT INTO notes (title ,dates ,contents, lat, lng) values (?,now(),?,?,?)")){
          $stmt->bind_param('ssdd',$title, $contents, $lat, $lng);
          $stmt->execute();
          echo $conn->error;
          echo "<br>" . $title .'<br>'. $contents .'<br>' . $lat .' : '. $lng;
          $stmt->close();
          $conn->close();
        }else{
          echo $conn->error . "h";
        }
      }else{
          echo "error";
          echo $title . "" . $contents . $lat . $lng;
      }

    }else{
    echo "fill the fields plz";
    }
}else{

}

?>
