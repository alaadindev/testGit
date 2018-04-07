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
	title: <input type="test" name="title"><br>
	content: <input type="test" name="contents"><br>
	location lat: <input type="test" name="lat">
	location lng: <input type="test" name="lng"><br>
	<input type="submit" value="submit">
<form>
</body>

</html>
