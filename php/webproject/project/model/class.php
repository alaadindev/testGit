<?php
  class user{
    protected $username;
    protected $password;
    protected $phone;

    function getname(){return $username;}
    function getpassword(){return $password;}
    function getphone(){return $phone;}

    function __construct($username, $password, $phone){
      $this->$username = $username;
      $this->$password = $password;
      $this->$phone = $phone;
    }
  }
  class note{
    protected $noteID;
    protected $title;
    protected $date;
    protected $location;
    protected $content;
    protected $owner;

    function getnoteID(){return $noteID;}
    function gettitle(){return $title;}
    function getdate(){return $date;}
    function getlocation(){return $location;}
    function getcontent(){return $content;}
    function getowner(){return $owner;}

    function __construct($noteID, $title, $date, $location, $content, $owner){
      $this->$noteID = $noteID;
      $this->$title = $title;
      $this->$date = $date;
      $this->$location = $location;
      $this->$content = $content;
      $this->$owner = $owner;
  }
}
?>
