<?php
class check{
  static public function checkuser($username, $password, $phone){
    return true;
  }
  static public function checknote($username,$password,$title, $content, $lat, $lng){
	return true;
  }
  static public function checkgetnote($username, $password, $lat, $lng){
    return true;
  }
}
?>
