package io.androidalatan.router.journey

import android.app.PendingIntent
import android.content.Intent
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.ComponentJourney

class ActivityJourney(
    private val visitor: Visitor,
    private val intent: Intent
) : ComponentJourney {

    override fun rawIntent(): Intent {
        return intent
    }

    override fun pendingIntent(reqCode: Int, flags: Int): PendingIntent {
        return visitor.getPendingIntent(reqCode, flags, intent)
    }

    override fun end() {
        visitor.end()
    }

    override fun visit(): ComponentJourney {
        visitor.visitActivity(intent)
        return this
    }

    override fun visitForResult(reqCode: Int): ComponentJourney {
        visitor.visitForResult(reqCode, intent)
        return this
    }
}