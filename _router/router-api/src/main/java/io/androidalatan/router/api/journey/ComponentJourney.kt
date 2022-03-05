package io.androidalatan.router.api.journey

import android.app.PendingIntent
import android.content.Intent

interface ComponentJourney : Journey {
    fun visitForResult(reqCode: Int): ComponentJourney
    fun pendingIntent(reqCode: Int, flags: Int): PendingIntent
    fun rawIntent(): Intent
    override fun visit(): ComponentJourney
}