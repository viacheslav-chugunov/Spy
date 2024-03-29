package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.domain.SpyNavigation
import viacheslav.chugunov.spy.internal.domain.ToolbarController
import viacheslav.chugunov.spy.internal.presentation.customview.ToolbarView
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsListFragment

internal class SpyActivity : AppCompatActivity(), ToolbarController, SpyNavigation {

    private val toolbar by lazy { findViewById<ToolbarView>(R.id.spy_toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spy_res_activity_spy)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, false)
        if (savedInstanceState == null) {
            val spyEventsListFragment = SpyEventsListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, spyEventsListFragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
    }

    override fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private val fragmentLifecycleCallbacks = object : FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?,
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            toolbar.unregisterCallback()
            when (f) {
                is ToolbarView.ListCallback -> toolbar.registerListCallback(f)
                is ToolbarView.DetailCallback -> toolbar.registerDetailCallback(f)
            }
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            toolbar.resetViewVisibility()
        }
    }

    override fun onBackPressed() {
        if (!toolbar.backFromEditText()) {
            super.onBackPressed()
        }
    }

    override fun setTitleToolBar(title: String) {
        toolbar.setTitle(title)
    }

    override fun showDeleteAction(show: Boolean) {
        toolbar.showDeleteAction(show)
    }

    override fun showFilterAction(show: Boolean) {
        toolbar.showFilterAction(show)
    }

    override fun showSearchAction(show: Boolean) {
        toolbar.showSearchAction(show)
    }

    override fun showShareAction(show: Boolean) {
        toolbar.showShareAction(show)
    }
}