<?php

?>
<html>
<head>

</head>
<body>
  <h2>create user</h2>
<form action= "./control/createuser.php" method="post">
  username: <input type="text" name="username"><br>
  password: <input type="password" name="password"><br>
  phone number: <input type="text" name="phone"><br>
  <input type="submit" value="Submit"><br>
</form>
<form action= "./control/createnote.php" method="post">
  username: <input type= "text" name="username">
  password: <input type="text" name="password"><br>
	title: <input type="text" name="title"><br>
	content: <input type="text" name="contents"><br>
	location lat: <input type="text" name="lat">
	location lng: <input type="text" name="lng"><br>
	<input type="submit" value="submit"><br>
</form>
<form action="./control/getnotes.php" method="post">
  username: <input type="text" name="username"><br>
  password: <input type="password" name="password"><br>
  lat: <input type="text" name="lat">
  lng: <input type="text" name="lng"><br>
  <input type="submit" name="submit">
</form><br>
login:
<br>
<form action="./control/getuser.php" method="post">
  username: <input type="text" name="username">
  password: <input type="text" name="password">
  <input type="submit" name="sumbit">
</form>
<br>
explored notes:
<br>
<form action="./control/getusernotes.php" method="post">
  username: <input type="text" name="username">
  password: <input type="text" name="password">
  <input type="submit" name="submit">
</form>
<br>
<form action="./control/explore.php" method="post">
  username: <input type="text" name="username">
  NoteID: <input type="text" name="noteID">
  <input type="submit" name="submit">
</form>
</body>

</html>
