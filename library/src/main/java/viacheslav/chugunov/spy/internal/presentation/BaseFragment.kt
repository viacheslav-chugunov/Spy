package viacheslav.chugunov.spy.internal.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.domain.SpyActionController
import viacheslav.chugunov.spy.internal.domain.SpyNavigation
import viacheslav.chugunov.spy.internal.domain.ToolbarController
import viacheslav.chugunov.spy.internal.presentation.detail.SpyEventDetailFragment
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsListFragment

internal abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment(),
    SpyNavigation {
    private var controller: ToolbarController? = null
    private var navigator: SpyNavigation? = null

    protected open val title: String by lazy { getString(R.string.spy_res_app_name) }
    protected open val showSearch: Boolean = false
    protected open val showDelete: Boolean = false
    protected open val showFilter: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = context as ToolbarController
        navigator = context as SpyNavigation
    }

    override fun onDetach() {
        super.onDetach()
        controller = null
        navigator = null
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun onStart() {
        super.onStart()
        controller?.setTitleToolBar(title)
        controller?.showSearchAction(showSearch)
        controller?.showDeleteAction(showDelete)
        controller?.showFilterAction(showFilter)
    }

    override fun onStop() {
        super.onStop()
        controller?.showDeleteAction(false)
        controller?.showFilterAction(false)
        controller?.showSearchAction(false)
    }

    override fun navigate(fragment: Fragment) {
        navigator?.navigate(fragment)
    }
}