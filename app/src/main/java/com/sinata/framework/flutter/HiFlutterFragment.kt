package com.sinata.framework.flutter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.sinata.common.R
import com.sinata.common.ui.component.HiBaseFragment
import com.sinata.hi_library.utils.ToastUtils
import io.flutter.embedding.android.FlutterTextureView
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/11/24
 */
open abstract class HiFlutterFragment constructor(val moduleName:String) : HiBaseFragment() {

    private lateinit var flutterEngine: FlutterEngine
    private lateinit var flutterView: FlutterView
    private lateinit var root:View

    private val cached = HiFlutterCacheManager.instance!!.hastCached(moduleName)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        flutterEngine = HiFlutterCacheManager.instance!!.getCacheFlutterEngine(
            context,
            moduleName
        )
        HiFlutterBridge.init(flutterEngine)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root = view
        (layoutView as ViewGroup).addView(createFlutterView(activity))
        view.findViewById<TextView>(R.id.tv_back).setOnClickListener {
            activity?.onBackPressed()
        }
    }

    /**
     * 设置标题
     */
    fun setTitle(titleStr: String) {
        val titleTv = root.findViewById<TextView>(R.id.tv_title)
        titleTv.text = titleStr
        titleTv.setOnClickListener {
            HiFlutterBridge.instance!!.fire("onRefresh", "so easy", object : MethodChannel.Result {
                override fun success(result: Any?) {
                    ToastUtils.showShortToast(result as String)
                }

                override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                    ToastUtils.showShortToast(errorMessage)
                }

                override fun notImplemented() {

                }

            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //注册flutter/platform_views 插件以便能够处理native view
        if (!cached){
            flutterEngine.platformViewsController.attach(context,flutterEngine.renderer,flutterEngine.dartExecutor)
        }
    }

    private fun createFlutterView(activity: FragmentActivity?): View {
        activity?.apply {
            val flutterTextureView = FlutterTextureView(activity)
            flutterView = FlutterView(activity, flutterTextureView)
        }
        return flutterView
    }


    override fun onStart() {
        super.onStart()
        flutterView.attachToFlutterEngine(flutterEngine)
    }

    override fun onResume() {
        super.onResume()
        flutterEngine.lifecycleChannel.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine.lifecycleChannel.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        flutterEngine.lifecycleChannel.appIsPaused()
    }

    override fun onDetach() {
        super.onDetach()
        flutterEngine.lifecycleChannel.appIsDetached()
    }

    override fun onDestroy() {
        super.onDestroy()
        flutterView.detachFromFlutterEngine()
    }

    override fun getLayoutId(): Int = R.layout.fragment_hi_flutter
}