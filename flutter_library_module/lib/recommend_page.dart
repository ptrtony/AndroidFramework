

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_library_module/hi_flutter_bridge.dart';

class RecommendPage extends StatefulWidget {

  @override
  _RecommendPageState createState() => _RecommendPageState();
}

class _RecommendPageState extends State<RecommendPage> {

  @override
  void initState() {
    super.initState();
    HiFlutterBridge.getInstance().register("onRefresh", (MethodCall call){
      print("----------onRefresh------------");
      return Future.value("flutter received...");
    });
  }


  @override
  void dispose() {
    HiFlutterBridge.getInstance().unregister("onRefresh");
    super.dispose();
  }



  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Text("推荐模块"),
          TextButton(onPressed: (){
            HiFlutterBridge.getInstance().gotoNative({
              "action" : "togoGoodsDetail",
              "goodsId" : "1234456789"
            });
          }, child: Text("跳转到商品详情"))
        ],
      ));
  }
}
