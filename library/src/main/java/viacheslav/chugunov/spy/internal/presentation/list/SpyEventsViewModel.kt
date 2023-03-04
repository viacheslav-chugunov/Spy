package viacheslav.chugunov.spy.internal.presentation.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.inject
import viacheslav.chugunov.spy.internal.data.ConditionViewTypeAdapter

internal class SpyEventsViewModel private constructor(
    application: Application,
    private val storage: EventStorage,
) : AndroidViewModel(application) {
    private val condition = ConditionViewTypeAdapter()

    private val allEventsQuery = MutableStateFlow("")
    private var filters = MutableStateFlow(condition.map)

    val allEventsFlow =
        combine(storage.getAllEventsFlow(), allEventsQuery, filters) { events, query, map ->
            val trimmedQuery = query.trim().lowercase()
            val resultEvents = events.filter { map[it.spyEventAdapterViewType]!! }
            if (query.isEmpty()) {
                resultEvents
            } else {
                resultEvents.filter { it.message.lowercase().contains(trimmedQuery) }
            }
        }

    constructor(application: Application) : this(
        application = application,
        storage = inject(application)
    )

    fun getMap() = filters.value

    fun setFilter(key: Int, value: Boolean) {
        condition.addCondition(key, value)
        filters = MutableStateFlow(condition.map)
    }

    fun removeAllData() {
        viewModelScope.launch(Dispatchers.IO) { storage.removeAllData() }
    }

    fun updateSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) { allEventsQuery.emit(query) }
    }
}