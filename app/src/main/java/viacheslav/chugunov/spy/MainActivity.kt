package viacheslav.chugunov.spy

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import viacheslav.chugunov.spy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),1)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spy = Spy(applicationContext)
        binding.showNotification.setOnClickListener {
            spy.info("my txt")
        }
    }
}