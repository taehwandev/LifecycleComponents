package io.androidalatan.router.api

import android.content.Intent
import android.net.Uri

interface DeepLinkJourneyGuidance {
    fun handle(uri: Uri, journeyFinder: JourneyFinder): List<Intent>
}