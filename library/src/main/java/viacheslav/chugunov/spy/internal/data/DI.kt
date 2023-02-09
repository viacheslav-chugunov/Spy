package viacheslav.chugunov.spy.internal.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import viacheslav.chugunov.spy.Spy
import kotlin.properties.ReadOnlyProperty

@Suppress("IMPLICIT_CAST_TO_ANY")
internal inline fun <reified T> inject(applicationContext: Context): T = when (T::class) {
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