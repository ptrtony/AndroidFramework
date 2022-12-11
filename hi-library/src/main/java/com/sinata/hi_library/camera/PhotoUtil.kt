package com.sinata.hi_library.camera

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import com.sinata.hi_library.R
import com.sinata.hi_library.camera.PhotoUtil
import com.tbruyelle.rxpermissions.RxPermissions
import rx.functions.Action1
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/9/18
 */
open class PhotoUtil private constructor(private val context: Activity) : PhotoInterceptor {

    // 申请相机权限的requestCode
    private val PERMISSION_CAMERA_REQUEST_CODE = 0x00000012

    override fun checkPermission() {
        RxPermissions(context)
            .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .doOnNext { aBoolean ->
                if (aBoolean) {
                    openCamera()
                } else {
                    Toast.makeText(context, "您拒绝了如下权限", Toast.LENGTH_SHORT).show()
                }
            }


//        Toast.makeText(context, "您拒绝了如下权限：$deniedList", Toast.LENGTH_SHORT).show()
    }

    //用于保存拍照图片的uri
    private var mCameraUri: Uri? = null

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private var mCameraImagePath: String? = null

    // 是否是Android 10以上手机
    private var isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;


    override fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(context.getPackageManager()) != null) {
            var photoFile :File ?= null
            var photoUri:Uri? = null

            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri()
            } else {
                try {
                    photoFile = createImageFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.absolutePath;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", photoFile)
                    } else {
                        photoUri = Uri.fromFile(photoFile)
                    }
                }
            }

            mCameraUri = photoUri
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                context.startActivityForResult(captureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }



    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private fun createImageUri() :Uri? {
        val status = Environment.getExternalStorageState()
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        return if (status.equals(Environment.MEDIA_MOUNTED)) {
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues())
        } else {
            context.contentResolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, ContentValues())
        }
    }

    /**
     * 创建保存图片的文件
     */

    @Throws
    private fun createImageFile():File ? {
        val imageName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir!!.exists()) {
            storageDir.mkdir();
        }
        val tempFile = File(storageDir, imageName)
        if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
            return null
        }
        return tempFile
    }

    override fun openGallery() {}


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //允许权限，有调起相机拍照。
                openCamera()
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(context, "拍照权限被拒绝", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,callback:Action1<Bitmap>) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val mp = if (isAndroidQ) {
                    // Android 10 使用图片uri加载
                    MediaStore.Images.Media.getBitmap(context.contentResolver, mCameraUri)
                } else {
                    // Android 10 以下使用图片路径加载
                    BitmapFactory.decodeFile(mCameraUri?.path)
                }
                //对图片添加水印 这里添加一张图片为示例：
                val bitmap = ImageUtil.drawTextToLeftTop(context,mp,"示例文字",30, R.color.color_000,20,30)
                callback.call(bitmap)
            } else {
                Toast.makeText(context,"取消",Toast.LENGTH_LONG).show();
            }
        }
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 1
        var instance: PhotoUtil? = null
        fun getInstance(activity: Activity): PhotoUtil {
            if (instance == null) {
                synchronized(PhotoUtil::class.java) {
                    if (instance == null) {
                        instance = PhotoUtil(activity)
                    }
                }
            }
            return instance!!
        }
    }
}