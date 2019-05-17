import 'package:flutter/material.dart';
import 'package:sr/main.dart';
import 'package:sr/pages/varliklarim.dart';
import 'package:sr/pages/kullanimlarim.dart';
import 'package:sr/pages/sonIslemler.dart';
import 'package:sr/pages/bank.dart';
import 'package:sr/pages/profile.dart';
import 'package:sr/pages/QR.dart';

class appBar extends AppBar {

  appBar({Key key, Widget title,BuildContext ctxt})
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
                          new MaterialPageRoute(builder: (ctxt) => new profile()),
                        );
                      },
                    ),
                    IconButton(
                      icon: Icon(Icons.power_settings_new),
                      onPressed: () {

                      },
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

  List<Widget> pages;
  Widget currentPage;

  final PageStorageBucket bucket = PageStorageBucket();

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
                  /*onPressed: ()
                  {
                    Navigator.pushReplacement(
                      ctxt,
                      new MaterialPageRoute(builder: (ctxt) => new MyApp()),
                    );
                  },*/
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
      onWillPop: _willPopCallback,
      child: Scaffold(
        appBar: appBar(
          ctxt: context,
            title: Text("SR"),
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
              currentTab = index;
              currentPage = pages[index];
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

