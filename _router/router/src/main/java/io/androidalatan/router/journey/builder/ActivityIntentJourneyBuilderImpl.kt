package io.androidalatan.router.journey.builder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.ActivityIntentJourneyBuilder
import io.androidalatan.router.journey.ActivityJourney

class ActivityIntentJourneyBuilderImpl(
    _intent: Intent?,
    private val visitor: Visitor,
    private val bundleData: BundleData = BundleDataImpl(Bundle())
) : ActivityIntentJourneyBuilder {

    internal var intent: Intent? = _intent

    @VisibleForTesting
    internal var uri: Uri? = null

    @VisibleForTesting
    internal var intentFlag: Int = 0

    @VisibleForTesting
    internal var intentAction: String = ""

    @VisibleForTesting
    internal var type: String? = null

    override fun putData(body: (BundleData) -> Unit): ActivityIntentJourneyBuilder {
        body(bundleData)
        return this
    }

    override fun setAction(body: () -> String): ActivityIntentJourneyBuilder {
        intentAction = body()
        return this
    }

    override fun addFlag(body: () -> Int): ActivityIntentJourneyBuilder {
        intentFlag = intentFlag or body()
        return this
    }

    override fun setData(body: () -> Uri): ActivityIntentJourneyBuilder {
        uri = body()
        return this
    }

    override fun setIntent(body: () -> Intent): ActivityIntentJourneyBuilder {
        intent = body()
        return this
    }

    override fun setType(body: () -> String): ActivityIntentJourneyBuilder {
        type = body()
        return this
    }

    override fun build(): ComponentJourney {
        return ActivityJourney(visitor, (intent ?: Intent()).apply {
            if (intentAction.isNotEmpty()) {
                action = intentAction
            }
            this@ActivityIntentJourneyBuilderImpl.type?.let {
                this.type = it
            }
            if (uri != null && uri != Uri.EMPTY) {
                data = uri
            }
            this.putExtras(bundleData.rawBundle())
            this.addFlags(intentFlag)
        })
    }
}