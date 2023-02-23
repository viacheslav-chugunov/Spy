package viacheslav.chugunov.spy.internal.presentation

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.domain.SpyActivityController
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsListFragment

internal class SpyActivity : AppCompatActivity(), SpyActivityController {
    private val tvTitle by lazy { findViewById<TextView>(R.id.title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spy)
        window.statusBarColor = Color.WHITE
        window.navigationBarColor = Color.WHITE
        if (savedInstanceState == null) {
            val spyEventsListFragment = SpyEventsListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, spyEventsListFragment)
                .commit()
        }
    }

    override fun setTitle(title: String) {
        tvTitle.text = title
    }

    override fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}