<?php
$host = "localhost";
$user = "root";
$pass = "";
$db = "sr";

try {
	$db = new PDO("mysql:host=$host;dbname=$db", $user, $pass);
  //echo "aaaaaaaaaaaaaaConnected successfully";
}catch(PDOException $e) {
	echo $e->getMessage();
}




?>
