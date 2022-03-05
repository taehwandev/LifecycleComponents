package io.androidalatan.router.journey.builder

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.builder.ActivityIntentJourneyBuilder
import io.androidalatan.router.api.journey.builder.ActivityJourneyBuilder
import io.androidalatan.router.api.journey.builder.FragmentJourneyBuilder
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import io.androidalatan.router.api.journey.builder.ServiceJourneyBuilder

class JourneyBuilderImpl(
    private val visitor: Visitor,
    private val bundleData: BundleData = BundleDataImpl(Bundle())
) : JourneyBuilder {

    override fun activity(destination: Class<out Activity>): ActivityJourneyBuilder {
        return ActivityJourneyBuilderImpl(
            activity = destination,
            visitor = visitor,
            bundleData = bundleData
        )
    }

    override fun activityIntent(intent: Intent?): ActivityIntentJourneyBuilder {
        return ActivityIntentJourneyBuilderImpl(intent, visitor, bundleData)
    }

    override fun service(destination: Class<out Service>): ServiceJourneyBuilder {
        return ServiceJourneyBuilderImpl(
            service = destination,
            visitor = visitor,
            bundleData = bundleData
        )
    }

    override fun fragment(destination: Class<out Fragment>): FragmentJourneyBuilder {
        return FragmentJourneyBuilderImpl(
            destination = destination,
            visitor = visitor,
            bundleData = bundleData
        )
    }
}