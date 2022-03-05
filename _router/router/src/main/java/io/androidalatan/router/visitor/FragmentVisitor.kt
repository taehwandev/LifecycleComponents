package io.androidalatan.router.visitor

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor

class FragmentVisitor(private val originFragment: Fragment) : Visitor {
    private val activity: FragmentActivity
        get() = originFragment.requireActivity()

    private val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    internal fun dismiss() {
        if (originFragment is DialogFragment) {
            originFragment.dismissAllowingStateLoss()
        }
    }

    override fun buildFragment(clazz: Class<out Fragment>): Fragment {
        return fragmentManager.fragmentFactory.instantiate(activity.applicationContext.classLoader, clazz.canonicalName!!)
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

    override fun createComponent(clazz: Class<*>): ComponentName =
        ComponentName(activity, clazz)

    override fun getPendingIntent(reqCode: Int, flags: Int, intent: Intent): PendingIntent {
        return PendingIntent.getActivity(activity, reqCode, intent, flags)
    }

    override fun end() {
        when (originFragment) {
            is DialogFragment -> dismiss()
            else -> activity.finish()
        }
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        val bundle = Bundle()
        val bundleDataImpl = BundleDataImpl(bundle)
        bundleBody.invoke(bundleDataImpl)
        val intent = Intent()
        intent.putExtras(bundle)
        if (uriData.isNotEmpty()) {
            intent.data = Uri.parse(uriData)
        }
        activity.setResult(
            resultCode,
            Intent().setData(Uri.parse(uriData))
                .putExtras(bundle)
        )
        activity.finish()
    }

    override fun visitActivity(intent: Intent) {
        originFragment.startActivity(intent)
    }

    override fun visitGroup(intents: Array<out Intent>) {
        activity.startActivities(intents)
    }

    override fun visitForResult(reqCode: Int, intent: Intent) {
        originFragment.startActivityForResult(intent, reqCode)
    }

    override fun visitService(intent: Intent) {
        activity.startService(intent)
    }
}