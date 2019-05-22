import 'package:flutter/material.dart';

var orange = Color(0xFFfc9f6a);
var pink = Color(0xFFee528c);
var blue = Color(0xFF8bccd6);
var darkBlue = Color(0xFF60a0d7);
var valueBlue = Color(0xFF5fa0d6);

class MyHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
          //color: Color(0xFFf7f8fc),
          color: Colors.black87,
          child: ListView(
            children: <Widget>[
              SizedBox(
                height: 32.0,
              ),
              ItemCard('Kazanılan', '', '\$ 3284.00', [Colors.green,Colors.green],Colors.white),
              SizedBox(
                height: 8.0,
              ),
              ItemCard('Harcanan', '', '\$ 3284.00', [Colors.red, Colors.red],Colors.white),
              SizedBox(
                height: 32.0,
              ),
              SizedBox(
                height: 4.0,
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8.0),
                child: Card(
                  color: Colors.black12,
                  child: Column(
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
        ));
  }
}

class ValueCard extends StatelessWidget {
  final name;
  final value;
  final date;
  final bank;
  final color;
  ValueCard(this.name,this.value,this.date, this.bank,this.color);
  @override
  Widget build(BuildContext context) {
    return Container(
        padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 4.0),
        color: Colors.black,
        child: Column(
          children: <Widget>[
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Text(
                  name,
                  //style: TextStyle(fontWeight: FontWeight.w500, color: Colors.black.withOpacity(0.6)),
                  style: TextStyle(fontWeight: FontWeight.w500, color: Colors.white),
                ),
                Text(
                  value,
                  style: TextStyle(
                    //color: valueBlue,
                    color: color,
                    fontSize: 18.0,
                    fontWeight: FontWeight.bold,),
                )
              ],
            ),
            SizedBox(
              height: 4.0,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Text(
                  date,
                  style: TextStyle(color: Colors.grey),
                ),
                Text(
                  bank,
                  style: TextStyle(color: Colors.grey),
                )
              ],
            ),
            SizedBox(height: 4.0,),
            Divider()
          ],
        ));
  }
}

class ItemCard extends StatelessWidget {
  final titel;
  final subtitle;
  final value;
  final colors;
  final textColor;
  ItemCard(this.titel, this.subtitle, this.value, this.colors, this.textColor);
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
                  color: textColor,
                  fontWeight: FontWeight.bold,
                  fontSize: 20.0),
            )
          ],
        ),
      ),
    );
  }
}