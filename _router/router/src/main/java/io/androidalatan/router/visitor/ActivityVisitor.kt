package io.androidalatan.router.visitor

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor

class ActivityVisitor(private val activity: FragmentActivity) : Visitor {

    private val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    override fun createComponent(clazz: Class<*>): ComponentName =
        ComponentName(activity, clazz)

    override fun getPendingIntent(reqCode: Int, flags: Int, intent: Intent): PendingIntent {
        return PendingIntent.getActivity(activity, reqCode, intent, flags)
    }

    override fun end() {
        activity.finish()
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        val bundle = Bundle()
        val bundleData: BundleData = BundleDataImpl(bundle)
        bundleBody.invoke(bundleData)
        val intent = Intent()
        intent.putExtras(bundle)
        if (uriData.isNotEmpty()) {
            intent.data = Uri.parse(uriData)
        }
        activity.setResult(
            resultCode,
            intent
        )
        activity.finish()
    }

    override fun buildFragment(clazz: Class<out Fragment>): Fragment {
        return activity.supportFragmentManager.fragmentFactory.instantiate(activity.applicationContext.classLoader, clazz.canonicalName!!)
    }

    override fun visitActivity(intent: Intent) {
        activity.startActivity(intent)
    }

    override fun visitGroup(intents: Array<out Intent>) {
        activity.startActivities(intents)
    }

    override fun visitForResult(reqCode: Int, intent: Intent) {
        activity.startActivityForResult(intent, reqCode)
    }

    override fun visitFragment(containerId: Int, fragment: Fragment) {
        fragmentManager.beginTransaction()
            .run {
                add(containerId, fragment)
                commit()
            }
    }

    override fun replaceFragment(containerId: Int, fragment: Fragment) {
        fragmentManager.beginTransaction()
            .run {
                replace(containerId, fragment)
                commit()
            }

    }

    override fun visitService(intent: Intent) {
        activity.startService(intent)
    }

    fun restart() {
        activity.recreate()
    }
}