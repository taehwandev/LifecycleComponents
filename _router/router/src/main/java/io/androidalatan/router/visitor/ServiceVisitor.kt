package io.androidalatan.router.visitor

import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import androidx.fragment.app.Fragment
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor

class ServiceVisitor(private val service: Service) : Visitor {
    private val randomReqNumber by lazy {
        (Math.random() * 1000).toInt()
    }

    override fun createComponent(clazz: Class<*>): ComponentName {
        return ComponentName(service, clazz)
    }

    override fun getPendingIntent(reqCode: Int, flags: Int, intent: Intent): PendingIntent {
        return PendingIntent.getActivity(service, reqCode, intent, flags)
    }

    override fun end() {
        service.stopSelf()
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        service.stopSelf()
    }

    override fun visitActivity(intent: Intent) {
        try {
            PendingIntent.getActivity(service, randomReqNumber, Intent(intent)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_UPDATE_CURRENT)
                .send()
        } catch (e: PendingIntent.CanceledException) {
            // do nothing
        }
    }

    override fun visitGroup(intents: Array<out Intent>) {
        try {
            PendingIntent.getActivities(service, randomReqNumber, intents.map { intent ->
                Intent(intent)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
                .toTypedArray(), PendingIntent.FLAG_UPDATE_CURRENT)
                .send()
        } catch (e: PendingIntent.CanceledException) {
            // do nothing
        }
    }

    override fun buildFragment(clazz: Class<out Fragment>) =
        throw UnsupportedOperationException("Building Fragment by Service won't work")

    override fun visitForResult(reqCode: Int, intent: Intent) {
        try {
            PendingIntent.getActivity(
                service, reqCode, Intent(intent)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_UPDATE_CURRENT
            )
                .send()
        } catch (e: PendingIntent.CanceledException) {
            // do nothing
        }
    }

    override fun visitFragment(containerId: Int, fragment: Fragment) =
        throw UnsupportedOperationException("visitFragment() is not a business of Service")

    override fun replaceFragment(containerId: Int, fragment: Fragment) =
        throw UnsupportedOperationException("replaceFragment() is not a business of Service")

    override fun visitService(intent: Intent) {
        try {
            PendingIntent.getService(service, randomReqNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                .send()
        } catch (e: PendingIntent.CanceledException) {
            // do nothing
        }
    }
}