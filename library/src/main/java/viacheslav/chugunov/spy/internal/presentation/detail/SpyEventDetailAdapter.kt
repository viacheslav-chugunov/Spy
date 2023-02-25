package viacheslav.chugunov.spy.internal.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R

internal class SpyEventDetailAdapter(
    private val binder: Binder
) : RecyclerView.Adapter<SpyEventDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.spy_res_item_detail, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binder.bindSpyMetaViewHolder(holder, position)
    }

    override fun getItemCount(): Int = binder.spyMetaAdapterItemCount

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val root: View = view.findViewById(R.id.ll_root)
        val key: TextView = view.findViewById(R.id.tv_key_detail)
        val field: TextView = view.findViewById(R.id.tv_field_detail)
    }

    interface Binder {
        val spyMetaAdapterItemCount: Int
        fun bindSpyMetaViewHolder(holder: ViewHolder, position: Int)
    }
}