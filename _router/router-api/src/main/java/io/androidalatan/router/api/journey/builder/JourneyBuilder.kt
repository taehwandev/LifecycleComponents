package io.androidalatan.router.api.journey.builder

import android.app.Activity
import android.app.Service
import android.content.Intent
import androidx.fragment.app.Fragment

interface JourneyBuilder {
    fun activity(destination: Class<out Activity>): ActivityJourneyBuilder
    fun activityIntent(intent: Intent? = null): ActivityIntentJourneyBuilder
    fun service(destination: Class<out Service>): ServiceJourneyBuilder
    fun fragment(destination: Class<out Fragment>): FragmentJourneyBuilder
}