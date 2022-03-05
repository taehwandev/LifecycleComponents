package io.androidalatan.router.journey

import android.app.PendingIntent
import android.content.Intent
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.ComponentJourney

class JourneyGroup(
    private val visitor: Visitor,
    private vararg val intents: Intent
) : ComponentJourney {

    override fun rawIntent(): Intent {
        return intents[0]
    }

    override fun end() {
        visitor.end()
    }

    override fun visit(): ComponentJourney {
        visitor.visitGroup(intents)
        return this
    }

    override fun visitForResult(reqCode: Int): ComponentJourney {
        visitor.visitGroup(intents)
        return this
    }

    override fun pendingIntent(reqCode: Int, flags: Int): PendingIntent {
        throw UnsupportedOperationException("For Group Intent, we only accept visit()")
    }
}