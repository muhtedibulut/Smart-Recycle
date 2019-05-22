import 'package:flutter/material.dart';
import 'package:sr/pages/parola2.dart';
import 'package:sr/util/text.dart';

class parola1 extends StatefulWidget {
  @override
  _parola1State createState() => _parola1State();
}

class _parola1State extends State<parola1> {

  TextEditingController Controller = new TextEditingController();

  String controller;

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
          title: Text("Parolamı Unuttum"),
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
                    Container(
                      // logo altındaki yazı
                      child: Text(
                        "Parolamı Unuttum",
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
                      textBox: "E-Mail",
                      controller: Controller,
                      icon: Icon(Icons.email),
                      textType: TextInputType.emailAddress,
                      obscure: false,
                    ),
                    Row( // boşluk bırakma
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.all(15.0),
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
                            controller = Controller.text;
                            Navigator.pushReplacement(
                              context,
                              new MaterialPageRoute(builder: (ctxt) => new parola2()),
                            );
                          },
                          child: Text(
                            // butonun text kısmı
                            "E-Mail Gönder",
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
