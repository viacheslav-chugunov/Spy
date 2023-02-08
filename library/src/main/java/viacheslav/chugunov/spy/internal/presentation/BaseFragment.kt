package viacheslav.chugunov.spy.internal.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.internal.domain.Navigation

abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment(), Navigation {
    private var navigation: Navigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as Navigation
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun navigate(fragment: Fragment) {
        navigation?.navigate(fragment)
    }
}