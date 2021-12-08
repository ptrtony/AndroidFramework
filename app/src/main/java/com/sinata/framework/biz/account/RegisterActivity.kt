package com.sinata.framework.biz.account

import android.graphics.Color
import android.os.Bundle
import com.sinata.common.ui.component.HiBaseActivity
import com.sinata.framework.R
import com.sinata.hi_library.restful.http.ApiFactory
import com.sinata.framework.http.api.AccountApi
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import com.sinata.hi_library.utils.HiStatusBar
import kotlinx.android.synthetic.main.activity_regisiter.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 19/7/2021
 */
class RegisterActivity : HiBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regisiter)
        HiStatusBar.setStatusBar(this,true, Color.WHITE,false)

        action_register.setOnClickListener {
            goRegister()
        }

        action_back.setOnClickListener {
            onBackPressed()
        }




    }

    private fun goRegister() {
        val username = input_item_user_name.getEditText().text.toString().trim()
        val password = input_item_password.getEditText().text.toString().trim()
        val imoocId = input_item_imoocid.getEditText().text.toString().trim()
        val orderId = input_item_order_id.getEditText().text.toString().trim()

        if (username.isEmpty() || password.isEmpty() || imoocId.isEmpty() || orderId.isEmpty()){
            return
        }

        ApiFactory.create(AccountApi::class.java).registration(username,password, imoocId, orderId)
            .enqueue(object:HiCallback<String>{
                override fun onSuccess(response: HiResponse<String>) {

                }

                override fun onFailed(throwable: Throwable) {

                }

            })

    }


}