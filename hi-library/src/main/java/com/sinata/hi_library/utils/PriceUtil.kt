package com.sinata.hi_library.utils

import android.text.TextUtils
import java.math.BigDecimal
import java.text.DecimalFormat

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/23
 */
internal object PriceUtil {
    private fun subPrice(goodsPrice:String?):String?{
        if (goodsPrice!=null){
            return if (goodsPrice.startsWith("¥") && goodsPrice.length > 1){
                goodsPrice.substring(1,goodsPrice.length)
            }else{
                goodsPrice
            }
        }
        return null
    }

    fun calculate(goodsPrice: String?,amount:Int):String{
        val price = subPrice(goodsPrice)
        if (TextUtils.isEmpty(price))return ""
        //在做金额进行加减乘除的时候不能够直接使用基本的数据类型 +-*/
        val bigDecimal = BigDecimal(price)
        val multiply = bigDecimal.multiply(BigDecimal(amount))
        //金额数据的格式化
        val df = DecimalFormat("###,###.##")
        return df.format(multiply)
    }

}