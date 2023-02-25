package viacheslav.chugunov.spy.internal.domain

internal interface SearchViewVisitor {
    fun onSearchChanged(query: String)
}