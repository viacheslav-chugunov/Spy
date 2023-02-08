package viacheslav.chugunov.spy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.Spy.Companion.applicationContext
import viacheslav.chugunov.spy.internal.data.SpyEventDetailFragment
import viacheslav.chugunov.spy.internal.presentation.SpyEventsAdapter
import viacheslav.chugunov.spy.internal.presentation.SpyViewModel

class SpyEventsListFragment : Fragment(), SpyEventsAdapter.Listener {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: SpyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this)[SpyViewModel::class.java]
        return inflater.inflate(R.layout.fragment_spy_events_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = SpyEventsAdapter()
        recycler.adapter=adapter
        adapter.setListenerrr(this)
        recycler.layoutManager= LinearLayoutManager(applicationContext)
        coroutineScope.launch {
            viewModel.allEventsFlow.collect { events ->
                adapter.setEvents(events)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onItemClick(position: Int) {
        val fragment = SpyEventDetailFragment()
        if(activity!=null) {
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.frag_container, fragment)
            ft.addToBackStack(null)
            ft.commit()
        }
    }
}