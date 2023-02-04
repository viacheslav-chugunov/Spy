package viacheslav.chugunov.spy

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import viacheslav.chugunov.spy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val spy: Spy = Spy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.infoNotification.setOnClickListener {
            spy.info("Test info notification")
        }
        binding.warningNotification.setOnClickListener {
            spy.warning("Test warning notification")
        }
        binding.errorNotification.setOnClickListener {
            spy.error("Test error notificatio")
        }
    }
}