import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:sr/pages/homePage.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: loginPage(),
    );
  }
}

class loginPage extends StatefulWidget {
  @override
  _loginPageState createState() => _loginPageState();
}

class _loginPageState extends State<loginPage> {

  TextEditingController nameController = new TextEditingController();
  TextEditingController surnameController = new TextEditingController();
  TextEditingController tcController = new TextEditingController();
  TextEditingController emailController = new TextEditingController();
  TextEditingController phoneController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();
  TextEditingController passwordAgainController = new TextEditingController();

  String name, surname, tc, phone, email, password, passwordAgain;

  void _showDialog(String dialog) {
    // flutter defined function
    showDialog(
      context: context,
      builder: (BuildContext context) {
        // return object of type Dialog
        return AlertDialog(
          title: new Text("KAYIT"),
          content: new Text(dialog),
          actions: <Widget>[
            // usually buttons at the bottom of the dialog
            new FlatButton(
              child: new Text("OK"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),

          ],
        );
      },
    );
  }

  //Telefondaki geri tuşuna bastığında
  Future<bool> _willPopCallback() async {
    return showDialog(
        context: context, builder: (context) =>AlertDialog(
      title: Text("UYGULAMADAN ÇIKMAK İSTİYOR MUSUNUZ?"),
      actions: <Widget>[
        FlatButton(
          child: Text("HAYIR"),
          onPressed: () => Navigator.pop(context, false),//Uuygulamada Kal
        ),
        FlatButton(
          child: Text("EVET"),
          onPressed: () => Navigator.pop(context, true),//Uygulamadan Çık
        )
      ],
    ));
  }

  loginService(BuildContext ctxt)
  {
    http.get("http://muhtedibulut.com/SR/login.php?tc=" + tc).then((cevap)
    {
      print(cevap.statusCode);
      print(cevap.body.length);
      var jsonData = json.decode(cevap.body);
      print(jsonData);
      print(jsonData["hata"]);
      bool error = jsonData["hata"];
      if(!error)//Hata yok ise
          {
        print(jsonData["data"]["password"]);
        String a = jsonData["data"]["password"];
        if (a == password)
        {
          print("Home page");
          //_showDialog("BİLGİLER DOĞRU");
          Navigator.pushReplacement(
            ctxt,
            new MaterialPageRoute(builder: (ctxt) => new homePage()),
          );
        }
        else
        {
          _showDialog("KULLANICI ADINIZI VEYA PAROLANIZI KONTROL EDİNİZ");
        }
      }
      else
      {
        _showDialog("ERROR\nKULLANICI ADINIZI VEYA PAROLANIZI KONTROL EDİNİZ");
      }
      /*setState(()
      {

      });*/
    });
    print("Buton Basildi");
  }

  registerService()
  {
    var url = "http://muhtedibulut.com/SR/index.php";
    var body = {"name" : nameController.text,
      "surname" : surnameController.text,
      "tc" : tcController.text,
      "phone" : phoneController.text,
      "email" : emailController.text,
      "password" : passwordController.text};

    Map<String,String> headers = {
      'Content-type' : 'application/json',
      'Accept': 'application/json',
    };
    http.post(url,body:body).then((cevap)
    {
      //veri = cevap.body;
      //print(veri.toString());

      var jsonData = json.decode(cevap.body);
      print(jsonData["hata"]);
      print(body);
      print(jsonData["mesaj"]);
      bool error = jsonData["hata"];
      if(error)
      {
        _showDialog("İŞLEM GERÇEKLEŞTİRİLEMEDİ");
      }
      else
      {
        _showDialog("İŞLEM BAŞARIYLA GERÇEKLEŞTİRİLDİ");
      }
    });
    print("Post Basildi");
  }

  //////////******************************************************************



  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope( //Baqck Tuşu
      onWillPop: _willPopCallback,
      child: Scaffold(
        body: _tabbarLS(context),
      ),
    );
    /*return Scaffold(
      body: _tabbarLS(),
    );*/
  }

  // ana fonksiyonda body kısmı tabbarview içerir
  Widget _tabbarLS(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: new Scaffold(
        appBar: yap_tabbar(),
        body: Container(
          child: Center(
            child: new TabBarView(
              children: <Widget>[
                login(context),
                register(context),
              ],
            ),
          ),
        ),
      ),
    );
  }

  // body -> tabbarSL -> appbar kısmında tab seçenekleri
  Widget yap_tabbar() {
    return AppBar(
      backgroundColor: Colors.blueGrey,
      title: TabBar(
        tabs: [
          new Tab(icon: new Icon(Icons.account_circle)),
          new Tab(icon: new Icon(Icons.person_add)),
        ],
        indicatorColor: Colors.white,
      ),
    );
  }

  // login ekranında body de gösterilen cardpage kısmı.
  Widget login(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        // en arkadaki renk
        gradient: LinearGradient(
          colors: <Color>[Colors.black87, Colors.black87],
        ),
      ),
      child: ListView(
        children: <Widget>[
          Container(
            //height: 700.0,
            decoration: BoxDecoration(
              // beyaz card köşe yuvarlama
              color: Colors.white,
              borderRadius: BorderRadius.circular(20.0),
            ),
            padding: EdgeInsets.all(10.0), // boyutlandırma
            margin: EdgeInsets.all(40.0), // boyutlandırma
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                /*Container(
                  // logo
                  /*child: Image.network(
                "https://trello-attachments.s3.amazonaws.com/5c7a3f78cf9cfb08c3bbfb9f/5c923907469ef32d0af7f523/18c2acaf1cb471346958035bae62fccb/icon.png",
                width: 150.0,
              ),*/
                  child: IconButton(
                    // şimdilik logo yerine icon
                    icon: Icon(Icons.accessibility),
                    iconSize: 80.0,
                  ),
                ),*/
                Container(
                  // logo altındaki yazı
                  child: Text(
                    "Giriş Yap",
                    style: TextStyle(
                      fontSize: 35.0,
                      fontWeight: FontWeight.bold,
                      color: Colors.black54,
                    ),
                  ),
                ),
                Row( // boşluk bırakma
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.all(0.0),
                    )
                  ],
                ),
                TextField(
                  keyboardType: TextInputType.number,
                  controller: tcController,
                  decoration: InputDecoration(
                    //prefixIcon: Icon(Icons.person),
                    icon: const Icon(Icons.person),
                    labelText: "TC",
                    hintText: "12345678900",
                    hintStyle: TextStyle(
                      fontSize: 13.0,
                      height: 1.5,
                    ),
                  ),
                ),
                Row( // boşluk bırakma
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.all(5.0),
                    )
                  ],
                ),
                TextField(
                  keyboardType: TextInputType.text,
                  controller: passwordController,
                  obscureText: true,
                  decoration: InputDecoration(
                    //prefixIcon: Icon(Icons.lock),
                    icon: const Icon(Icons.lock),
                    labelText: "Parola",
                    hintText: "xxxxxx",
                    hintStyle: TextStyle(
                      fontSize: 13.0,
                      height: 1.5,
                    ),
                  ),
                ),
                Row( // boşluk bırakma
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.all(5.0),
                    )
                  ],
                ),
                Container(
                  //  ŞİFREMİ UNUTTUM flat button
                  alignment: Alignment.centerRight,
                  child: FlatButton(
                    onPressed: () {
                      //sifremiunuttum();
                    },
                    child: Text(
                      "Şifremi Unuttum",
                      style: TextStyle(fontSize: 13.0),
                    ),
                  ),
                ),
                Row(
                  // boşluk bırakma
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.all(5.0),
                    )
                  ],
                ),
                ButtonTheme(
                  // GİRİŞ YAP BUTONU GRADİANT RENKLİ
                  minWidth: 200.0,
                  height: 50.0,
                  buttonColor: Colors.transparent,
                  child: Container(
                    decoration: const BoxDecoration(
                      // butonun rengi
                      gradient: LinearGradient(
                        colors: <Color>[Colors.black87, Colors.black87],
                      ),
                      borderRadius: BorderRadius.all(Radius.circular(30.0)),
                    ),
                    child: RaisedButton(
                      onPressed: () {
                        // butona tıklama
                        tc = tcController.text;
                        password = passwordController.text;
                        loginService(context);
                      },
                      child: Text(
                        // butonun text kısmı
                        "GİRİŞ YAP",
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
    );
  }

  // login ekranında sağ ekranda kayıt ol kısmı burası.
  Widget register(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        // en arka plana renk verme
          gradient: LinearGradient(
            colors: <Color>[Colors.black87, Colors.black87],
          )),
      child: ListView(
        children: <Widget>[
          Container(
            child: Container(
              decoration: BoxDecoration(
                // içerdeki kutuya köşe yuvarlama
                color: Colors.white,
                borderRadius: BorderRadius.circular(20.0),
              ),
              padding: EdgeInsets.all(10.0), // iç kutu boyutları
              margin: EdgeInsets.all(40.0), // iç kutu boyutları
              child: Column(
                // satır satır aşağı kodlama
                children: <Widget>[
                  /*Container(
                    // logo yükleme
                    child: IconButton(
                      icon: Icon(Icons.person_add),
                      iconSize: 80.0,
                    ),
                  ),*/
                  Container(
                    // logo altındaki yazı
                    child: Text(
                      "Kayıt Ol",
                      style: TextStyle(
                        fontSize: 35.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.black54,
                      ),
                    ),
                  ),
                  Row(
                    // logodan sonra aşağı boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(0.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.text,
                    controller: nameController,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.person),
                      icon: const Icon(Icons.person),
                      hintText: "Ad",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
                  ),
                  Row(
                    // boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(5.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.text,
                    controller: surnameController,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.person),
                      icon: const Icon(Icons.person),
                      hintText: "Soyad",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
                  ),
                  Row(
                    // boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(5.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.number,
                    controller: tcController,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.person),
                      icon: const Icon(Icons.person),
                      hintText: "TC",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
                  ),
                  Row(
                    // boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(5.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.phone,
                    controller: phoneController,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.mail_outline),
                      icon: const Icon(Icons.call),
                      hintText: "Telefon",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
                  ),
                  Row(
                    // boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(5.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.emailAddress,
                    controller: emailController,
                    obscureText: true,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.lock_outline),
                      icon: const Icon(Icons.email),
                      hintText: "E-mail",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
                  ),
                  Row(
                    // boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(10.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.text,
                    controller: passwordController,
                    obscureText: true,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.lock_outline),
                      icon: const Icon(Icons.lock),
                      hintText: "Parola",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
                  ),
                  Row(
                    // boşluk bırakma
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.all(10.0),
                      )
                    ],
                  ),
                  TextField(
                    keyboardType: TextInputType.text,
                    controller: passwordAgainController,
                    obscureText: true,
                    decoration: InputDecoration(
                      //prefixIcon: Icon(Icons.lock_outline),
                      icon: const Icon(Icons.lock),
                      hintText: "Parola (Tekrar)",
                      hintStyle: TextStyle(
                        fontSize: 13.0,
                        height: 1.5,
                      ),
                    ),
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
                    minWidth: 200.0,
                    height: 50.0,
                    buttonColor: Colors.transparent,
                    child: Container(
                      decoration: const BoxDecoration(
                        gradient: LinearGradient(
                          colors: <Color>[Colors.black87, Colors.black87],
                        ),
                        borderRadius: BorderRadius.all(Radius.circular(30.0)),
                      ),
                      child: RaisedButton(
                        onPressed: () {
                          registerService();
                        },
                        child: Text(
                          "KAYIT OL",
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
          )
        ],
      ),
    );
  }


}

