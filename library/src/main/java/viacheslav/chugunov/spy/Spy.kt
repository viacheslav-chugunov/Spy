package viacheslav.chugunov.spy

import android.content.Context
import viacheslav.chugunov.spy.internal.data.*
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.SpyEventType

class Spy internal constructor(
    private val notifications: NotificationFactory,
    private val storage: EventStorage,
    private val config: SpyConfig
) {

    constructor(applicationContext: Context, config: SpyConfig) : this(
        notifications = inject(applicationContext),
        storage = inject(applicationContext),
        config = config
    )

    constructor(applicationContext: Context):this(
        notifications = inject(applicationContext),
        storage = inject(applicationContext),
        config = SpyConfig.Builder().build()
    )

    fun success(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.SUCCESS, *meta)

    fun info(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.INFO, *meta)

    fun warning(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.WARNING, *meta)

    fun error(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.ERROR, *meta)

    private fun log(message: String, type: SpyEventType, vararg meta: SpyMeta) {
        notifications.show(type, message)
        val metaArray = (meta.toList()+config.getInitialMeta()).toTypedArray()
        val event = SpyEvent(message, type, *metaArray)
        storage.addEvent(event)
    }
}