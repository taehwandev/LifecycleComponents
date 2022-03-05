package io.androidalatan.router.assertion.builder

import android.app.Activity
import android.app.Service
import android.content.Intent
import androidx.fragment.app.Fragment
import io.androidalatan.router.api.journey.builder.ActivityIntentJourneyBuilder
import io.androidalatan.router.api.journey.builder.ActivityJourneyBuilder
import io.androidalatan.router.api.journey.builder.FragmentJourneyBuilder
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import io.androidalatan.router.api.journey.builder.ServiceJourneyBuilder

class MockJourneyBuilder : JourneyBuilder {
    var destinationJourneyBuilder: MockDestinationJourneyBuilder? = null
    var destinationFragmentJourneyBuilder: MockDestinationFragmentJourneyBuilder? = null

    override fun activity(destination: Class<out Activity>): ActivityJourneyBuilder {
        return MockDestinationJourneyBuilder().apply {
            this.destination = destination
            destinationJourneyBuilder = this
        }
    }

    override fun activityIntent(intent: Intent?): ActivityIntentJourneyBuilder {
        return MockDestinationJourneyBuilder().apply {
            this.intent = intent
            destinationJourneyBuilder = this
        }
    }

    override fun service(destination: Class<out Service>): ServiceJourneyBuilder {
        return MockDestinationJourneyBuilder().apply {
            this.destination = destination
            destinationJourneyBuilder = this
        }
    }

    override fun fragment(destination: Class<out Fragment>): FragmentJourneyBuilder {
        return MockDestinationFragmentJourneyBuilder().apply {
            this.destination = destination
            destinationFragmentJourneyBuilder = this
        }
    }
}
