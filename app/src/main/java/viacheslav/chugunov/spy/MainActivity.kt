package viacheslav.chugunov.spy

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import viacheslav.chugunov.spy.databinding.ActivityMainBinding
import viacheslav.chugunov.spy.internal.data.SpyConfig

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val spy: Spy by lazy { Spy(applicationContext)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.successNotification.setOnClickListener {
            spy.success("Test success notification")
        }
        binding.infoNotification.setOnClickListener {
            spy.info("Test info notification")
        }
        binding.warningNotification.setOnClickListener {
            spy.warning("Test warning notification")
        }
        binding.errorNotification.setOnClickListener {
            spy.error("Test error notification")
        }
    }
}