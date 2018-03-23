<?php
  class user{
    protected var username;
    protected var password;
    protected var phone;

    public getname(){return username;}
    public getpassword(){return password;}
    public getphone(){return phone;}

    public __construct(username, password, phone){
      this->username=username;
      this->password = password;
      this->phone = phone;
    }
  }
  class note{
    protected noteID;
    protected title;
    protected date;
    protected location;
    protected content;
    protected owner;

    public getnoteID(){return noteID;}
    public gettitle(){return title;}
    public getdate(){return date;}
    public getlocation(){return location;}
    public getcontent(){return content;}
    public getowner(){return owner;}

    public __construct(noteID, title, date, location, content, owner){
      this->noteID = noteID;
      this->title = title;
      this->date = date;
      this->location = location;
      this->content = content;
      this->owner = owner;
  }
}
?>
