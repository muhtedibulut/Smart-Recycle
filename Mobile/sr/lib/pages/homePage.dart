import 'package:flutter/material.dart';
import 'package:sr/main.dart';
import 'package:sr/pages/varliklarim.dart';
import 'package:sr/pages/kullanimlarim.dart';
import 'package:sr/pages/sonIslemler.dart';
import 'package:sr/pages/bank.dart';
import 'package:sr/pages/profile.dart';
import 'package:sr/pages/QR.dart';
import 'package:sr/main.dart';

import 'package:barcode_scan/barcode_scan.dart';
import 'dart:async';
import 'package:flutter/services.dart';

class appBar extends AppBar {
  appBar({Key key, Widget title, BuildContext ctxt, String name})
      : super(
            key: key,
            title: title,
            backgroundColor: Colors.blueGrey,
            actions: <Widget>[
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    /*IconButton(
                      icon: Icon(Icons.settings),
                      onPressed: () {},
                    ),
                    IconButton(
                      icon: Icon(Icons.notifications),
                      onPressed: () {},
                    ),
                    IconButton(
                      icon: Icon(Icons.notifications_none),
                      onPressed: () {},
                    ),*/
                    IconButton(
                      icon: Icon(Icons.person),
                      onPressed: () {
                        Navigator.push(
                          ctxt,
                          new MaterialPageRoute(
                              builder: (ctxt) => new profile()),
                        );
                      },
                    ),
                    Text(name),
                    IconButton(
                      icon: Icon(Icons.power_settings_new),
                      //onPressed: () { },
                      onPressed: () =>
                          //Navigator.pop(ctxt, true), //Uygulamadan Çık
                          Navigator.pushReplacement(
                            ctxt,
                            new MaterialPageRoute(
                                builder: (ctxt) => new MyApp()),
                          ),
                    ),
                  ],
                ),
              ),
            ]);
}

class homePage extends StatefulWidget {
  @override
  _homePageState createState() => _homePageState();
}

class _homePageState extends State<homePage> {
  int currentTab = 0;

  PageOne one;
  PageTwo two;
  PageThree three;
  PageFour four;
  varliklarim vrlk;
  kullanimlarim klnm;
  sonIslemler islm;
  MyHomePage bank;
  ScanScreen qr;
  homePage h;

  List<Widget> pages;
  Widget currentPage;

  final PageStorageBucket bucket = PageStorageBucket();

  //Telefondaki geri tuşuna bastığında
  Future<bool> _willPopCallback(BuildContext context) async {
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
                  //onPressed: () =>
                      //Navigator.pop(context, true), //Uygulamadan Çık
                  onPressed: ()
                  {
                    Navigator.pushReplacement(
                      context,
                      new MaterialPageRoute(builder: (ctxt) => new MyApp()),
                    );
                  },
                )
              ],
            ));
  }

  @override
  void initState() {
    one = PageOne();
    two = PageTwo();
    three = PageThree();
    four = PageFour();
    vrlk = varliklarim();
    klnm = kullanimlarim();
    islm = sonIslemler();
    bank = MyHomePage();
    qr = ScanScreen();

    pages = [vrlk, qr, klnm, islm];

    currentPage = vrlk;

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      //Back Tuşu
      onWillPop: ()
        {
          _willPopCallback(context);
        },
      child: Scaffold(
        appBar: appBar(
          ctxt: context,
          title: Text("SR"),
          name: "SMART RECYCLE",
        ),
        body: PageStorage(
          child: currentPage,
          bucket: bucket,
        ),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: currentTab,
          type: BottomNavigationBarType.fixed,
          fixedColor: Colors.pink,
          //Tüm butonların eşit olarak sabitlenmesini sağlar *EKSTRA BottomNavigationBarType.shifting
          onTap: (int index) {
            setState(() {
              if (index != 1) {
                currentTab = index;
                currentPage = pages[index];
              } else {
                scan();
              }
            });
          },
          items: <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              icon: Icon(Icons.assessment),
              title: Text('Varlıklarım'),
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.camera_alt),
              title: Text("QR"),
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.swap_horiz),
              title: Text("Kullanımlarım"),
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.art_track),
              title: Text("Son İşlemler"),
            ),
          ],
        ),
      ),
    );
  }

  void _showDialog(String dialog) {
    // flutter defined function
    showDialog(
      context: context,
      builder: (BuildContext context) {
        // return object of type Dialog
        return AlertDialog(
          title: new Text("QR"),
          content: new Text(dialog),
          actions: <Widget>[
            // usually buttons at the bottom of the dialog
            new FlatButton(
              child: new Text("TAMAM"),
              onPressed: () {
                Navigator.of(context).pop();
                /*Navigator.pushReplacement(
                  ctxt,
                  new MaterialPageRoute(builder: (ctxt) => new homePage()),
                );*/
              },
            ),
          ],
        );
      },
    );
  }

  Future scan() async {
    try {
      String barcode = await BarcodeScanner.scan();
      //setState(() => _showDialog(barcode));//this.barcode = barcode);
      setState(() => _showDialog("CİHAZ AKTİF HALE GETİRİLDİ"));
    } on PlatformException catch (e) {
      if (e.code == BarcodeScanner.CameraAccessDenied) {
        /*setState(() {
          this.barcode = 'The user did not grant the camera permission!';
        });*/
        setState(() => _showDialog("KAMERA İZNİ VERİLMEDİ")); //);
      } else {
        setState(() => _showDialog(
            "BEKLENMEYEN BİR HATA OLUŞTU")); //this.barcode = 'Unknown error: $e');
      }
    } on FormatException {
      //setState(() => this.barcode = 'null (User returned using the "back"-button before scanning anything. Result)');
      //setState(() => _showDialog("GERİ TUŞUNA BASILDI"));//this.barcode = '1');
    } catch (e) {
      setState(() => _showDialog(
          "BEKLENMEYEN BİR HATA OLUŞTU")); //this.barcode = 'Unknown error: $e');
    }
  }
}

class PageOne extends StatefulWidget {
  @override
  PageOneState createState() => PageOneState();
}

class PageOneState extends State<PageOne> {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center, //Container merkeze al
      margin: EdgeInsets.all(5.0),
      color: Colors.green,
      child: new Text(
        "Page One",
        textDirection: TextDirection.ltr,
      ),
    );
  }
}

class PageTwo extends StatefulWidget {
  @override
  PageTwoState createState() => PageTwoState();
}

class PageTwoState extends State<PageTwo> {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center, //Container merkeze al
      margin: EdgeInsets.all(5.0),
      color: Colors.purple,
      child: new Text(
        "Page Two",
        textDirection: TextDirection.ltr,
      ),
    );
  }
}

class PageThree extends StatefulWidget {
  @override
  _PageThreeState createState() => _PageThreeState();
}

class _PageThreeState extends State<PageThree> {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center, //Container merkeze al
      margin: EdgeInsets.all(5.0),
      color: Colors.yellow,
      child: new Text(
        "Page Three",
        textDirection: TextDirection.ltr,
      ),
    );
  }
}

class PageFour extends StatefulWidget {
  @override
  _PageFourState createState() => _PageFourState();
}

class _PageFourState extends State<PageFour> {
  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center, //Container merkeze al
      margin: EdgeInsets.all(5.0),
      color: Colors.deepOrange,
      child: new Text(
        "Page Four",
        textDirection: TextDirection.ltr,
      ),
    );
  }
}
