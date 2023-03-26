package viacheslav.chugunov.spy.internal.presentation.customview

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import viacheslav.chugunov.spy.R

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attribute: AttributeSet? = null,
    defStyleAttr: Int = 0,
    desStyleRes: Int = 0,
) : FrameLayout(context, attribute, defStyleAttr, desStyleRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.spy_res_toolbar_custom, this, true)
        initializeListeners()
    }

    private var callback: Callback? = null

    private var title = context.getString(R.string.spy_res_app_name)

    private var isSearchMode: Boolean = false
    private var searchEnabled: Boolean = false

    private lateinit var tvTitle: TextView
    private lateinit var ivSearch: ImageView
    private lateinit var etSearch: EditText
    private lateinit var ivDelete: ImageView
    private lateinit var ivFilter: ImageView

    private lateinit var searchTextWatcher: TextWatcher

    interface Callback {
        fun showDeleteDialog()
        fun showFilterDialog()
        fun onSearchChanged(query: String)
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun showToolbarActions(show: Boolean) {
        setTitleSettings(title)
        showSearchAction(show)
        showDeleteAction(show)
        showFilterAction(show)
    }

    fun removeTextChangedListener() {
        etSearch.removeTextChangedListener(searchTextWatcher)
    }

    fun backFromEditText(): Boolean {
        if (searchEnabled && isSearchMode) {
            isSearchMode = false
            ivSearch.isVisible = true
            tvTitle.isVisible = true
            etSearch.isVisible = false
            ivDelete.isVisible = true
            ivFilter.isVisible = true
            updateSearch()
            return true
        }
        return false
    }

    private fun initializeListeners() {
        tvTitle = findViewById(R.id.title)
        etSearch = findViewById(R.id.search_input)
        ivFilter = findViewById(R.id.action_filter)
        ivDelete = findViewById(R.id.action_delete)
        ivSearch = findViewById(R.id.action_search)

        searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) =
                updateSearch(newText?.toString() ?: "")
        }
        ivSearch.setOnClickListener {
            isSearchMode = true
            tvTitle.isVisible = false
            etSearch.isVisible = true
            ivSearch.isVisible = false
            ivFilter.isVisible = false
            ivDelete.isVisible = false
            etSearch.setText("")
            etSearch.requestFocus()
            val imm =
                context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT)
        }
        ivDelete.setOnClickListener {
            callback?.showDeleteDialog()
        }
        ivFilter.setOnClickListener {
            callback?.showFilterDialog()
        }
        etSearch.addTextChangedListener(searchTextWatcher)
    }

    private fun updateSearch(query: String = "") {
        callback?.onSearchChanged(query)
    }

    private fun setTitleSettings(title: String) {
        tvTitle.isVisible = true
        etSearch.isVisible = false
        tvTitle.text = title
    }

    private fun showSearchAction(show: Boolean) {
        searchEnabled = show
        isSearchMode = false
        ivSearch.isVisible = show
        etSearch.isVisible = false
        if (!show) {
            val imm =
                context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
        }
    }

    private fun showDeleteAction(show: Boolean) {
        ivDelete.isVisible = show
    }

    private fun showFilterAction(show: Boolean) {
        ivFilter.isVisible = show
    }
}