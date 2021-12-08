package com.sinata.framework.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/11/24
 */
class GlideDemo {

    public void glide(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }
}
