package viacheslav.chugunov.spy.internal.presentation.customview

import android.text.Editable
import android.text.TextWatcher

internal class TextSearcher(private val textChangedListener: (String) -> Unit) {

    private var state = State(
        searchEnabled = false,
        isSearchMode = false,
        query = "")

    val searchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(text: Editable?) {
            state.query = text.toString()
        }
        override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int)  {
            textChangedListener(newText?.toString() ?: "")
        }
    }

    fun getQuery() = state.query

    fun backFromEditMode(): Boolean {
        return if(state.isInEditMode()) {
            state = state.copy(isSearchMode = false, query = "")
            textChangedListener("")
            true
        } else {
            false
        }
    }

    fun showSearchAction(show: Boolean) {
        state.setSearchEnabled(show)
    }

    fun showSearchMode(show: Boolean) {
        state.setSearchMode(show)
    }

    private data class State(
        private var searchEnabled: Boolean,
        private var isSearchMode: Boolean,
        var query: String){

        fun setSearchEnabled(enabled: Boolean) {
            searchEnabled = enabled
        }

        fun setSearchMode(enabled: Boolean){
            isSearchMode = enabled
        }

        fun isInEditMode() = isSearchMode && searchEnabled
    }
}