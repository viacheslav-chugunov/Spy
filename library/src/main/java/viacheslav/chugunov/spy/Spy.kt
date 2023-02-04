package viacheslav.chugunov.spy

import android.content.Context
import viacheslav.chugunov.spy.internal.data.*
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.SpyEventType

class Spy {
    private val notifications: NotificationFactory by inject()
    private val storage: EventStorage by inject()

    fun info(message: String, vararg meta: SpyMeta) =
        log(message, SpyEventType.INFO, *meta)

    fun warning(message: String, vararg meta: SpyMeta) =
        log(message, SpyEventType.WARNING, *meta)

    fun error(message: String, vararg meta: SpyMeta) =
        log(message, SpyEventType.ERROR, *meta)

    private fun log(message: String, type: SpyEventType, vararg meta: SpyMeta) {
        notifications.show(type, message)
        val event = SpyEvent.from(message, type, *meta)
        storage.addEvent(event)
    }

    companion object {
        internal var applicationContext: Context? = null

        fun install(applicationContext: Context) {
            Spy.applicationContext = applicationContext
        }
    }
}