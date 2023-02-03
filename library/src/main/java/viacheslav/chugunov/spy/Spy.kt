package viacheslav.chugunov.spy

import android.content.Context
import kotlinx.coroutines.Dispatchers
import viacheslav.chugunov.spy.internal.EventStorage
import viacheslav.chugunov.spy.internal.NotificationFactory
import viacheslav.chugunov.spy.internal.SpyEvent
import viacheslav.chugunov.spy.internal.SpyEventType

class Spy(applicationContext: Context) {
    private val notifications = NotificationFactory(
        applicationContext, "spy-notifications", "Spy notifications")
    private val storage = EventStorage(applicationContext, "spy-database", Dispatchers.IO)

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

}