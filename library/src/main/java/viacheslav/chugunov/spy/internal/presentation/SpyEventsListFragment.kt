package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.Spy.Companion.applicationContext
import viacheslav.chugunov.spy.internal.data.SpyEvent

internal class SpyEventsListFragment : BaseFragment(R.layout.fragment_spy_events_list), SpyEventsAdapter.Listener{
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: SpyViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineScope = CoroutineScope(Dispatchers.Main)
        viewModel = ViewModelProvider(this)[SpyViewModel::class.java]
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = SpyEventsAdapter(listener = this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext)
            coroutineScope.launch {
                viewModel.allEventsFlow.collect { events ->
                    adapter.setEvents(events)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
    }

    override fun onItemClick(position: Int, event:SpyEvent) {
        val spyEventDetailFragment = SpyEventDetailFragment(event)
        navigate(spyEventDetailFragment)
    }
}