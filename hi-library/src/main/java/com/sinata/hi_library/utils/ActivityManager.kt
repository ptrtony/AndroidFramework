package com.sinata.hi_library.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

/**
@author cjq
@Date 10/5/2021
@Time 9:44 PM
@Describe:
 */
open class ActivityManager private constructor() {

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontBackCallback = ArrayList<FontBackCallback>()
    var front = true
    private var activityStartCount = 0

    interface FontBackCallback {
        fun onChange(font: Boolean)
    }

    fun addFontBackCallback(callBack: FontBackCallback) {
        frontBackCallback.add(callBack)
    }

    fun removeFontBackCallback(callBack: FontBackCallback) {
        frontBackCallback.remove(callBack)
    }

    companion object {
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    inner class InnerActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackCallback(front)
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (front && activityStartCount <= 0) {
                front = false
                onFrontBackCallback(front)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef != null && activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

    }

    private fun onFrontBackCallback(front: Boolean) {
        for (callback in frontBackCallback) {
            callback.onChange(front)
        }
    }

    val topActivity: Activity?
        get() {
            return if (activityRefs.size <= 0) {
                null
            } else {
                activityRefs[activityRefs.size - 1].get()
            }
            return null
        }

}