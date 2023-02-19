package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.Spy
import viacheslav.chugunov.spy.internal.data.SpyEvent
import viacheslav.chugunov.spy.internal.domain.Navigation

internal class SpyActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spy)
        val spyEventsListFragment = SpyEventsListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, spyEventsListFragment)
            .commit()
    }

    override fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}