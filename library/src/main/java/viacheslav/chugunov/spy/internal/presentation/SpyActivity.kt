package viacheslav.chugunov.spy.internal.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.domain.SpyNavigation
import viacheslav.chugunov.spy.internal.domain.ToolbarController
import viacheslav.chugunov.spy.internal.presentation.customview.ToolbarView
import viacheslav.chugunov.spy.internal.presentation.detail.SpyEventDetailFragment
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsListFragment

internal class SpyActivity : AppCompatActivity(), ToolbarController, SpyNavigation {

    private val toolbar by lazy { findViewById<ToolbarView>(R.id.spy_toolbar) }
    private var isFirstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spy_res_activity_spy)
        val pref = getSharedPreferences("STORAGE", Context.MODE_PRIVATE)
        isFirstLaunch = pref.getBoolean("isFirstLaunch", true)
        if (savedInstanceState == null) {
            val spyEventsListFragment = SpyEventsListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, spyEventsListFragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isFirstLaunch) {
            (supportFragmentManager.fragments.getOrNull(0) as SpyEventsListFragment).onAgreeButtonClick()
        }
    }

    override fun onStop() {
        super.onStop()
        isFirstLaunch = false
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar.removeTextChangedListener()
        val pref = getSharedPreferences("STORAGE", Context.MODE_PRIVATE)
        pref.edit().putBoolean("isFirstLaunch", isFirstLaunch).apply()
    }

    override fun setDataToolbar(message: String?, callback: ToolbarView.Callback?) {
        toolbar.setTitle(message ?: getString(R.string.spy_res_app_name))
        when (supportFragmentManager.fragments.getOrNull(0)) {
            is SpyEventsListFragment -> {
                toolbar.apply {
                    showToolbarActions(true)
                    setCallback(callback)
                }
            }
            is SpyEventDetailFragment -> {
                hideToolbarActions()
            }
        }
    }

    override fun hideToolbarActions() {
        toolbar.showToolbarActions(false)
    }

    override fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (!toolbar.backFromEditText()) {
            super.onBackPressed()
        }
    }
}