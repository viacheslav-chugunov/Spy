package viacheslav.chugunov.spy.internal.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.data.SpyEvent

internal class SpyMetaAdapter(event: SpyEvent) : RecyclerView.Adapter<SpyMetaAdapter.ViewHolder>() {
    private val meta = event.createSpyMetaEntity(0)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.tv_key_detail.text = meta[position].key
        holder.tv_field_detail.text = meta[position].field
    }

    override fun getItemCount(): Int = meta.size

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val tv_key_detail:TextView = view.findViewById(R.id.tv_key_detail)
        val tv_field_detail:TextView = view.findViewById(R.id.tv_field_detail)
    }
}