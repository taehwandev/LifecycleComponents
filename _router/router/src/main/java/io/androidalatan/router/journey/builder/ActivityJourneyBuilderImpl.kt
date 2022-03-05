package io.androidalatan.router.journey.builder

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.ActivityJourneyBuilder
import io.androidalatan.router.journey.ActivityJourney

class ActivityJourneyBuilderImpl(
    private val activity: Class<out Activity>,
    private val visitor: Visitor,
    private val bundleData: BundleData = BundleDataImpl(Bundle())
) : ActivityJourneyBuilder {

    @VisibleForTesting
    internal var uri: Uri? = null

    @VisibleForTesting
    internal var intentFlag: Int = 0

    @VisibleForTesting
    internal var intentAction: String = ""

    override fun putData(body: (BundleData) -> Unit): ActivityJourneyBuilder {
        body(bundleData)
        return this
    }

    override fun setAction(body: () -> String): ActivityJourneyBuilder {
        intentAction = body()
        return this
    }

    override fun addFlag(body: () -> Int): ActivityJourneyBuilder {
        intentFlag = intentFlag or body()
        return this
    }

    override fun setData(body: () -> Uri): ActivityJourneyBuilder {
        uri = body()
        return this
    }

    override fun build(): ComponentJourney {
        val intent = Intent().apply {
            component = visitor.createComponent(activity)
            if (intentAction.isNotEmpty()) {
                action = intentAction
            }
            if (uri != null && uri != Uri.EMPTY) {
                data = uri
            }
            this.putExtras(bundleData.rawBundle())
            this.addFlags(intentFlag)
        }
        return ActivityJourney(visitor, intent)
    }
}