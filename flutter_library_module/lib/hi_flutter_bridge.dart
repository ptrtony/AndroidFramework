import 'package:flutter/services.dart';

class HiFlutterBridge {
  static HiFlutterBridge _instance = HiFlutterBridge._();
  MethodChannel _bridge = const MethodChannel("HiFlutterBridge");
  var _listeners = {};

  HiFlutterBridge._() {
    _bridge.setMethodCallHandler((MethodCall call) {
      String method = call.method;
      if (_listeners[method] != null) {
        return _listeners[method](call);
      }
      return null;
    });
  }

  static HiFlutterBridge getInstance() {
    return _instance;
  }

  register(String method, Function(MethodCall) callback) {
    _listeners[method] = callback;
  }

  unregister(String method) {
    _listeners.remove(method);
  }

  gotoNative(Map params) {
    _bridge.invokeMethod("gotoNative", params);
  }

  MethodChannel getBridge() {
    return _bridge;
  }

}
