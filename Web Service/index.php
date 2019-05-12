<?php
include "db.php";
include "function.php";
$islem = isset($_GET["islem"]) ? addslashes(trim($_GET["islem"])) : null;
$jsonArray = array(); // array değişkenimiz bunu en alta json objesine çevireceğiz.
$jsonArray["hata"] = FALSE; // Başlangıçta hata yok olarak kabul edelim.

$_code = 200; // HTTP Ok olarak durumu kabul edelim.


    // üye ekleme kısmı burada olacak. CREATE İşlemi
 if($_SERVER['REQUEST_METHOD'] == "POST") {

 	// verilerimizi post yöntemi ile alalım.
    $name = addslashes($_POST["name"]);
    $surname = addslashes($_POST["surname"]);
    $tc = addslashes($_POST["tc"]);
    $phone = addslashes($_POST["phone"]);
    $email = addslashes($_POST["email"]);
    $password = addslashes($_POST["password"]);

    // Kontrollerimizi yapalım.
    // gelen tc veya e-posta veri tabanında kayıtlı mı kontrol edelim.
    $users = $db->query("SELECT * from user WHERE tc='$tc' OR email='$email'");

    if(empty($name) || empty($surname) || empty($tc) || empty($email) || empty($password)) {
    	$_code = 400;
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["mesaj"] = "Boş Alan Bırakmayınız."; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
    /*else if(!filter_var(email,FILTER_VALIDATE_EMAIL)) {
    	$_code = 400;
        $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["hataMesaj"] = "Geçersiz E-Posta Adresi"; // Hatanın neden kaynaklı olduğu belirtilsin.
    }else if($kullaniciAdi != kullaniciAdi($kullaniciAdi)){ // kullaniciAdi fonksiyonunu db.php dosyası içerisinden bakabilirsiniz.
        $_code = 400;
        $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["hataMesaj"] = "Geçersiz Kullanıcı Adı"; // Hatanın neden kaynaklı olduğu belirtilsin.
    }*/else if($users->rowCount() !=0) {
    	$_code = 400;
        $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["mesaj"] = "TC Veya E-Posta Alınmış.";
    }else {

			$ex = $db->prepare("insert into user set
			name= :nm,
			surname= :sn,
			tc= :tc,
			phone= :ph,
      email= :eml,
			password= :pass");
		$ekle = $ex->execute(array(
			"nm" => $name,
			"sn" => $surname,
			"tc" => $tc,
			"ph" => $phone,
			"eml" => $email,
      "pass" => $password

		));


		if(	$ekle) {
			$_code = 201;
			$jsonArray["mesaj"] = "Ekleme Başarılı.";
		}else {
			$_code = 400;
			 $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
       		 $jsonArray["mesaj"] = "Sistem Hatası.";
		}
	}
}else if($_SERVER['REQUEST_METHOD'] == "PUT") {
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
} else if($_SERVER['REQUEST_METHOD'] == "DELETE") {

    // üye silme işlemi burada olacak. DELETE işlemi
    if(isset($_GET["user_id"]) && !empty(trim($_GET["user_id"]))) {
		$user_id = intval($_GET["user_id"]);
		$userVarMi = $db->query("select * from user where id='$user_id'")->rowCount();
		if($userVarMi) {

			$sil = $db->query("delete from user where id='$user_id'");
			if( $sil ) {
				$_code = 200;
				$jsonArray["mesaj"] = "Üyelik Silindi.";
			}else {
				// silme başarısız ise bilgi veriyoruz.
				$_code = 400;
				$jsonArray["hata"] = TRUE;
	 			$jsonArray["mesaj"] = "Sistemsel Bir Hata Oluştu";
			}
		}else {
			$_code = 400;
			$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    		$jsonArray["mesaj"] = "Geçersiz id"; // Hatanın neden kaynaklı olduğu belirtilsin.
		}
	}else {
		$_code = 400;
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    	$jsonArray["mesaj"] = "Lütfen user_id değişkeni gönderin"; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
} else if($_SERVER['REQUEST_METHOD'] == "GET") {

  /*// üye bilgisi listeleme burada olacak. GET işlemi
  if(isset($_GET["user_id"]) && !empty(trim($_GET["user_id"]))) {
  $user_id = intval($_GET["user_id"]);
  $userVarMi = $db->query("select * from user where id='$user_id'")->rowCount();
  if($userVarMi) {

    $bilgiler = $db->query("select * from  user where id='$user_id'")->fetch(PDO::FETCH_ASSOC);
    $jsonArray["uye-bilgileri"] = $bilgiler;
    $_code = 200;

  }else {
    $_code = 400;
    $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
      $jsonArray["mesaj"] = "Üye bulunamadı"; // Hatanın neden kaynaklı olduğu belirtilsin.
    }
  }*/
    // üye bilgisi listeleme burada olacak. GET işlemi
    if(isset($_GET["tc"]) && !empty(trim($_GET["tc"]))) {
		$tc = intval($_GET["tc"]);
		$userVarMi = $db->query("select * from user where tc='$tc'")->rowCount();
		if($userVarMi) {

			$bilgiler = $db->query("select * from  user where tc='$tc'")->fetch(PDO::FETCH_ASSOC);
			$jsonArray["uye-bilgileri"] = $bilgiler;
			$_code = 200;

		}else {
			$_code = 400;
			$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    		$jsonArray["mesaj"] = "Üye bulunamadı"; // Hatanın neden kaynaklı olduğu belirtilsin.
		}
	}else {
		$_code = 400;
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    	$jsonArray["mesaj"] = "Lütfen user değişkeni gönderin"; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
}else {
	$_code = 406;
	$jsonArray["hata"] = TRUE;
 	$jsonArray["mesaj"] = "Geçersiz method!";
}


SetHeader($_code);
$jsonArray[$_code] = HttpStatus($_code);
echo json_encode($jsonArray);
?>
