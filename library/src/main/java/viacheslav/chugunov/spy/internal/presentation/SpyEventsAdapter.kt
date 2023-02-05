package viacheslav.chugunov.spy.internal.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent
import java.text.SimpleDateFormat
import java.util.*

internal class SpyEventsAdapter(
     events: List<SpyEvent> = emptyList()
) : RecyclerView.Adapter<SpyEventsAdapter.ViewHolder>() {

    private val events = events.toMutableList()
    override fun getItemViewType(position: Int): Int {
        return events[position].getType()
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
        holder.message.text = events[position].toSpyEventEntity().message
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(Date(events[position].toSpyEventEntity().timestamp))
        holder.date.text=dateFormat
        if(position==itemCount-1) holder.divider.visibility = View.INVISIBLE
        else holder.divider.visibility=View.VISIBLE
    }

    fun setEvents(events: List<SpyEvent>){
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    fun removeEvent(event: SpyEvent) {
        val index = this.events.indexOf(event)
        this.events.remove(event)
        notifyItemRemoved(index)
    }

    fun clearAllEvents() {
        events.clear()
        notifyDataSetChanged()
    }

    object ViewType {
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