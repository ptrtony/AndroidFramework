
import 'package:flutter/services.dart';

class HiImageViewController{
  MethodChannel _channel;
  HiImageViewController(int id){
    _channel = MethodChannel("HiImageView_$id");
    _channel.setMethodCallHandler(_handleMethod);
  }


  ///来自native的调用
  Future<dynamic> _handleMethod(MethodCall call) async{
    switch(call.method){

    }
  }

  ///调用native的方法
  Future<void> setUrl(String url) async{
    try{
      final String result = await _channel.invokeMethod("setUrl",{"url":url});
      print("result from native" + result);
    } on PlatformException catch(e){
      print("${e.message}");
    }
  }

}