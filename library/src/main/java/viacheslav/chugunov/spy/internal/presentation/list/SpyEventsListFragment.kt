package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.room.SpyRoomDatabase
import viacheslav.chugunov.spy.internal.domain.SearchViewVisitor
import viacheslav.chugunov.spy.internal.presentation.BaseFragment
import viacheslav.chugunov.spy.internal.presentation.detail.SpyEventDetailFragment

internal class SpyEventsListFragment() : BaseFragment(R.layout.spy_res_fragment_spy_events_list),
    SpyEventsAdapter.Listener, SearchViewVisitor, DeleteDialogFragment.Listener {

    companion object {
        fun newInstance() = SpyEventsListFragment()
    }

    private lateinit var viewModel: SpyEventsViewModel

    override val showSearch: Boolean = true
    override val showDelete: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SpyEventsViewModel::class.java]
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_list)
        val adapter = SpyEventsAdapter(listener = this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            viewModel.allEventsFlow.collect { events ->
                adapter.setEvents(events)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateSearch("")
    }

    override fun onItemClick(event: SpyEvent) {
        val spyEventDetailFragment = SpyEventDetailFragment.newInstance(event)
        navigate(spyEventDetailFragment)
    }

    override fun onSearchChanged(query: String) {
        viewModel.updateSearch(query)
    }

    override fun onAgreeButtonClick() {
        viewModel.removeAllData()
    }
}