package viacheslav.chugunov.spy.internal.presentation.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.domain.DialogController
import viacheslav.chugunov.spy.internal.domain.DialogListener
import viacheslav.chugunov.spy.internal.domain.SearchViewVisitor
import viacheslav.chugunov.spy.internal.presentation.BaseFragment
import viacheslav.chugunov.spy.internal.presentation.ConditionViewTypeAdapter
import viacheslav.chugunov.spy.internal.presentation.detail.SpyEventDetailFragment

internal class SpyEventsListFragment : BaseFragment(R.layout.spy_res_fragment_spy_events_list),
    SpyEventsAdapter.Listener, SearchViewVisitor,
    DialogListener, DialogController {

    companion object {
        fun newInstance() = SpyEventsListFragment()
    }

    private lateinit var adapter: SpyEventsAdapter

    private lateinit var viewModel: SpyEventsViewModel

    private lateinit var condition: ConditionViewTypeAdapter

    private lateinit var filterDialogFragment: FilterDialogFragment

    override val showSearch: Boolean = true
    override val showDelete: Boolean = true
    override val showFilter: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SpyEventsViewModel::class.java]

        if (savedInstanceState == null) {
            condition = ConditionViewTypeAdapter()
            filterDialogFragment = FilterDialogFragment()
        } else {
            condition = savedInstanceState.getParcelable("condition")!!
            filterDialogFragment = savedInstanceState.getParcelable("filter")!!
        }

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_list)
        adapter = SpyEventsAdapter(listener = this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val index = condition.getAvailableIndex()
        if (index >= 0) onCheckBoxesClicked(index, true)
        else onCheckBoxesClicked(0, false)
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

    override fun onCheckBoxesClicked(index: Int, isChecked: Boolean) {
        condition.addCondition(index, isChecked)
        lifecycleScope.launch {
            viewModel.allEventsFlow.collect { events ->
                adapter.setEvents(events.filter { condition.getAllConditions(it.spyEventAdapterViewType) })
            }
        }
    }

    override fun onAgreeButtonClick() {
        viewModel.removeAllData()
    }

    override fun showDeleteDialog() {
        DeleteDialogFragment().show(childFragmentManager, null)
    }

    override fun showFilterDialog() {
        filterDialogFragment.show(childFragmentManager, null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("filter", filterDialogFragment)
        outState.putParcelable("condition", condition)
    }
}