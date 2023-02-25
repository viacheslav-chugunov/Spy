package viacheslav.chugunov.spy.internal.domain

internal interface SpyActivityController : SpyNavigation {
    fun setTitle(title: String)
    fun showSearchAction(show: Boolean)
}