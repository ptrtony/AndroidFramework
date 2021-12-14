


import 'package:flutter/material.dart';
import 'package:flutter_library_module/hi_image_view.dart';
import 'package:flutter_library_module/hi_image_view_controller.dart';

class HiNativePage extends StatefulWidget {
  @override
  _HiNativePageState createState() => _HiNativePageState();
}

class _HiNativePageState extends State<HiNativePage> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Text('Flutter 嵌入Native组件的实现和原理'),
        SizedBox(height: 300,
        child: HiImageView(url: "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F53%2F0a%2Fda%2F530adad966630fce548cd408237ff200.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641652170&t=36123e73762c2264de8f64b6d4dd459b",
        onViewCreated:_onViewCreated ,),)
      ],
    );
  }


  void _onViewCreated(HiImageViewController hiImageViewController){
    Future.delayed(Duration(milliseconds: 3000),(){
      hiImageViewController.setUrl("");
    });
  }
}
