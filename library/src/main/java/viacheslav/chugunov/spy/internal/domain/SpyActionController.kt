package viacheslav.chugunov.spy.internal.domain

internal interface SpyActionController : SpyNavigation {
    fun setTitle(title: String)
    fun showSearchAction(show: Boolean)
    fun showDeleteAction(show: Boolean)
}