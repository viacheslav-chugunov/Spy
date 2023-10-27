package viacheslav.chugunov.spy.internal.presentation.customview

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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

internal class ToolbarView @JvmOverloads constructor(
    context: Context,
    attribute: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attribute, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.spy_res_toolbar_custom, this, true)
        initializeListeners()
    }

    private var listCallback: ListCallback? = null
    private var detailCallback: DetailCallback? = null

    private var textSearcher: TextSearcher? = null

    private lateinit var tvTitle: TextView
    private lateinit var ivSearch: ImageView
    private lateinit var etSearch: EditText
    private lateinit var ivDelete: ImageView
    private lateinit var ivFilter: ImageView
    private lateinit var ivShare: ImageView

    interface ListCallback {
        fun showDeleteDialog()
        fun showFilterDialog()
        fun onSearchChanged(query: String)
    }

    interface DetailCallback {
        fun requestShare()
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun backFromEditText(): Boolean =
        if (textSearcher?.backFromEditMode()==true) {
            etSearch.setText("")
            resetViewVisibility()
            true
        } else {
            false
        }

    private fun initializeListeners() {
        tvTitle = findViewById(R.id.title)
        etSearch = findViewById(R.id.search_input)
        ivFilter = findViewById(R.id.action_filter)
        ivDelete = findViewById(R.id.action_delete)
        ivSearch = findViewById(R.id.action_search)
        ivShare = findViewById(R.id.action_share)

        ivSearch.setOnClickListener {
            onIvSearchClicked()
        }
        ivDelete.setOnClickListener {
             listCallback?.showDeleteDialog()
        }
        ivFilter.setOnClickListener {
            listCallback?.showFilterDialog()
        }
        ivShare.setOnClickListener {
            detailCallback?.requestShare()
        }
    }

    private fun onIvSearchClicked() {
        textSearcher?.showSearchMode(true)
        tvTitle.isVisible = false
        etSearch.isVisible = true
        ivSearch.isVisible = false
        ivFilter.isVisible = false
        ivDelete.isVisible = false
        etSearch.setText(textSearcher!!.getQuery())
        etSearch.requestFocus()
        val imm =
            context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun updateSearch(query: String = "") = listCallback?.onSearchChanged(query)


    fun registerListCallback(listCallback: ListCallback) {
        this.listCallback = listCallback
        textSearcher = TextSearcher(::updateSearch)
        etSearch.addTextChangedListener(textSearcher?.searchTextWatcher)
        etSearch.setText("")
        resetViewVisibility()
    }

    fun registerDetailCallback(detailCallback: DetailCallback) {
        this.detailCallback = detailCallback
    }

    fun unregisterCallback() {
        etSearch.removeTextChangedListener(textSearcher?.searchTextWatcher)
        etSearch.setText("")
        textSearcher = null
        resetViewVisibility()
    }

    fun resetViewVisibility() {
        tvTitle.isVisible = textSearcher?.getQuery()?.isEmpty() ?: true
        etSearch.isVisible = textSearcher?.getQuery()?.isNotEmpty() ?: false
        ivSearch.isVisible = textSearcher?.getQuery()?.isEmpty() ?: false
        ivFilter.isVisible = textSearcher?.getQuery()?.isEmpty() ?: false
        ivDelete.isVisible = textSearcher?.getQuery()?.isEmpty() ?: false
    }

    fun showSearchAction(show: Boolean) {
        textSearcher?.showSearchAction(show)
        ivSearch.isVisible = show
        etSearch.isVisible = false
        if (!show) {
            val imm =
                context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etSearch.windowToken, 0)
        }
    }

    fun showDeleteAction(show: Boolean) {
        ivDelete.isVisible = show
    }

    fun showFilterAction(show: Boolean) {
        ivFilter.isVisible = show
    }

    fun showShareAction(show: Boolean) {
        ivShare.isVisible = show
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putString("query", textSearcher?.getQuery())
        bundle.putParcelable("parcelableState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        val query = bundle.getString("query")
        if(!query.isNullOrBlank()){
            onIvSearchClicked()
        }
        super.onRestoreInstanceState(bundle.getParcelable("parcelableState"))
    }
}