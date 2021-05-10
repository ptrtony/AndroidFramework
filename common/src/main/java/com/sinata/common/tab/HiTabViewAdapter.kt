package com.sinata.common.tab

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sinata.hi_ui.tab.bottom.HiTabBottomInfo

/**
@author cjq
@Date 7/5/2021
@Time 3:26 PM
@Describe:
 */
class HiTabViewAdapter(private val mFragmentManager: FragmentManager, private val mInfoList: List<HiTabBottomInfo<*>>) {
    private var mCurrentFragment: Fragment? = null

    /**
     * 实例化以及显示指定位置的fragment
     */
    fun instantiateItem(container: ViewGroup, position: Int) {
        val transaction = mFragmentManager.beginTransaction()
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment!!)
        }
        val name = container.id.toString() + ":" + position
        var fragment = mFragmentManager.findFragmentByTag(name)
        if (fragment != null) {
            transaction.show(fragment)
        } else {
            fragment = getItem(position)
            if (!fragment!!.isAdded) {
                transaction.add(container.id, fragment, name)
            }
        }
        mCurrentFragment = fragment
        transaction.commitAllowingStateLoss()
    }

    fun getCurrentFragment(): Fragment? {
        return mCurrentFragment
    }

    fun getItem(position: Int): Fragment? {
        try {
            return mInfoList[position].fragment!!.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getCount(): Int {
        return mInfoList.size
    }

}
