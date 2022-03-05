package io.androidalatan.router.api

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import androidx.fragment.app.Fragment
import io.androidalatan.bundle.api.BundleData

interface Visitor {
    fun createComponent(clazz: Class<*>): ComponentName
    fun getPendingIntent(reqCode: Int, flags: Int, intent: Intent): PendingIntent
    fun end()
    fun end(resultCode: Int, uriData: String = "", bundleBody: (BundleData) -> Unit = {})
    fun visitActivity(intent: Intent)
    fun visitService(intent: Intent)
    fun visitForResult(reqCode: Int, intent: Intent)
    fun visitFragment(containerId: Int, fragment: Fragment)
    fun replaceFragment(containerId: Int, fragment: Fragment)
    fun visitGroup(intents: Array<out Intent>)
    fun buildFragment(clazz: Class<out Fragment>): Fragment
}