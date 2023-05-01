package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.SpyApplication
import viacheslav.chugunov.spy.internal.domain.SpyNavigation
import viacheslav.chugunov.spy.internal.domain.ToolbarController
import viacheslav.chugunov.spy.internal.presentation.customview.ToolbarView
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsListFragment

internal class SpyActivity : AppCompatActivity(), ToolbarController, SpyNavigation {

    private val toolbar by lazy { findViewById<ToolbarView>(R.id.spy_toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spy_res_activity_spy)
        if (savedInstanceState == null) {
            val spyEventsListFragment = SpyEventsListFragment.newInstance()
            toolbar.setCallback(spyEventsListFragment)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, spyEventsListFragment)
                .commit()
        } else toolbar.setCallback(supportFragmentManager.fragments[0] as ToolbarView.Callback)
    }

    override fun onStart() {
        super.onStart()
        if((application as SpyApplication).getIsFirstLaunch()){
            (supportFragmentManager.fragments.getOrNull(0) as SpyEventsListFragment).onAgreeButtonClick()
        }
        toolbar.resetTitleVisibility()
    }

    override fun onStop() {
        super.onStop()
        toolbar.removeTextChangedListener()
    }

    override fun navigate(fragment: Fragment) {
        if(fragment is ToolbarView.Callback) {
            toolbar.setCallback(fragment)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()
        toolbar.resetTitleVisibility()
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
}