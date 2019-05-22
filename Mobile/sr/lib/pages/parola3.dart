import 'package:flutter/material.dart';
import 'package:sr/util/text.dart';


class parola3 extends StatefulWidget {
  @override
  _parola3State createState() => _parola3State();
}

class _parola3State extends State<parola3> {
  TextEditingController passwordController = new TextEditingController();
  TextEditingController passwordAgainController = new TextEditingController();

  String password, passwordAgain;


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

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: _willPopCallback,
      child: Scaffold(
        appBar: AppBar(
          title: Text("Yeni Parola"),
        ),
        body: Container(
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
                  color: Colors.black,
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
                        "Yeni Parola",
                        style: TextStyle(
                          fontSize: 25.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
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
                    text2(
                      textBox: "Parola",
                      controller: passwordController,
                      icon: Icon(Icons.lock),
                      textType: TextInputType.text,
                      obscure: true,
                    ),
                    text2(
                      textBox: "Parola(Tekrar)",
                      controller: passwordAgainController,
                      icon: Icon(Icons.lock),
                      textType: TextInputType.text,
                      obscure: true,
                    ),
                    Row( // boşluk bırakma
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.all(5.0),
                        )
                      ],
                    ),
                    Row( // boşluk bırakma
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.all(10.0),
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
                            colors: <Color>[Colors.blue, Colors.pink],
                          ),
                          borderRadius: BorderRadius.all(Radius.circular(30.0)),
                        ),
                        child: RaisedButton(
                          onPressed: () {
                            // butona tıklama
                            password = passwordController.text;
                            passwordAgain = passwordAgainController.text;
                          },
                          child: Text(
                            // butonun text kısmı
                            "YENİ PAROLA",
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
  }
}
