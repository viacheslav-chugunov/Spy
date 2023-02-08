package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.SpyEventsListFragment
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.data.SpyEventDetailFragment

internal class SpyActivity : AppCompatActivity(), SpyEventsAdapter.Listener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spy)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frag_container, SpyEventsListFragment())
        ft.commit()
    }
    override fun onItemClick(position: Int, event:SpyEvent) {
        val fragment = SpyEventDetailFragment()
        setFragment(fragment)
    }

    fun setFragment(fragment:Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frag_container, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}