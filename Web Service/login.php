<?php
include "db.php";
include "function.php";
$islem = isset($_GET["islem"]) ? addslashes(trim($_GET["islem"])) : null;
$jsonArray = array(); // array değişkenimiz bunu en alta json objesine çevireceğiz.
$jsonArray["hata"] = FALSE; // Başlangıçta hata yok olarak kabul edelim.

$_code = 200; // HTTP Ok olarak durumu kabul edelim.


if($_SERVER['REQUEST_METHOD'] == "GET") {

    // üye bilgisi listeleme burada olacak. GET işlemi
    if(isset($_GET["tc"]) && !empty(trim($_GET["tc"]))) {
		$tc = intval($_GET["tc"]);
		$userVarMi = $db->query("select * from user where id='$tc'")->rowCount();
		if($userVarMi) {

			$bilgiler = $db->query("select * from  user where id='$tc'")->fetch(PDO::FETCH_ASSOC);
			$jsonArray["uye-bilgileri"] = $bilgiler;
			$_code = 200;

		}else {
			$_code = 400;
			$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    		$jsonArray["hataMesaj"] = "Üye bulunamadı"; // Hatanın neden kaynaklı olduğu belirtilsin.
		}
	}else {
		$_code = 400;
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    	$jsonArray["hataMesaj"] = "Lütfen tc değişkeni gönderin"; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
}else {
	$_code = 406;
	$jsonArray["hata"] = TRUE;
 	$jsonArray["hataMesaj"] = "Geçersiz method!";
}


SetHeader($_code);
$jsonArray[$_code] = HttpStatus($_code);
echo json_encode($jsonArray);
?>
