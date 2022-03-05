package io.androidalatan.router.assertion

import android.app.PendingIntent
import android.content.Intent
import io.androidalatan.router.api.journey.ComponentJourney
import org.mockito.kotlin.mock

class MockComponentJourney : ComponentJourney {
    var endCount = 0
    var visitCount = 0
    var visitCountForResult = 0
    var reqCode = -1
    var rawIntent: Intent? = null

    override fun rawIntent(): Intent {
        return mock<Intent>().apply {
            rawIntent = this
        }
    }

    override fun pendingIntent(reqCode: Int, flags: Int): PendingIntent {
        TODO("this should be with power mock :( ")
    }

    override fun end() {
        endCount++
    }

    override fun visit(): ComponentJourney {
        visitCount++
        return this
    }

    override fun visitForResult(reqCode: Int): ComponentJourney {
        this.reqCode = reqCode
        visitCountForResult++
        return this
    }
}