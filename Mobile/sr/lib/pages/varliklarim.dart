import 'package:flutter/material.dart';
import 'package:pie_chart/pie_chart.dart';
import 'package:sr/pages/bank.dart';


var orange = Color(0xFFfc9f6a);
var pink = Color(0xFFee528c);
var blue = Color(0xFF8bccd6);
var darkBlue = Color(0xFF60a0d7);
var valueBlue = Color(0xFF5fa0d6);


class varliklarim extends StatefulWidget {
  @override
  _varliklarimState createState() => _varliklarimState();
}

class _varliklarimState extends State<varliklarim> {
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
  Widget build(BuildContext context) {
    Map<String, double> dataMap = new Map();
    dataMap.putIfAbsent("Plastik", () => 5);
    dataMap.putIfAbsent("Cam", () => 5);
    dataMap.putIfAbsent("Metal", () => 5);


    return WillPopScope(
      //back tuşu
      onWillPop: _willPopCallback,
      child: Container(
          alignment: Alignment.topCenter, //Container merkeze al
          margin: EdgeInsets.all(0.0),
          color: Colors.black87,
          child: ListView(
            children: <Widget>[
              Card(
                color: Colors.black54,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(25.0),
                ),
                child: PieChart(
                  dataMap: dataMap,
                  legendFontColor: Colors.white,
                  legendFontSize: 14.0,
                  legendFontWeight: FontWeight.w500,
                  animationDuration: Duration(milliseconds: 1800),
                  chartLegendSpacing: 32.0,
                  chartRadius: MediaQuery.of(context).size.width / 2.7,
                  showChartValuesInPercentage: true,
                  showChartValues: true,
                  chartValuesColor: Colors.blueGrey[900].withOpacity(0.9),
                ),
              ),
              Padding(padding: EdgeInsets.all(5.0),),
              ItemCard('Toplam', '', '\$ 3284.00', [orange, pink],Colors.white),
              SizedBox(
                height: 8.0,
              ),
              ItemCard('Kullanılabilir', '', '\$ 1284.00', [blue, darkBlue],Colors.white),
              SizedBox(
                height: 32.0,
              ),
              ItemCard('Plastik', '', '\$ 1200.00', [Colors.black, Colors.black],Colors.green),
              SizedBox(
                height: 8.0,
              ),
              ItemCard('Metal', '', '\$ 1080.00', [Colors.black, Colors.black],Colors.green),
              SizedBox(
                height: 8.0,
              ),
              ItemCard('Cam', '', '\$ 1004.00', [Colors.black, Colors.black],Colors.green),
              SizedBox(
                height: 8.0,
              ),
              /*Card(
                color: Colors.black54,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(25.0),
                ),
                child: Container(
                  alignment: Alignment.bottomLeft,
                  padding: const EdgeInsets.all(10.0),
                  child: Column(
                    children: <Widget>[
                      Text(
                        "Toplam",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontSize: 25.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(5.0),
                      ),
                      Text(
                        "333.33 TL",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontSize: 15.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                ),
              ),

              Card(
                color: Colors.black54,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(25.0),
                ),
                child: Container(
                  alignment: Alignment.bottomLeft,
                  padding: const EdgeInsets.all(10.0),
                  child: Column(
                    children: <Widget>[
                      Text(
                        "Kullanılabilir",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontSize: 25.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(5.0),
                      ),
                      Text(
                        "222.22 TL",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontSize: 15.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.all(25.0),
              ),
              Card(
                color: Colors.black54,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(25.0),
                ),
                child: Container(
                  alignment: Alignment.bottomLeft,
                  padding: const EdgeInsets.all(10.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: <Widget>[
                      Text(
                        "PLASTİK",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      Text(
                        "2.18 TL",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.green,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Card(
                color: Colors.black54,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(25.0),
                ),
                child: Container(
                  alignment: Alignment.bottomLeft,
                  padding: const EdgeInsets.all(10.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: <Widget>[
                      Text(
                        "METAL",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      Text(
                        "1.15 TL",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.green,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Card(
                color: Colors.black54,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(25.0),
                ),
                child: Container(
                  alignment: Alignment.bottomLeft,
                  padding: const EdgeInsets.all(10.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: <Widget>[
                      Text(
                        "CAM",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                        ),
                      ),
                      Text(
                        "3.17 TL",
                        textScaleFactor: 1.2,
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.green,
                        ),
                      ),
                    ],
                  ),
                ),
              ),*/








              /*Container(
              decoration: BoxDecoration(
                // beyaz card köşe yuvarlama
                color: Colors.black54,
                borderRadius: BorderRadius.circular(20.0),
              ),
              padding: EdgeInsets.all(10.0), // boyutlandırma
              child: Text(
                "Toplam\n333.33",
                style: TextStyle(
                  fontSize: 35.0,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
            ),*/
              /*Container(
              decoration: BoxDecoration(
                // beyaz card köşe yuvarlama
                color: Colors.white,
                borderRadius: BorderRadius.circular(20.0),
              ),
              padding: EdgeInsets.all(10.0), // boyutlandırma
              child: Text(
                "333.33",
                style: TextStyle(
                  fontSize: 35.0,
                  fontWeight: FontWeight.bold,
                  color: Colors.black54,
                ),
              ),
            ),*/
            ],
          )),
      /*child: Card(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            const ListTile(
              leading: Icon(Icons.album),
              title: Text('The Enchanted Nightingale'),
              subtitle: Text('Music by Julie Gable. Lyrics by Sidney Stein.'),
            ),
            ButtonTheme.bar( // make buttons use the appropriate styles for cards
              child: ButtonBar(
                children: <Widget>[
                  FlatButton(
                    child: const Text('BUY TICKETS'),
                    onPressed: () { /* ... */ },
                  ),
                  FlatButton(
                    child: const Text('LISTEN'),
                    onPressed: () { /* ... */ },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),*/
      /*child: Scaffold(
        body: Padding(
            padding: const EdgeInsets.all(5.0),
          child: ListView(
            children: <Widget>[
              Text("asfdf"),
            ],
          ),
        ),
      ),*/
    );
  }
}

/*class ItemCard extends StatelessWidget {
  final titel;
  final subtitle;
  final value;
  final colors;
  ItemCard(this.titel, this.subtitle, this.value, this.colors);
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 10.0),
      child: Container(
        padding: EdgeInsets.all(16.0),
        width: double.infinity,
        decoration: BoxDecoration(
            gradient: LinearGradient(
                begin: Alignment.topLeft,
                end: Alignment.topRight,
                colors: colors),
            borderRadius: BorderRadius.circular(4.0)),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text(
                  titel,
                  style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 20.0),
                ),
                Text(
                  subtitle,
                  style: TextStyle(color: Colors.white),
                )
              ],
            ),
            Text(
              value,
              style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 20.0),
            )
          ],
        ),
      ),
    );
  }
}*/