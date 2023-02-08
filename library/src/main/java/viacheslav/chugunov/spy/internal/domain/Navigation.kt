package viacheslav.chugunov.spy.internal.domain

import androidx.fragment.app.Fragment

interface Navigation {
    fun navigate(fragment: Fragment)
}