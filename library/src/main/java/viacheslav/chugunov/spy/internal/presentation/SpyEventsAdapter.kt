package viacheslav.chugunov.spy.internal.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent

internal class SpyEventsAdapter(
    private var events: List<SpyEvent> = emptyList()
) : RecyclerView.Adapter<SpyEventsAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if(position==1) return ViewType.INFO
        if(position==2) return ViewType.WARNING
        if(position==3) return ViewType.ERROR
        else return ViewType.INFO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            ViewType.INFO -> {
                return ViewHolder.Info(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_spy_event_info, parent, false)
                )
            }
            ViewType.WARNING -> {
                return ViewHolder.Warning(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_spy_event_warning,parent,false)
                )
            }
            else -> {
                return ViewHolder.Error(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_spy_event_error,parent,false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.message.text = events[position].toSpyEventEntity().message;
        holder.date.text = events[position].toSpyEventEntity().timestamp.toString()
        if(position==itemCount) holder.divider.visibility = View.INVISIBLE
        else holder.divider.visibility=View.VISIBLE
    }

    fun setEvents(events: List<SpyEvent>) {
        this.events=events
    }

    fun removeEvent(event: SpyEvent) {
        val newList = mutableListOf<SpyEvent>()
        events.forEach {if(it!=event) newList.add(it)}
        events = newList.toList()
    }

    fun clearAllEvents() {
        events = emptyList()
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