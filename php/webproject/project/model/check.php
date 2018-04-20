<?php
class check{
  static public function checkuser($username, $password, $phone){
    return $username!=""&&$password!=""&&$phone!="";
  }
  static public function checklogin($username, $password){
    return $username!=""&&$password!="";
  }
  static public function checknote($username,$password,$title, $content, $lat, $lng){
	return $username!=""&&$password!=""&&$title!=""&&$content!=""&&$lat!=""&&$lng!="";
  
  }
  static public function checkgetnote($username, $password, $lat, $lng){
    return $username!=""&&$password!=""&&$lat!=""&&$lng!="";
  }
}
?>
