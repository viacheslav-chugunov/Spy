package viacheslav.chugunov.spy.internal.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import viacheslav.chugunov.spy.R
import viacheslav.chugunov.spy.internal.domain.DialogController
import viacheslav.chugunov.spy.internal.domain.SearchViewVisitor
import viacheslav.chugunov.spy.internal.domain.SpyActionController
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsListFragment

internal class SpyActivity : AppCompatActivity(), SpyActionController {
    private var isSearchMode: Boolean = false
    private var searchEnabled: Boolean = false

    private val tvTitle by lazy { findViewById<TextView>(R.id.title) }
    private val ivSearch by lazy { findViewById<ImageView>(R.id.action_search) }
    private val etSearch by lazy { findViewById<EditText>(R.id.search_input) }
    private val ivDelete by lazy { findViewById<ImageView>(R.id.action_delete) }
    private val ivFilter by lazy { findViewById<ImageView>(R.id.action_filter) }

    private val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = Unit
        override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) =
            updateSearch(newText?.toString() ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spy_res_activity_spy)
        if (savedInstanceState == null) {
            val spyEventsListFragment = SpyEventsListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, spyEventsListFragment)
                .commit()
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
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT)
        }
        ivDelete.setOnClickListener {
            supportFragmentManager.fragments.getOrNull(0)?.let {
                if (it is DialogController) it.showDeleteDialog()
            }
        }
        ivFilter.setOnClickListener {
            supportFragmentManager.fragments.getOrNull(0)?.let {
                if (it is DialogController) it.showFilterDialog()
            }
        }

        etSearch.addTextChangedListener(searchTextWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        etSearch.removeTextChangedListener(searchTextWatcher)
    }

    override fun setTitle(title: String) {
        tvTitle.isVisible = true
        etSearch.isVisible = false
        tvTitle.text = title
    }

    override fun showSearchAction(show: Boolean) {
        searchEnabled = show
        isSearchMode = false
        ivSearch.isVisible = show
        etSearch.isVisible = false
        if (!show) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
        }
    }

    override fun showDeleteAction(show: Boolean) {
        ivDelete.visibility = if (!show) View.GONE else View.VISIBLE
    }

    override fun showFilterAction(show: Boolean) {
        ivFilter.visibility = if (!show) View.GONE else View.VISIBLE
    }

    override fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (searchEnabled && isSearchMode) {
            isSearchMode = false
            ivSearch.isVisible = true
            tvTitle.isVisible = true
            etSearch.isVisible = false
            ivDelete.isVisible = true
            ivFilter.isVisible = true
            updateSearch()
        } else {
            super.onBackPressed()
        }
    }

    private fun updateSearch(query: String = "") {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment is SearchViewVisitor) {
                fragment.onSearchChanged(query)
            }
        }
    }
}