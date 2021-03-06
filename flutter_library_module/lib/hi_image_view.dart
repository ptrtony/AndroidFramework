


import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_library_module/hi_image_view_controller.dart';

typedef void HiImageViewCreatedCallback(HiImageViewController hiImageViewController);

class HiImageView extends StatefulWidget {
  final String url;
  final HiImageViewCreatedCallback onViewCreated;
  const HiImageView({Key key, this.url,this.onViewCreated}) : super(key: key);
  @override
  _HiImageViewState createState() => _HiImageViewState();
}

class _HiImageViewState extends State<HiImageView> {
  static const StandardMessageCodec _decoder = StandardMessageCodec();
  @override
  Widget build(BuildContext context) {
    return AndroidView(viewType: "HiImageView",
      onPlatformViewCreated: _onPlatformViewCreated,
      creationParams: {'url',widget.url},
    creationParamsCodec:_decoder,);
  }


  void _onPlatformViewCreated(int id){
    if(widget.onViewCreated == null){
      return;
    }
    widget.onViewCreated(HiImageViewController(id));
  }
}

