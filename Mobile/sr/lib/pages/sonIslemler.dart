import 'package:flutter/material.dart';
import 'package:sr/pages/bank.dart';

class sonIslemler extends StatefulWidget {
  @override
  _sonIslemlerState createState() => _sonIslemlerState();
}

class _sonIslemlerState extends State<sonIslemler> {
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
      //Baqck Tuşu
      onWillPop: _willPopCallback,
      child: Scaffold(
        body: _tabbarLS(context),
      ),
    );
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
                birikim(context),
                odeme(context),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget yap_tabbar() {
    return AppBar(
      backgroundColor: Colors.black,
      title: TabBar(
        tabs: [
          Text(
            "BİRİKİM",
            style: TextStyle(
                fontSize: 25.0,
                fontWeight: FontWeight.bold,
                color: Colors.white),
          ),
          Text(
            "ÖDEME",
            style: TextStyle(
                fontSize: 25.0,
                fontWeight: FontWeight.bold,
                color: Colors.white),
          ),
          //new Tab(icon: new Icon(Icons.account_circle)),
          //new Tab(icon: new Icon(Icons.person_add)),
        ],
        indicatorColor: Colors.white,
      ),
    );
  }

  Widget birikim(BuildContext context) {

    List cards = new List<ValueCard>.generate(20, (i)=>new ValueCard('0.50 ml Plastik Pet x 3','\$ 3284.00','12-03-2019','Çaydaçıra',Colors.green));

    return Container(
      //color: Color(0xFFf7f8fc),
      color: Colors.black87,
      child: ListView(
        children: <Widget>[
          SizedBox(
            height: 32.0,
          ),
          ItemCard('Kazanılan', '', '\$ 3284.00', [Colors.green,Colors.green],Colors.white),
          SizedBox(
            height: 40.0,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Card(
              color: Colors.black87,
              child: Column(
                //children: cards,
                children: <Widget>[
                  SizedBox(height: 16.0,),
                  ValueCard('0.50 ml Plastik Pet x 3','\$ 3284.00','12-03-2019','Çaydaçıra',Colors.green),
                  ValueCard('0.50 ml Plastik Pet x 3','\$ 64.00','12-03-2019','Hilal Kent',Colors.green),
                  ValueCard('0.50 ml Metal Teneke x 3','\$ 6532.21','12-03-2019','Bahçelievler',Colors.green),
                  ValueCard('0.50 ml Cam Şişe x 3','\$ 258.00','12-03-2019','Gazi Caddesi',Colors.green),
                  ValueCard('0.50 ml Plastik Pet x 3','\$ 533.20','12-03-2019','Çaydaçıra',Colors.green),
                  ValueCard('0.50 ml Cam Şişe x 3','\$ 3335','12-03-2019','Doğu Kent',Colors.green),
                  ValueCard('0.50 ml Cam Şişe x 3','\$ 520','12-03-2019','Çaydaçıra',Colors.green),
                ],
              ),
            ),
          )
        ],
      ),
    );
  }


  Widget odeme(BuildContext context) {
    return Container(
      //color: Color(0xFFf7f8fc),
      color: Colors.black87,
      child: ListView(
        children: <Widget>[
          SizedBox(
            height: 32.0,
          ),
          ItemCard('Harcanan', '', '\$ 1284.00', [Colors.red,Colors.red],Colors.white),
          SizedBox(
            height: 40.0,
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 8.0),
            child: Card(
              color: Colors.black87,
              child: Column(
                children: <Widget>[
                  SizedBox(height: 16.0,),
                  ValueCard('Su','\$ 3284.00','12-03-2019','Çaydaçıra',Colors.red),
                  ValueCard('Elektrik','\$ 64.00','12-03-2019','Hilal Kent',Colors.red),
                  ValueCard('Mobil Fatura','\$ 6532.21','12-03-2019','Bahçelievler',Colors.red),
                  ValueCard('Market','\$ 258.00','12-03-2019','Gazi Caddesi',Colors.red),
                  ValueCard('Doğalgaz','\$ 533.20','12-03-2019','Çaydaçıra',Colors.red),
                  ValueCard('Su','\$ 3335','12-03-2019','Doğu Kent',Colors.red),
                  ValueCard('Elektrik','\$ 520','12-03-2019','Çaydaçıra',Colors.red),
                ],
              ),
            ),
          )
        ],
      ),
    );
  }
}

class card extends StatelessWidget {
  final String atik;
  final String para;
  final String tarih;
  final Color renk;

  const card({Key key, this.atik, this.para, this.tarih, this.renk}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      color: Colors.black54,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(25.0),
      ),
      child: Container(
        alignment: Alignment.centerRight,
        padding: const EdgeInsets.all(10.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: <Widget>[
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: <Widget>[
                Container(
                  alignment: Alignment.bottomLeft,
                  child: Text(
                    atik,
                    textScaleFactor: 1.2,
                    style: TextStyle(
                      fontSize: 15.0,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
                Container(
                  alignment: Alignment.bottomRight,
                  child: Text(
                    para,
                    textScaleFactor: 1.2,
                    style: TextStyle(
                      fontSize: 15.0,
                      fontWeight: FontWeight.bold,
                      color: renk,
                    ),
                  ),
                ),
              ],
            ),
            Padding(
              padding: const EdgeInsets.all(5.0),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: <Widget>[
                Container(
                  alignment: Alignment.bottomLeft,
                  child: Text(
                    tarih,
                    textScaleFactor: 1.2,
                    style: TextStyle(
                      fontSize: 10.0,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
                Container(
                  alignment: Alignment.bottomRight,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
