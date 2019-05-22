import 'package:flutter/material.dart';


class text1 extends StatelessWidget {
  final textBox;
  final controller;
  final icon;
  final textType;
  final obscure;
  final labelText;

  const text1(
      {this.textBox, this.controller, this.icon, this.textType, this.obscure, this.labelText});

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
              labelText: labelText,
              labelStyle: TextStyle(
                color: Colors.blue,
              ),
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


class text2 extends StatelessWidget {
  final textBox;
  final controller;
  final icon;
  final textType;
  final obscure;

  const text2(
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