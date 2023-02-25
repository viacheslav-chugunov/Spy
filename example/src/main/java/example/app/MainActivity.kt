package example.app

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import viacheslav.chugunov.spy.Spy
import viacheslav.chugunov.spy.SpyMeta
import viacheslav.chugunov.spy.SpyConfig
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val spy: Spy by lazy {
        val locale = Locale.getDefault()
        val time = SimpleDateFormat("dd.MM.yy HH:mm:ss", locale).format(Date(System.currentTimeMillis()))
        val config = SpyConfig.Builder()
            .setInitialMeta(
                SpyMeta("manufacturer", Build.MANUFACTURER),
                SpyMeta("model", Build.MODEL),
                SpyMeta("brand", Build.BRAND),
                SpyMeta("language", locale.language),
                SpyMeta("time", time)
            )
            .showOpenSpyNotification(true)
            .isNotificationsImportant(true)
            .build()
        Spy(applicationContext, config)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1)
        }
        setContentView(R.layout.activity_main)
        val btnSuccessNotification = findViewById<Button>(R.id.btn_success_notification)
        val btnInfoNotification = findViewById<Button>(R.id.btn_info_notification)
        val btnWarningNotification = findViewById<Button>(R.id.btn_warning_notification)
        val btnErrorNotification = findViewById<Button>(R.id.btn_error_notification)
        spy
        btnSuccessNotification.setOnClickListener {
            spy.success("Test success notification")
        }
        btnInfoNotification.setOnClickListener {
            spy.info("Test info notification")
        }
        btnWarningNotification.setOnClickListener {
            spy.warning("Test warning notification")
        }
        btnErrorNotification.setOnClickListener {
            spy.error("Test error notification")
        }
    }
}