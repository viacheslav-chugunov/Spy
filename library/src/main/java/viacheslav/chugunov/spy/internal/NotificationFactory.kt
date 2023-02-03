package viacheslav.chugunov.spy.internal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import viacheslav.chugunov.spy.R

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
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(type.name)
            .setSmallIcon(R.drawable.baseline_error_outline_24)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
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