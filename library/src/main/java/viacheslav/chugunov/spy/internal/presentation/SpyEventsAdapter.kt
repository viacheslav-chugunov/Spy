package viacheslav.chugunov.spy.internal.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent

internal class SpyEventsAdapter(
    private val events: List<SpyEvent> = emptyList()
) : RecyclerView.Adapter<SpyEventsAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        R.layout.item_spy_event_info
//        R.layout.item_spy_event_warning
//        R.layout.item_spy_event_error
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = when(holder) {
        is ViewHolder.Info -> TODO("Not yet implemented")
        is ViewHolder.Warning -> TODO("Not yet implemented")
        is ViewHolder.Error -> TODO("Not yet implemented")
    }

    fun setEvents(events: List<SpyEvent>) {
        TODO("Not yet implemented")
    }

    fun removeEvent(events: SpyEvent) {
        TODO("Not yet implemented")
    }

    fun clearAllEvents() {
        TODO("Not yet implemented")
    }

    private object ViewType {
        const val INFO = 1
        const val WARNING = 2
        const val ERROR = 3
    }

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val divider: View = view.findViewById(R.id.divider)
        val date: TextView = view.findViewById(R.id.date)
        val message: TextView = view.findViewById(R.id.message)

        class Info(view: View) : ViewHolder(view)
        class Warning(view: View) : ViewHolder(view)
        class Error(view: View) : ViewHolder(view)
    }
}