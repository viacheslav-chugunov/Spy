package viacheslav.chugunov.spy.internal.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.presentation.SpyActivity

internal class NotificationFactory(
    private val applicationContext: Context,
    private val channelId: String,
    private val channelName: String,
) {

    private val notificationManager: NotificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showEventNotification(type: SpyEventType, description: String) =
        showNotification(
            title = type.name,
            description = description,
            iconRes = type.iconRes,
            priority = NotificationCompat.PRIORITY_MAX,
            autoCancel = true,
            ongoing = false,
            notificationId = 1
        )

    fun showSpyNotification() =
        showNotification(
            title = applicationContext.getString(R.string.spy_res_spy_notification_title),
            description = applicationContext.getString(R.string.spy_res_spy_notification_description),
            iconRes = R.drawable.ic_open_spy,
            priority = NotificationCompat.PRIORITY_MIN,
            autoCancel = false,
            ongoing = true,
            notificationId = 0
        )

    private fun showNotification(
        title: String,
        description: String,
        @DrawableRes iconRes: Int,
        priority: Int,
        autoCancel: Boolean,
        ongoing: Boolean,
        notificationId: Int,
    ) {
        val intent = Intent(applicationContext, SpyActivity::class.java)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, flags)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setSmallIcon(iconRes)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setPriority(priority)
            .setAutoCancel(autoCancel)
            .setOngoing(ongoing)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    fun createChannel(isChannelImportant: Boolean) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val importance = if (isChannelImportant) NotificationManager.IMPORTANCE_HIGH else
            NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }
}