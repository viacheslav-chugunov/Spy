package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.R

internal class SpyActivity : AppCompatActivity() {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: SpyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spy)
        viewModel = ViewModelProvider(this)[SpyViewModel::class.java]
        Toast.makeText(this, "CREATE", Toast.LENGTH_SHORT).show()
        coroutineScope.launch {
            viewModel.allEventsFlow.collect { events ->
                // TODO update spy events into adapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}