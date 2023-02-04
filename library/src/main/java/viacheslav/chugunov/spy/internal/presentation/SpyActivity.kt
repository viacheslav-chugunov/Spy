package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlinx.coroutines.*
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
                val recycler = findViewById<RecyclerView>(R.id.recycler)
                recycler.adapter=SpyEventsAdapter()
                recycler.layoutManager=LinearLayoutManager(applicationContext)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}