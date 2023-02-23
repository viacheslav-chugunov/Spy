package viacheslav.chugunov.spy.internal.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.domain.SpyActivityController
import viacheslav.chugunov.spy.internal.domain.SpyNavigation

abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment(), SpyNavigation {
    private var controller: SpyActivityController? = null
    protected open val title: String by lazy { getString(R.string.spy_res_app_name) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = context as SpyActivityController
    }

    override fun onDetach() {
        super.onDetach()
        controller = null
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun onStart() {
        super.onStart()
        controller?.setTitle(title)
    }

    override fun navigate(fragment: Fragment) {
        controller?.navigate(fragment)
    }
}