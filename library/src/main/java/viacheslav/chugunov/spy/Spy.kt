package viacheslav.chugunov.spy

import android.content.Context
import viacheslav.chugunov.spy.internal.data.*
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.SpyEventType

class Spy internal constructor(
    private val applicationContext: Context,
    private val notifications: NotificationFactory,
    private val storage: EventStorage
) {

    constructor(applicationContext: Context) : this(
        applicationContext = applicationContext,
        notifications = inject(applicationContext),
        storage = inject(applicationContext)
    )

    fun info(message: String, vararg meta: SpyMeta) =
        log(message, SpyEventType.INFO, *meta)

    fun warning(message: String, vararg meta: SpyMeta) =
        log(message, SpyEventType.WARNING, *meta)

    fun error(message: String, vararg meta: SpyMeta) =
        log(message, SpyEventType.ERROR, *meta)

    private fun log(message: String, type: SpyEventType, vararg meta: SpyMeta) {
        notifications.show(type, message)
        val event = SpyEvent(message, type, *meta)
        storage.addEvent(event)
    }
}