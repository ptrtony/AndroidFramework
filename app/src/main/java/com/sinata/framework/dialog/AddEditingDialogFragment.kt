package com.sinata.framework.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.sinata.framework.R
import com.sinata.framework.model.entity.Address

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/25
 */
class AddEditingDialogFragment : AppCompatDialogFragment() {

    private var address: Address? = null

    companion object {
        const val KEY_ADDRESS_PARAMS = "key_address"
    }

    fun newInstance(address: Address?): AddEditingDialogFragment {
        val args = Bundle()
        args.putParcelable(KEY_ADDRESS_PARAMS, address)
        val fragment = AddEditingDialogFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        address = arguments?.getParcelable(KEY_ADDRESS_PARAMS)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = dialog?.window
        //使用Android.R.content作为父容器，在inflate布局的时候，会使用他的layoutParams来设置子View的宽高
        val root = window?.findViewById(android.R.id.content)?:container
        val contentView = inflater.inflate(R.layout.fragment_add_editing_dialog,root,false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}