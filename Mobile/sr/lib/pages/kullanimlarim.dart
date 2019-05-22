import 'package:flutter/material.dart';
import 'package:sr/pages/bank.dart';


class kullanimlarim extends StatefulWidget {
  @override
  _kullanimlarimState createState() => _kullanimlarimState();
}

class _kullanimlarimState extends State<kullanimlarim> {

  void d()
  {
    print("basildi");
    _showDialog("basildi");
  }

  void _showDialog(String dialog) {
    // flutter defined function
    showDialog(
      context: context,
      builder: (BuildContext context) {
        // return object of type Dialog
        return AlertDialog(
          title: new Text("ÖDEME"),
          content: new Text(dialog + " FATURANIZ ÖDENMİŞTİR"),
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
    return WillPopScope(
      onWillPop: _willPopCallback,//back tuşu
      child: Container(
        alignment: Alignment.topCenter, //Container merkeze al
        margin: EdgeInsets.all(0.0),
        color: Colors.black87,
        child: ListView(
          children: <Widget>[
            /*Card(
              color: Colors.black54,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(25.0),
              ),
              child: Container(
                alignment: Alignment.center,
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
            ),*/
            SizedBox(
              height: 20.0,
            ),
            ItemCard('Kullanılabilir', '', '\$ 1284.00', [Colors.orange,Colors.pink],Colors.white),
            SizedBox(
              height: 20.0,
            ),
            Padding(
              padding: const EdgeInsets.all(25.0),
            ),
            Card(
              color: Colors.black54,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(25.0),
              ),
              child: Container(
                alignment: Alignment.center,
                padding: EdgeInsets.all(3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    RawMaterialButton(
                      onPressed: () {},
                      child: new Icon(
                        Icons.directions_bus,
                        color: Colors.black,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.white,
                      padding: const EdgeInsets.all(10.0),
                    ),
                    Text(
                      "Akbil",
                      textScaleFactor: 1.2,
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    RawMaterialButton(
                      onPressed: () {
                        _showDialog("AKBİL");
                      },
                      child: new Icon(
                        Icons.arrow_forward,
                        color: Colors.white,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.pink,
                      padding: const EdgeInsets.all(10.0),
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
                alignment: Alignment.center,
                padding: EdgeInsets.all(3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    RawMaterialButton(
                      onPressed: () {},
                      child: new Icon(
                        Icons.shopping_cart,
                        color: Colors.black,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.white,
                      padding: const EdgeInsets.all(10.0),
                    ),
                    Text(
                      "Market",
                      textScaleFactor: 1.2,
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    RawMaterialButton(
                      onPressed: () {
                        _showDialog("MARKET");
                      },
                      child: new Icon(
                        Icons.arrow_forward,
                        color: Colors.white,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.pink,
                      padding: const EdgeInsets.all(10.0),
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
                alignment: Alignment.center,
                padding: EdgeInsets.all(3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    RawMaterialButton(
                      onPressed: () {},
                      child: new Icon(
                        Icons.ac_unit,
                        color: Colors.black,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.white,
                      padding: const EdgeInsets.all(10.0),
                    ),
                    Text(
                      "Su",
                      textScaleFactor: 1.2,
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    RawMaterialButton(
                      onPressed: () {
                        _showDialog("SU");
                      },
                      child: new Icon(
                        Icons.arrow_forward,
                        color: Colors.white,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.pink,
                      padding: const EdgeInsets.all(10.0),
                    ),
                  ],
                ),
              ),
            ),Card(
              color: Colors.black54,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(25.0),
              ),
              child: Container(
                alignment: Alignment.center,
                padding: EdgeInsets.all(3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    RawMaterialButton(
                      onPressed: () {},
                      child: new Icon(
                        Icons.wb_incandescent,
                        color: Colors.black,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.white,
                      padding: const EdgeInsets.all(10.0),
                    ),
                    Text(
                      "Elektrik",
                      textScaleFactor: 1.2,
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    RawMaterialButton(
                      onPressed: () {
                        _showDialog("ELEKTRİK");
                      },
                      child: new Icon(
                        Icons.arrow_forward,
                        color: Colors.white,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.pink,
                      padding: const EdgeInsets.all(10.0),
                    ),
                  ],
                ),
              ),
            ),Card(
              color: Colors.black54,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(25.0),
              ),
              child: Container(
                alignment: Alignment.center,
                padding: EdgeInsets.all(3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    RawMaterialButton(
                      onPressed: () {},
                      child: new Icon(
                        Icons.redeem,
                        color: Colors.black,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.white,
                      padding: const EdgeInsets.all(10.0),
                    ),
                    Text(
                      "Doğalgaz",
                      textScaleFactor: 1.2,
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    RawMaterialButton(
                      onPressed: () {
                        _showDialog("DOĞALGAZ");
                      },
                      child: new Icon(
                        Icons.arrow_forward,
                        color: Colors.white,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.pink,
                      padding: const EdgeInsets.all(10.0),
                    ),
                  ],
                ),
              ),
            ),Card(
              color: Colors.black54,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(25.0),
              ),
              child: Container(
                alignment: Alignment.center,
                padding: EdgeInsets.all(3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    RawMaterialButton(
                      onPressed: () {},
                      child: new Icon(
                        Icons.phone_android,
                        color: Colors.black,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.white,
                      padding: const EdgeInsets.all(10.0),
                    ),
                    Text(
                      "Mobil Fatura",
                      textScaleFactor: 1.2,
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    RawMaterialButton(
                      onPressed: () {
                        _showDialog("MOBİL FATURA");
                      },
                      child: new Icon(
                        Icons.arrow_forward,
                        color: Colors.white,
                      ),
                      shape: new CircleBorder(),
                      elevation: 2.0,
                      fillColor: Colors.pink,
                      padding: const EdgeInsets.all(10.0),
                    ),
                  ],
                ),
              ),
            ),
            /*card(
              icon: Icons.directions_bus,
              islem: "Akbil",
              actionTap: ()
              {
                _showDialog("AKBİL");
              },
            ),
            card(
              icon: Icons.add_shopping_cart,
              islem: "Market",
              actionTap: ()
              {
                _showDialog("MARKET");
              },
            ),
            card(
              icon: Icons.ac_unit,
              islem: "Su",
              actionTap: ()
              {
                _showDialog("SU");
              },
            ),
            card(
              icon: Icons.wb_incandescent,
              islem: "Elektrik",
              actionTap: ()
              {
                _showDialog("ELEKTRİK");
              },
            ),
            card(
              icon: Icons.redeem,
              islem: "Doğalgaz",
              actionTap: ()
              {
                _showDialog("DOĞALGAZ");
              },
            ),
            card(
              icon: Icons.phone_android,
              islem: "Mobil Fatura",
              actionTap: ()
              {
                _showDialog("MOBİL FATURA");
              },
            ),*/
          ],
        ),
      ),
    );
  }
}

class card extends StatelessWidget
{
  final IconData icon;
  final String islem;
  final VoidCallback actionTap;

  card({this.icon, this.islem, this.actionTap});


  @override
  Widget build(BuildContext context) {
    return Card(
      color: Colors.black54,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(25.0),
      ),
      child: Container(
        alignment: Alignment.center,
        padding: EdgeInsets.all(3.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: <Widget>[
            RawMaterialButton(
              onPressed: () {},
              child: new Icon(
                icon,
                color: Colors.black,
              ),
              shape: new CircleBorder(),
              elevation: 2.0,
              fillColor: Colors.white,
              padding: const EdgeInsets.all(10.0),
            ),
            Text(
              "" + islem,
              textScaleFactor: 1.2,
              style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
            RawMaterialButton(
              onPressed: () {
                actionTap;
              },
              child: new Icon(
                Icons.arrow_forward,
                color: Colors.white,
              ),
              shape: new CircleBorder(),
              elevation: 2.0,
              fillColor: Colors.black12,
              padding: const EdgeInsets.all(10.0),
            ),
          ],
        ),
      ),
    );
  }

}
