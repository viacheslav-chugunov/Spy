package viacheslav.chugunov.spy.internal.domain

internal interface ToolbarController {
fun setTitleToolBar(title: String)
fun showDeleteAction(show: Boolean)
fun showFilterAction(show: Boolean)
fun showSearchAction(show: Boolean)
}