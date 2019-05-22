import 'package:flutter/material.dart';
import 'package:sr/pages/parola3.dart';
import 'dart:math' as math;

class ptime extends StatefulWidget {
  @override
  _ptimeState createState() => _ptimeState();
}

class _ptimeState extends State<ptime> with TickerProviderStateMixin {
  TextEditingController Controller = new TextEditingController();

  String controllerr;

  AnimationController controller;

  String get timerString {
    Duration duration = controller.duration * controller.value;
    return '${duration.inMinutes}:${(duration.inSeconds % 60).toString().padLeft(2, '0')}';
  }

  @override
  void initState() {
    super.initState();
    controller = AnimationController(
      vsync: this,
      duration: Duration(seconds: 120),
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

  @override
  Widget build(BuildContext context) {
    ThemeData themeData = Theme.of(context);
    return WillPopScope(
      onWillPop: _willPopCallback,
      child: Scaffold(
        appBar: AppBar(
          title: Text("Güvenlik Kodu"),
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
                        "Güvenlik Kodu",
                        style: TextStyle(
                          fontSize: 35.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.black54,
                        ),
                      ),
                    ),
                    Row(
                      // boşluk bırakma
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.all(0.0),
                        )
                      ],
                    ),
                    Expanded(
                      child: Align(
                        alignment: FractionalOffset.center,
                        child: AspectRatio(
                          aspectRatio: 1.0,
                          child: Stack(
                            children: <Widget>[
                              Positioned.fill(
                                child: AnimatedBuilder(
                                  animation: controller,
                                  builder:
                                      (BuildContext context, Widget child) {
                                    return CustomPaint(
                                        painter: TimerPainter(
                                      animation: controller,
                                      backgroundColor: Colors.white,
                                      color: themeData.indicatorColor,
                                    ));
                                  },
                                ),
                              ),
                              Align(
                                alignment: FractionalOffset.center,
                                child: Column(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceEvenly,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    AnimatedBuilder(
                                        animation: controller,
                                        builder: (BuildContext context,
                                            Widget child) {
                                          return Text(
                                            timerString,
                                            style: themeData.textTheme.display4,
                                          );
                                        }),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                    Container(
                      margin: EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: <Widget>[
                          FloatingActionButton(
                            child: AnimatedBuilder(
                              animation: controller,
                              builder: (BuildContext context, Widget child) {
                                return Icon(controller.isAnimating
                                    ? Icons.pause
                                    : Icons.play_arrow);
                              },
                            ),
                            onPressed: () {
                              if (controller.isAnimating)
                                controller.stop();
                              else {
                                controller.reverse(
                                    from: controller.value == 0.0
                                        ? 1.0
                                        : controller.value);
                              }
                            },
                          )
                        ],
                      ),
                    ),
                    TextField(
                      keyboardType: TextInputType.emailAddress,
                      controller: Controller,
                      obscureText: true,
                      decoration: InputDecoration(
                        //prefixIcon: Icon(Icons.person),
                        icon: const Icon(Icons.person),
                        labelText: "Güvenlik Kodu",
                        hintText: "123456",
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
                            colors: <Color>[Colors.black87, Colors.black87],
                          ),
                          borderRadius: BorderRadius.all(Radius.circular(30.0)),
                        ),
                        child: RaisedButton(
                          onPressed: () {
                            // butona tıklama
                            controllerr = Controller.text;
                            Navigator.pushReplacement(
                              context,
                              new MaterialPageRoute(
                                  builder: (ctxt) => new parola3()),
                            );
                          },
                          child: Text(
                            // butonun text kısmı
                            "Kodu Gönder",
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

class TimerPainter extends CustomPainter {
  TimerPainter({
    this.animation,
    this.backgroundColor,
    this.color,
  }) : super(repaint: animation);

  final Animation<double> animation;
  final Color backgroundColor, color;

  @override
  void paint(Canvas canvas, Size size) {
    Paint paint = Paint()
      ..color = backgroundColor
      ..strokeWidth = 5.0
      ..strokeCap = StrokeCap.round
      ..style = PaintingStyle.stroke;

    canvas.drawCircle(size.center(Offset.zero), size.width / 2.0, paint);
    paint.color = color;
    double progress = (1.0 - animation.value) * 2 * math.pi;
    canvas.drawArc(Offset.zero & size, math.pi * 1.5, -progress, false, paint);
  }

  @override
  bool shouldRepaint(TimerPainter old) {
    return animation.value != old.animation.value ||
        color != old.color ||
        backgroundColor != old.backgroundColor;
  }
}
