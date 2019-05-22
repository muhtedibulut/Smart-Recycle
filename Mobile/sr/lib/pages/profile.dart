import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:sr/util/text.dart';

class profile extends StatefulWidget {
  @override
  _profileState createState() => _profileState();
}

class _profileState extends State<profile> {
  TextEditingController nameController = new TextEditingController();
  TextEditingController surnameController = new TextEditingController();
  TextEditingController tcController = new TextEditingController();
  TextEditingController emailController = new TextEditingController();
  TextEditingController phoneController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();

  String id;
  String name;
  String surname;
  String tc;
  String email;
  String phone;
  String password;

  /*Future<bool> smsCodeDialog(BuildContext context) {
    return showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return new AlertDialog(
            title: Text('Enter sms Code'),
            content: TextField(
              onChanged: (value) {
                //this.smsCode = value;
              },
            ),
            contentPadding: EdgeInsets.all(10.0),
            actions: <Widget>[
              new FlatButton(
                child: Text('Done'),
                onPressed: () {
                  Navigator.of(context).pop();
                  FirebaseAuth.instance.currentUser().then((user) {
                    if (user != null) {
                      Navigator.of(context).pop();
                      Navigator.of(context).pushReplacementNamed('/homepage');
                    } else {
                      Navigator.of(context).pop();
                      signIn();
                    }
                  });
                },
              )
            ],
          );
        });
  }*/

  void _showDialog(String dialog) {
    // flutter defined function
    showDialog(
      context: context,
      builder: (BuildContext context) {
        // return object of type Dialog
        return AlertDialog(
          title: new Text("PROFİL GÜNCELLEME"),
          content: new Text(dialog),
          actions: <Widget>[
            // usually buttons at the bottom of the dialog
            new FlatButton(
              child: new Text("TAMAM"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  getProfile() {
    http.get("http://muhtedibulut.com/SR?tc=" + "1").then((cevap) {
      print(cevap.statusCode);
      print(cevap.body.length);
      var jsonData = json.decode(cevap.body);
      print(jsonData);
      print(jsonData["hata"]);
      bool error = jsonData["hata"];
      if (!error) //Hata yok ise
      {
        id = jsonData["data"]["id"];
        name = jsonData["data"]["name"];
        surname = jsonData["data"]["surname"];
        tc = jsonData["data"]["tc"];
        email = jsonData["data"]["email"];
        phone = jsonData["data"]["phone"];
        password = jsonData["data"]["password"];

        print(jsonData["data"]);

        nameController.text = name;
        surnameController.text = surname;
        tcController.text = tc;
        phoneController.text = phone;
        emailController.text = email;
        passwordController.text = password;
      } else {
        print(jsonData["hata"]);
      }
    });
  }

  updateProfile() {
    var url = "http://muhtedibulut.com/SR/profile.php";
    var body = json.encode({
      "user_id": id,
      "name": nameController.text,
      "surname": surnameController.text,
      "tc": tcController.text,
      "phone": phoneController.text,
      "email": emailController.text,
      "password": passwordController.text
    });

    Map<String, String> headers = {
      'Content-type': 'application/json',
      'Accept': 'application/json',
    };
    http.put(url, body: body).then((cevap) {
      //veri = cevap.body;
      //print(veri.toString());

      var jsonData = json.decode(cevap.body);
      print(jsonData["hata"]);
      print(body);
      print(jsonData["mesaj"]);
      bool error = jsonData["hata"];
      if (!error) //Hata yok ise
      {
        _showDialog("GÜNCELLEME İŞLEMİNİZ BAŞARIYLA GERÇEKLEŞTİ");
      } else {
        _showDialog("ERROR\nGÜNCELLEME İŞLEMİ BAŞARISIZ");
      }
    });

    /*http.put("http://muhtedibulut.com/SR/index.php",
        headers: {"Content-Type": "application/octet-stream"},
        body:
    {
      "user_id" : id,
      "name" : nameController.text,
      "surname" : surnameController.text,
      "tc" : tcController.text,
      "phone" : phoneController.text,
      "email" : emailController.text,
      "password" : passwordController.text
    }).then((cevap)
    {
      var jsonData = json.decode(cevap.body);
      print(jsonData);
    });*/

    /*http.get("http://muhtedibulut.com/SR/index.php?user_id=21"
        "&name=${nameController.text}"
        "&surname=${surnameController}"
        "&").then((cevap)
    {

    });*/
  }

  @override
  void initState() {
    super.initState();
    getProfile();
    /*Future.delayed(const Duration(milliseconds: 500), () {
      getProfile();
    });*/
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: new AppBar(
        title: new Text('Profile'),
        backgroundColor: Colors.black,
      ),
      body: Padding(
        padding: EdgeInsets.all(1.0),
        child: Container(
          decoration: BoxDecoration(
              // en arka plana renk verme
              gradient: LinearGradient(
            colors: <Color>[Colors.black87, Colors.black87],
          )),
          child: ListView(
            children: <Widget>[
              Container(
                decoration: BoxDecoration(
                  // içerdeki kutuya köşe yuvarlama
                  color: Colors.black38,
                  borderRadius: BorderRadius.circular(20.0),
                ),
                padding: EdgeInsets.all(10.0), // iç kutu boyutları
                margin: EdgeInsets.all(40.0), // iç kutu boyutları
                child: Column(
                  children: <Widget>[
                    Container(
                      // logo altındaki yazı
                      child: Text(
                        "BİLGİLERİM",
                        style: TextStyle(
                          fontSize: 35.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                    ),
                    text1(
                      textBox: "AD",
                      controller: nameController,
                      icon: Icon(Icons.person),
                      textType: TextInputType.text,
                      obscure: false,
                      labelText: "AD",
                    ),
                    text1(
                      textBox: "SOYAD",
                      controller: surnameController,
                      icon: Icon(Icons.person),
                      textType: TextInputType.text,
                      obscure: false,
                      labelText: "SOYAD",
                    ),
                    text1(
                      textBox: "TC",
                      controller: tcController,
                      icon: Icon(Icons.person),
                      textType: TextInputType.number,
                      obscure: false,
                      labelText: "TC",
                    ),
                    text1(
                      textBox: "TELEFON",
                      controller: phoneController,
                      icon: Icon(Icons.phone),
                      textType: TextInputType.phone,
                      obscure: false,
                      labelText: "TELEFON",
                    ),
                    text1(
                      textBox: "E-MAİL",
                      controller: emailController,
                      icon: Icon(Icons.email),
                      textType: TextInputType.emailAddress,
                      obscure: false,
                      labelText: "E-MAİL",
                    ),
                    text1(
                      textBox: "PAROLA",
                      controller: passwordController,
                      icon: Icon(Icons.lock),
                      textType: TextInputType.text,
                      obscure: true,
                      labelText: "PAROLA",
                    ),
                    Row(
                      // boşluk bırakma
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.all(10.0),
                        )
                      ],
                    ),
                    ButtonTheme(
                      // GRADİANT RENKLİ KAYIT OL / GİRİŞ YAP BUTONU
                      minWidth: 300.0,
                      height: 50.0,
                      buttonColor: Colors.transparent,
                      child: Container(
                        decoration: const BoxDecoration(
                          gradient: LinearGradient(
                            colors: <Color>[Colors.blue, Colors.pink],
                          ),
                          borderRadius: BorderRadius.all(Radius.circular(30.0)),
                        ),
                        child: RaisedButton(
                          onPressed: () {
                            updateProfile();
                          },
                          child: Text(
                            "GÜNCELLE",
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          textColor: Colors.white,
                          shape: new RoundedRectangleBorder(
                              borderRadius: new BorderRadius.circular(30.0)),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
    /*Scaffold(
      appBar: new AppBar(
        title: new Text('Profile'),
      ),
      body: Padding(
          padding: const EdgeInsets.all(1.0),
        child: Form(
            child: ListView(
              children: <Widget>[
                TextFormField(
                    keyboardType: TextInputType.text,
                    // Use email input type for emails.
                    controller: nameController,
                    decoration: new InputDecoration(
                        icon: const Icon(Icons.person),
                        labelText: 'İsim',
                        hintText: "Mühtedi")),
                TextFormField(
                    keyboardType: TextInputType.text,
                    // Use email input type for emails.
                    controller: surnameController,
                    decoration: new InputDecoration(
                        icon: const Icon(Icons.person),
                        labelText: 'Soyisim',
                        hintText: "BULUT")),
                TextFormField(
                    keyboardType: TextInputType.number,
                    // Use email input type for emails.
                    controller: tcController,
                    decoration: new InputDecoration(
                        icon: const Icon(Icons.person),
                        labelText: 'TC',
                        hintText: "12345678900")),
                TextFormField(
                    keyboardType: TextInputType.phone,
                    // Use email input type for emails.
                    controller: phoneController,
                    decoration: new InputDecoration(
                        icon: const Icon(Icons.call),
                        labelText: 'Telefon',
                        hintText: "05070351383")),
                TextFormField(
                    keyboardType: TextInputType.emailAddress,
                    // Use email input type for emails.
                    controller: emailController,
                    decoration: new InputDecoration(
                        icon: const Icon(Icons.email),
                        labelText: 'E-mail',
                        hintText: "örnek@gmail.com")),
                TextFormField(
                    keyboardType: TextInputType.emailAddress,
                    // Use email input type for emails.
                    controller: passwordController,
                    obscureText: true,
                    decoration: new InputDecoration(
                        icon: const Icon(Icons.lock),
                        labelText: 'Parola',
                        hintText: "xxxx")),
                Padding(
                  padding: const EdgeInsets.all(20.0),
                ),
                RaisedButton(
                  child: new Text(
                    "ÇAĞIR",
                    style: new TextStyle(color: Colors.white),
                  ),
                  color: Colors.blue,
                  onPressed: () {
                    getProfile();
                  },
                ),
                Padding(
                  padding: const EdgeInsets.all(20.0),
                ),
                RaisedButton(
                  child: new Text(
                    "GÜNCELLE",
                    style: new TextStyle(color: Colors.white),
                  ),
                  color: Colors.blue,
                  onPressed: () {
                    updateProfile();
                  },
                ),
              ],
            )
        ),
      ),
    );*/
  }
}
