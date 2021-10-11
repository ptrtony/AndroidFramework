package com.sinata.framework.biz.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.zxing.client.android.CaptureActivity
import com.sinata.common.ui.component.HiBaseActivity
import com.sinata.hi_library.utils.SPUtils
import com.sinata.framework.R
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.api.AccountApi
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import com.sinata.hi_library.utils.HiStatusBar
import kotlinx.android.synthetic.main.activity_login.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 18/7/2021
 */
class LoginActivity : HiBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        HiStatusBar.setStatusBar(this, true, Color.WHITE, false)
        action_login.setOnClickListener {
            goLogin()
        }

        tv_go_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun goLogin() {
        startActivity(Intent(this, CaptureActivity::class.java))
//        val userName = input_item_user_name.getEditText().text.toString().trim()
//        val password = input_item_password.getEditText().text.toString().trim()
//        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
//            return
//        }
//
//        //viewmodel + repository + liivedata
//        ApiFactory.create(AccountApi::class.java).login(userName, password)
//            .enqueue(object : HiCallback<String> {
//                override fun onSuccess(response: HiResponse<String>) =
//                    if (response.code == HiResponse.SUCCESS) {
//                        //usermanager
//                        showToast(getString(R.string.login_success))
//                        val data = response.data
//                        SPUtils.putString("boarding-pass", data!!)
//                        setResult(Activity.RESULT_OK, Intent())
//                        finish()
//                    } else {
//                        showToast(getString(R.string.login_failed) + "${response.errorData}")
//                    }
//
//                override fun onFailed(throwable: Throwable) {
//                    showToast(getString(R.string.login_failed) + throwable.message)
//                }
//            })
    }

    private fun showToast(msg: String) {
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
    }
}