package viacheslav.chugunov.spy.internal.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.presentation.BaseFragment

internal class SpyEventDetailFragment : BaseFragment(R.layout.fragment_spy_event_detail) {
    private lateinit var event: SpyEvent

    companion object {
        private const val EXTRA_SPY_EVENT = "spy-event"

        fun newInstance(event: SpyEvent) = SpyEventDetailFragment().apply {
            this.event = event
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event = savedInstanceState?.getSerializable(EXTRA_SPY_EVENT) as? SpyEvent ?: event
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_detail)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = SpyEventDetailAdapter(event)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EXTRA_SPY_EVENT, event)
    }
}