package viacheslav.chugunov.spy

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import viacheslav.chugunov.spy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val spy: Spy by lazy { Spy(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.infoNotification.setOnClickListener {
            spy.info("Test info notification", SpyMeta("version","123"), SpyMeta("second","sssecond"),SpyMeta("third","ttthird"))
        }
        binding.warningNotification.setOnClickListener {
            spy.warning("Test warning notification",SpyMeta("version","123"), SpyMeta("ver","dwa"),SpyMeta("www","aaa"))
        }
        binding.errorNotification.setOnClickListener {
            spy.error("Test error notification",SpyMeta("version","123"), SpyMeta("ver","dwa"),SpyMeta("www","aaa"))
        }
    }
}