package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.Spy
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity

internal class SpyEventDetailFragment(private val event: SpyEvent) : BaseFragment(R.layout.fragment_spy_event_detail){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_detail)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = SpyMetaAdapter(event)
    }
}