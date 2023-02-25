package viacheslav.chugunov.spy.internal.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent
import java.util.*

internal class SpyEventsAdapter(
    events: List<Item> = emptyList(),
    private val listener: Listener
) : RecyclerView.Adapter<SpyEventsAdapter.ViewHolder>() {
    private val events = events.toMutableList()

    interface Item {
        val spyEventAdapterViewType: Int
        fun bindSpyEventViewHolder(holder: ViewHolder, showDivider: Boolean, listener: Listener)
    }

    interface Listener {
        fun onItemClick(event: SpyEvent)
    }

    override fun getItemViewType(position: Int): Int = events[position].spyEventAdapterViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = when (viewType) {
            ViewType.SUCCESS -> R.layout.spy_res_item_spy_event_success
            ViewType.INFO -> R.layout.spy_res_item_spy_event_info
            ViewType.WARNING -> R.layout.spy_res_item_spy_event_warning
            ViewType.ERROR -> R.layout.spy_res_item_spy_event_error
            else -> throw IllegalStateException("Provided unexpected viewType=$viewType")
        }
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val showDivider = position < itemCount - 1
        events[position].bindSpyEventViewHolder(holder, showDivider, listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(events: List<Item>){
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    fun removeEvent(event: Item) {
        val index = this.events.indexOf(event)
        this.events.remove(event)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAllEvents() {
        events.clear()
        notifyDataSetChanged()
    }

    object ViewType {
        const val SUCCESS = 1
        const val INFO = 2
        const val WARNING = 3
        const val ERROR = 4
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val divider: View = view.findViewById(R.id.divider)
        val date: TextView = view.findViewById(R.id.date)
        val message: TextView = view.findViewById(R.id.message)
    }
}