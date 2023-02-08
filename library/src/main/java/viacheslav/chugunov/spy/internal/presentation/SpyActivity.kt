package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.SpyEventsListFragment
import viacheslav.chugunov.spy.internal.data.SpyEventDetailFragment

internal class SpyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spy)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frag_container, SpyEventsListFragment())
        ft.commit()
    }
}