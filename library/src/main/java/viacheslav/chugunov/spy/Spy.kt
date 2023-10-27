package viacheslav.chugunov.spy

import android.content.Context
import viacheslav.chugunov.spy.internal.data.*
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.SpyEventType

class Spy internal constructor(
    private val notifications: NotificationFactory,
    private val storage: EventStorage,
    private val config: SpyConfig,
    private val parser: ModelReflectParser
) {

    constructor(applicationContext: Context, config: SpyConfig) : this(
        notifications = inject(applicationContext),
        storage = inject(applicationContext),
        config = config,
        parser = inject(applicationContext)
    )

    constructor(applicationContext: Context):this(
        notifications = inject(applicationContext),
        storage = inject(applicationContext),
        config = SpyConfig.Builder().build(),
        parser = inject(applicationContext),
    )

    init {
        notifications.createChannel(config.isNotificationsImportant)
        if (config.showSpyNotification) notifications.showSpyNotification()
    }

    fun success(model: Any, message: String, vararg meta: SpyMeta) = log(model, message, SpyEventType.SUCCESS, *meta)

    fun success(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.SUCCESS, *meta)

    fun success(message: String, meta: Collection<SpyMeta>) = success(message, *meta.toTypedArray())

    fun info(model: Any, message: String, vararg meta: SpyMeta) = log(model, message, SpyEventType.INFO, *meta)

    fun info(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.INFO, *meta)

    fun info(message: String, meta: Collection<SpyMeta>) = info(message, *meta.toTypedArray())

    fun warning(model: Any, message: String, vararg meta: SpyMeta) = log(model, message, SpyEventType.WARNING, *meta)

    fun warning(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.WARNING, *meta)

    fun warning(message: String, meta: Collection<SpyMeta>) = warning(message, *meta.toTypedArray())

    fun error(model: Any, message: String, vararg meta: SpyMeta) = log(model, message, SpyEventType.ERROR, *meta)

    fun error(message: String, vararg meta: SpyMeta) = log(message, SpyEventType.ERROR, *meta)

    fun error(message: String, meta: Collection<SpyMeta>) = error(message, *meta.toTypedArray())

    fun error(error: Throwable, vararg meta: SpyMeta) = error(error.toString(), *meta)

    fun error(error: Throwable, meta: Collection<SpyMeta>) = error(error, *meta.toTypedArray())

    private fun log(message: String, type: SpyEventType, vararg meta: SpyMeta) {
        notifications.showEventNotification(type, message)
        val metaArray = (config.initialMeta+meta.toList()).toTypedArray()
        val event = SpyEvent(message, type, *metaArray)
        storage.addEvent(event)
    }

    private fun log(model: Any, message: String, type: SpyEventType, vararg meta: SpyMeta) {
        val classInfoSpyMeta = parser.getFieldsClassInfo(model).map { SpyMeta(it.key, it.value) }
        val newMeta = (meta.toList()+classInfoSpyMeta).toTypedArray()
        log(message, type, *newMeta)
    }
}