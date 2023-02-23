package viacheslav.chugunov.spy.internal.domain

import androidx.fragment.app.Fragment

internal interface SpyNavigation {
    fun navigate(fragment: Fragment)
}