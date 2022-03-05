package io.androidalatan.router.journey.builder

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.ServiceJourneyBuilder
import io.androidalatan.router.journey.ServiceJourney

class ServiceJourneyBuilderImpl(
    private val service: Class<out Service>,
    private val visitor: Visitor,
    private val bundleData: BundleData = BundleDataImpl(Bundle())
) : ServiceJourneyBuilder {

    @VisibleForTesting
    internal var uri: Uri? = null

    @VisibleForTesting
    internal var intentFlag: Int = 0

    @VisibleForTesting
    internal var intentAction: String = ""

    override fun putData(body: (BundleData) -> Unit): ServiceJourneyBuilder {
        body(bundleData)
        return this
    }

    override fun setAction(body: () -> String): ServiceJourneyBuilder {
        intentAction = body()
        return this
    }

    override fun setData(body: () -> Uri): ServiceJourneyBuilder {
        uri = body()
        return this
    }

    override fun build(): ComponentJourney {
        val intent = Intent().apply {
            component = visitor.createComponent(service)
            if (intentAction.isNotEmpty()) {
                action = intentAction
            }
            if (uri != null && uri != Uri.EMPTY) {
                data = uri
            }
            this.putExtras(bundleData.rawBundle())
            this.addFlags(intentFlag)
        }
        return ServiceJourney(visitor, intent)
    }
}