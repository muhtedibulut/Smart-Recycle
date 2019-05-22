import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:sr/pages/homePage.dart';
import 'package:sr/pages/parola1.dart';
import 'package:sr/pages/time.dart';
import 'package:sr/pages/ptime.dart';
import 'package:sr/pages/time2.dart';

//clippath
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

  //Telefondaki geri tuşuna bastığında
  Future<bool> _willPopCallback() async {
    return showDialog(
        context: context,
        builder: (context) => AlertDialog(
              title: Text("UYGULAMADAN ÇIKMAK İSTİYOR MUSUNUZ?"),
              actions: <Widget>[
                FlatButton(
                  child: Text("HAYIR"),
                  onPressed: () =>
                      Navigator.pop(context, false), //Uuygulamada Kal
                ),
                FlatButton(
                  child: Text("EVET"),
                  onPressed: () =>
                      Navigator.pop(context, true), //Uygulamadan Çık
                )
              ],
            ));
  }

  loginService(BuildContext ctxt) {
    http.get("http://muhtedibulut.com/SR/login.php?tc=" + tc).then((cevap) {
      print(cevap.statusCode);
      print(cevap.body.length);
      var jsonData = json.decode(cevap.body);
      print(jsonData);
      print(jsonData["hata"]);
      bool error = jsonData["hata"];
      if (!error) //Hata yok ise
      {
        print(jsonData["data"]["password"]);
        String a = jsonData["data"]["password"];
        if (a == password) {
          print("Home page");
          //_showDialog("BİLGİLER DOĞRU");
          Navigator.pushReplacement(
            ctxt,
            new MaterialPageRoute(builder: (ctxt) => new homePage()),
          );
        } else {
          _showDialog("KULLANICI ADINIZI VEYA PAROLANIZI KONTROL EDİNİZ");
        }
      } else {
        _showDialog("ERROR\nKULLANICI ADINIZI VEYA PAROLANIZI KONTROL EDİNİZ");
      }
      /*setState(()
      {

      });*/
    });
    print("Buton Basildi");
  }

  registerService() {
    var url = "http://muhtedibulut.com/SR/index.php";
    var body = {
      "name": nameController.text,
      "surname": surnameController.text,
      "tc": tcController.text,
      "phone": phoneController.text,
      "email": emailController.text,
      "password": passwordController.text
    };

    Map<String, String> headers = {
      'Content-type': 'application/json',
      'Accept': 'application/json',
    };
    http.post(url, body: body).then((cevap) {
      //veri = cevap.body;
      //print(veri.toString());

      var jsonData = json.decode(cevap.body);
      print(jsonData["hata"]);
      print(body);
      print(jsonData["mesaj"]);
      bool error = jsonData["hata"];
      if (error) {
        _showDialog("İŞLEM GERÇEKLEŞTİRİLEMEDİ");
      } else {
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
    return WillPopScope(
      //Baqck Tuşu
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
      backgroundColor: Colors.black,
      title: TabBar(
        tabs: [
          new Tab(
            child: Text("Giriş"),
          ),
          new Tab(
            child: Text("Kayıt Ol"),
          ),
          //new Tab(icon: new Icon(Icons.account_circle)),
          //new Tab(icon: new Icon(Icons.person_add)),
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
              color: Colors.black38,
              borderRadius: BorderRadius.circular(20.0),
            ),
            padding: EdgeInsets.all(10.0),
            // boyutlandırma
            margin: EdgeInsets.all(40.0),
            // boyutlandırma
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
                    "Giriş",
                    style: TextStyle(
                      fontSize: 35.0,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
                /*TextField(
                  keyboardType: TextInputType.number,
                  controller: tcController,
                  decoration: InputDecoration(
                    //prefixIcon: Icon(Icons.person),
                    //suffixIcon: Icon(Icons.lock), //Iconu sağ tarafa atar
                    icon: const Icon(Icons.person),
                    labelText: "TC",
                    hintText: "12345678900",
                    //contentPadding: EdgeInsets.fromLTRB(20.0, 10.0, 20.0, 10.0),
                    //border: OutlineInputBorder(borderRadius: BorderRadius.circular(32.0)),
                    hintStyle: TextStyle(
                      fontSize: 13.0,
                      height: 1.5,
                    ),
                  ),
                ),*/

                text(
                  textBox: "TC",
                  controller: tcController,
                  icon: Icon(Icons.person),
                  textType: TextInputType.number,
                  obscure: false,
                ),
                text(
                  textBox: "Parola",
                  controller: passwordController,
                  icon: Icon(Icons.lock),
                  //Icons.lock,
                  textType: TextInputType.text,
                  obscure: true,
                ),
                Row(
                  // boşluk bırakma
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
                      Navigator.push(
                        context,
                        new MaterialPageRoute(builder: (ctxt) => new parola1()),
                      );
                    },
                    child: Text(
                      "Parolamı Unuttum",
                      style: TextStyle(fontSize: 13.0, color: Colors.white),
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
                /*Container(
                  //  ŞİFREMİ UNUTTUM flat button
                  alignment: Alignment.centerRight,
                  child: FlatButton(
                    onPressed: () {
                      //sifremiunuttum();
                      Navigator.push(
                        context,
                        new MaterialPageRoute(builder: (ctxt) => new time()),
                      );
                    },
                    child: Text(
                      "Parolamı Unuttum1",
                      style: TextStyle(fontSize: 13.0, color: Colors.white),
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
                ),*/
                ButtonTheme(
                  // GİRİŞ YAP BUTONU GRADİANT RENKLİ
                  minWidth: 200.0,
                  height: 50.0,
                  buttonColor: Colors.transparent,
                  child: Container(
                    decoration: const BoxDecoration(
                      // butonun rengi
                      gradient: LinearGradient(
                        //colors: <Color>[Colors.black87, Colors.black87],
                        colors: <Color>[Colors.blue, Colors.pink],
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
                color: Colors.black38,
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
                        color: Colors.white,
                      ),
                    ),
                  ),
                  text(
                    textBox: "Ad",
                    controller: nameController,
                    icon: Icon(Icons.person),
                    textType: TextInputType.text,
                    obscure: false,
                  ),
                  text(
                    textBox: "Soyad",
                    controller: surnameController,
                    icon: Icon(Icons.person),
                    textType: TextInputType.text,
                    obscure: false,
                  ),
                  text(
                    textBox: "TC",
                    controller: tcController,
                    icon: Icon(Icons.person),
                    textType: TextInputType.number,
                    obscure: false,
                  ),
                  text(
                    textBox: "Telefon",
                    controller: phoneController,
                    icon: Icon(Icons.call),
                    textType: TextInputType.phone,
                    obscure: false,
                  ),
                  text(
                    textBox: "E-mail",
                    controller: emailController,
                    icon: Icon(Icons.email),
                    textType: TextInputType.emailAddress,
                    obscure: false,
                  ),
                  text(
                    textBox: "Parola",
                    controller: passwordController,
                    icon: Icon(Icons.lock),
                    textType: TextInputType.text,
                    obscure: true,
                  ),
                  text(
                    textBox: "Parola (Tekrar)",
                    controller: passwordAgainController,
                    icon: Icon(Icons.lock),
                    textType: TextInputType.text,
                    obscure: true,
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
                          colors: <Color>[Colors.blue, Colors.pink],
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

class text extends StatelessWidget {
  final textBox;
  final controller;
  final icon;
  final textType;
  final obscure;

  const text(
      {this.textBox, this.controller, this.icon, this.textType, this.obscure});

  @override
  Widget build(BuildContext context) {
    return Theme(
      data: Theme.of(context).copyWith(splashColor: Colors.transparent),
      child: Column(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.all(8.0),
          ),
          TextField(
            autofocus: false,
            keyboardType: textType,
            controller: controller,
            obscureText: obscure,
            decoration: InputDecoration(
              filled: true,
              fillColor: Colors.white,
              prefixIcon: icon,
              //icon: const Icon(Icons.lock),
              hintText: textBox,
              hintStyle: TextStyle(
                fontSize: 10.0,
                height: 1.5,
              ),
              focusedBorder: OutlineInputBorder(
                borderSide: BorderSide(color: Colors.white),
                borderRadius: BorderRadius.circular(25.7),
              ),
              enabledBorder: UnderlineInputBorder(
                borderSide: BorderSide(color: Colors.white),
                borderRadius: BorderRadius.circular(25.7),
              ),
            ),
          )
        ],
      ),
    );
  }
}
