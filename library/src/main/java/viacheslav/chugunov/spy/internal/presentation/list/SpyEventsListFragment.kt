package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.domain.DialogListener
import viacheslav.chugunov.spy.internal.presentation.BaseFragment
import viacheslav.chugunov.spy.internal.presentation.customview.ToolbarView
import viacheslav.chugunov.spy.internal.presentation.detail.SpyEventDetailFragment

internal class SpyEventsListFragment : BaseFragment(R.layout.spy_res_fragment_spy_events_list),
    SpyEventsAdapter.Listener, ToolbarView.Callback, DialogListener {

    companion object {
        fun newInstance() = SpyEventsListFragment()
    }

    override val showDelete = true
    override val showFilter = true
    override val showSearch = true

    private lateinit var adapter: SpyEventsAdapter

    private val viewModel: SpyEventsViewModel by lazy { ViewModelProvider(this)[SpyEventsViewModel::class.java] }

    private lateinit var tvNotEvents: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvNotEvents = view.findViewById(R.id.tv_not_events_list)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_list)
        adapter = SpyEventsAdapter(listener = this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            viewModel.allEventsFlow.collect { events ->
                adapter.setEvents(events)
                tvNotEvents.isVisible = events.isEmpty()
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

    override fun onCheckBoxesClicked(index: Int, isChecked: Boolean) {
        viewModel.setFilter(index, isChecked)
        lifecycleScope.launch {
            viewModel.allEventsFlow.collect { events ->
                adapter.setEvents(events)
                tvNotEvents.isVisible = events.isEmpty()
            }
        }
    }

    override fun onAgreeButtonClick() {
        viewModel.removeAllData()
    }

    override fun provideFilters(): Map<Int, Boolean> = viewModel.getMap()

    override fun showDeleteDialog() {
        DeleteDialogFragment().show(childFragmentManager, null)
    }

    override fun showFilterDialog() {
        FilterDialogFragment().show(childFragmentManager, null)
    }

    override fun onSearchChanged(query: String) {
        viewModel.updateSearch(query)
    }
}