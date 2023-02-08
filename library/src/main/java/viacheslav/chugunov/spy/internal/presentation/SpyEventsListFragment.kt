package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.Spy.Companion.applicationContext

class SpyEventsListFragment : Fragment(){
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: SpyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_spy_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SpyViewModel::class.java]
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = SpyEventsAdapter(listener = activity as SpyEventsAdapter.Listener)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        coroutineScope.launch {
            viewModel.allEventsFlow.collect { events ->
                adapter.setEvents(events)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}