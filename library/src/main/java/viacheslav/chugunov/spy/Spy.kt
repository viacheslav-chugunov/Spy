package viacheslav.chugunov.spy

import android.content.Context
import viacheslav.chugunov.spy.internal.data.*
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.SpyEventType

class Spy internal constructor(
    private val applicationContext: Context,
    private val notifications: NotificationFactory,
    private val storage: EventStorage,
    private val config:SpyConfig
) {

    constructor(applicationContext: Context, config: SpyConfig) : this(
        applicationContext = applicationContext,
        notifications = inject(applicationContext),
        storage = inject(applicationContext),
        config = config
    )

    constructor(applicationContext: Context):this(
        applicationContext = applicationContext,
        notifications = inject(applicationContext),
        storage = inject(applicationContext),
        config = SpyConfig.SpyConfigBuilder().setInitialMeta(listOf(SpyMeta("a","b"))).build()
    )

    fun info(message: String) =
        log(message, SpyEventType.INFO, config.getInitialMeta())

    fun warning(message: String) =
        log(message, SpyEventType.WARNING, config.getInitialMeta())

    fun error(message: String) =
        log(message, SpyEventType.ERROR, config.getInitialMeta())

    private fun log(message: String, type: SpyEventType, metaList:List<SpyMeta>) {
        notifications.show(type, message)
        val event = SpyEvent(message, type, metaList)
        storage.addEvent(event)
    }
}