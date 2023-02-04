package viacheslav.chugunov.spy.internal.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.presentation.SpyActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class NotificationFactory(
    private val applicationContext: Context,
    private val channelId: String,
    private val channelName: String,
) {

    private val notificationManager: NotificationManager by lazy {
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createChannel()
    }

    fun show(type: SpyEventType, description: String) {
        val intent = Intent(applicationContext, SpyActivity::class.java)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, flags)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(type.name)
            .setSmallIcon(type.iconRes)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(0, notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }
}