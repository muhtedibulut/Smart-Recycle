<?php
include "db.php";
include "function.php";

$islem = isset($_GET["islem"]) ? addslashes(trim($_GET["islem"])) : null;
$jsonArray = array(); // array değişkenimiz bunu en alta json objesine çevireceğiz.
$jsonArray["hata"] = FALSE; // Başlangıçta hata yok olarak kabul edelim.

$_code = 200; // HTTP Ok olarak durumu kabul edelim.

if($_SERVER['REQUEST_METHOD'] == "PUT") {
     $gelen_veri = json_decode(file_get_contents("php://input")); // veriyi alıp diziye atadık.

    	// basitçe bi kontrol yaptık veriler varmı yokmu diye
     if( isset($gelen_veri->user_id) &&
        isset($gelen_veri->name) &&
     		isset($gelen_veri->surname) &&
     		isset($gelen_veri->tc) &&
     		isset($gelen_veri->phone) &&
     		isset($gelen_veri->email) &&
     		isset($gelen_veri->password)
     	) {

     		// veriler var ise güncelleme yapıyoruz.
				$q = $db->prepare("UPDATE user SET name= :name, surname= :surname, tc= :tc, phone= :phone, email= :email, password= :password WHERE id= :user_id ");
			 	$update = $q->execute(array(
			 			"name" => $gelen_veri->name,
			 			"surname" => $gelen_veri->surname,
			 			"tc" => $gelen_veri->tc,
			 			"phone" => $gelen_veri->phone,
			 			"email" => $gelen_veri->email,
			 			"password" => $gelen_veri->password,
            "user_id" => $gelen_veri ->user_id
			 	));
			 	// güncelleme başarılı ise bilgi veriyoruz.
			 	if($update) {
			 		$_code = 200;
			 		$jsonArray["mesaj"] = "Güncelleme Başarılı";
			 	}
			 	else {
			 		// güncelleme başarısız ise bilgi veriyoruz.
			 		$_code = 400;
					$jsonArray["hata"] = TRUE;
		 			$jsonArray["mesaj"] = $gelen_veri;//"Sistemsel Bir Hata Oluştu";
				}
		}else {
			// gerekli veriler eksik gelirse apiyi kulanacaklara hangi bilgileri istediğimizi bildirdik.
			$_code = 400;
			$jsonArray["hata"] = TRUE;
	 		$jsonArray["mesaj"] = "kullanici_adi,ad_soyad,posta,telefon,user_id Verilerini json olarak göndermediniz.";
		}
} 

else {
	$_code = 406;
	$jsonArray["hata"] = TRUE;
 	$jsonArray["hataMesaj"] = "Geçersiz method!";
}


SetHeader($_code);
$jsonArray[$_code] = HttpStatus($_code);
echo json_encode($jsonArray);

?>