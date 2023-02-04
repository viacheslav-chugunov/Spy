package viacheslav.chugunov.spy.internal.data

import kotlinx.coroutines.Dispatchers
import viacheslav.chugunov.spy.Spy
import kotlin.properties.ReadOnlyProperty

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
internal inline fun <reified T> inject(): ReadOnlyProperty<Any, T> {
    val applicationContext = Spy.applicationContext ?: throw IllegalStateException(
        "Spy was not installed. Use Spy.install(Context) before create an Spy()")
    return ReadOnlyProperty { _, _ ->
        when (T::class) {
            NotificationFactory::class -> {
                NotificationFactory(applicationContext, "spy-notifications", "Spy notifications")
            }
            EventStorage::class -> {
                EventStorage(applicationContext, "spy-database", Dispatchers.IO)
            }
            else -> {
                throw IllegalStateException("Injection for ${T::class.java.simpleName} was not provided")
            }
        } as T
    }
}