package com.sinata.framework

import android.content.Intent
import android.graphics.Typeface
import android.graphics.fonts.FontStyle
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.sinata.common.ui.component.HiBaseFragment
import com.sinata.common.ui.view.loadCircleUrl
import com.sinata.common.ui.view.loadRoundCornerUrl
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.api.AccountApi
import com.sinata.framework.model.CourseNotice
import com.sinata.framework.model.Notice
import com.sinata.framework.model.UserProfile
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import kotlinx.android.synthetic.main.fragment_profile.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 22/7/2021
 */
class ProfileFragment : HiBaseFragment() {
    val ITEM_PLACE_HOLDE = "  "
    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item_notify.setText(R.string.if_notify)
        item_notify.append(ITEM_PLACE_HOLDE + getString(R.string.item_notify))

        item_collection.setText(R.string.if_collection)
        item_collection.append(ITEM_PLACE_HOLDE + getString(R.string.item_collection))

        item_address.setText(R.string.if_address)
        item_address.append(ITEM_PLACE_HOLDE + getString(R.string.item_address))

        item_history.setText(R.string.if_history)
        item_history.append(ITEM_PLACE_HOLDE + getString(R.string.item_history))

        queryLoginUserData()
        queryCourseNotice()
    }

    private fun queryLoginUserData() {
        ApiFactory
            .create(AccountApi::class.java)
            .profile()
            .enqueue(object : HiCallback<UserProfile> {
                override fun onSuccess(data: HiResponse<UserProfile>) {
                    val userProfile = data.data
                    if (data.code == HiResponse.SUCCESS && userProfile != null) {
                        updateUI(userProfile)
                        updateBanner(userProfile.bannerNoticeList)
                    } else {
                        showToast(data.msg)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    showToast(throwable.message)
                }

            })

    }

    private fun queryCourseNotice() {
        ApiFactory.create(AccountApi::class.java)
            .notice()
            .enqueue(object : HiCallback<CourseNotice> {
                override fun onSuccess(response: HiResponse<CourseNotice>) {
                    if (response.data != null && response.data!!.total > 0) {
                        notify_count.text = response.data!!.total.toString()
                        notify_count.visibility = View.VISIBLE
                    }
                }

                override fun onFailed(throwable: Throwable) {

                }

            })
    }

    private fun updateBanner(bannerNoticeList: List<Notice>?) {
        if (bannerNoticeList == null || bannerNoticeList.isEmpty()) return
        hi_banner.setBannerData(R.layout.layout_profile_banner_item, bannerNoticeList)
        hi_banner.setBindAdapter { viewHolder, mo, position ->
            if (viewHolder == null) return@setBindAdapter
            val imageView = viewHolder.findViewById<ImageView>(R.id.banner)
            imageView.loadRoundCornerUrl(mo.bannerUrl, HiDisplayUtil.dp2Px(10f))
        }
        hi_banner.setOnBannerClickListener { viewHolder, mo, position ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bannerNoticeList[position].url))
            startActivity(intent)
        }
        hi_banner.visibility = View.VISIBLE
    }

    private fun showToast(message: String?) {
        if (message == null) return
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(userProfile: UserProfile) {
        user_name.text =
            if (userProfile.isLogin) userProfile.userName else getString(R.string.please_login)
        tab_item_collection.text =
            spannableTabItem(userProfile.favoriteCount, getString(R.string.profile_collection))
        tab_item_history.text =
            spannableTabItem(userProfile.browseCount, getString(R.string.profile_browse_history))
        tab_item_learn.text =
            spannableTabItem(userProfile.learnMinutes, getString(R.string.profile_learn_time))

    }

    private fun spannableTabItem(topText: Int, bottomText: String): CharSequence? {
        val spanStr = topText.toString()
        val ssb = SpannableStringBuilder()
        val ssTop = SpannableString(spanStr)
        ssTop.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_000)),
            0,
            spanStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssTop.setSpan(
            AbsoluteSizeSpan(18, true),
            0,
            spanStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssTop.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            spanStr.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(ssTop).append(bottomText)
        return ssb
    }
}