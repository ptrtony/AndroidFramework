package com.sinata.framework.biz.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.sinata.common.ui.component.HiBaseActivity
import com.sinata.hi_library.utils.SPUtils
import com.sinata.framework.R
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.api.AccountApi
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
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

        action_login.setOnClickListener {
            goLogin()
        }

        tv_go_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun goLogin() {
        val userName = input_item_user_name.getEditText().text.toString().trim()
        val password = input_item_password.getEditText().text.toString().trim()
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            return
        }

        //viewmodel + repository + liivedata
        ApiFactory.create(AccountApi::class.java).login(userName,password)
            .enqueue(object:HiCallback<String>{
                override fun onSuccess(response: HiResponse<String>) =
                    if (response.code == HiResponse.SUCCESS){
                        //usermanager
                        showToast(getString(R.string.login_success))
                        val data = response.data
                        SPUtils.putString("boarding-pass",data!!)
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    }else{
                        showToast(getString(R.string.login_failed) + "${response.errorData}")
                    }

                override fun onFailed(throwable: Throwable) {
                    showToast(getString(R.string.login_failed) + throwable.message)
                }
            })
    }

    private fun showToast(msg: String) {
        Toast.makeText(baseContext,msg,Toast.LENGTH_SHORT).show()
    }
}