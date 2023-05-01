package viacheslav.chugunov.spy.internal.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.presentation.BaseFragment

internal class SpyEventDetailFragment : BaseFragment(R.layout.spy_res_fragment_spy_event_detail) {

    private lateinit var event: SpyEvent
    override var title = ""

    companion object {
        private const val EXTRA_SPY_EVENT = "spy-event"

        fun newInstance(event: SpyEvent) = SpyEventDetailFragment().apply {
            this.event = event
            this.title = event.message
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event = savedInstanceState?.getSerializable(EXTRA_SPY_EVENT) as? SpyEvent ?: event
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_detail)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = SpyEventDetailAdapter(event)
        val divider = view.findViewById<View>(R.id.recycler_detail_divider)
        divider.isVisible = event.hasMeta
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EXTRA_SPY_EVENT, event)
    }
}