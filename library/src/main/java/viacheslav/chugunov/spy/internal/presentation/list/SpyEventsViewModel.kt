package viacheslav.chugunov.spy.internal.presentation.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.inject

internal class SpyEventsViewModel private constructor(
    application: Application,
    private val storage: EventStorage
) : AndroidViewModel(application) {
    private val allEventsQuery = MutableStateFlow("")
    val allEventsFlow = storage.getAllEventsFlow().combine(allEventsQuery) { events, query ->
        val trimmedQuery = query.trim().lowercase()
        if (query.isEmpty()) {
            events
        } else {
            events.filter { it.message.lowercase().contains(trimmedQuery) }
        }
    }

    constructor(application: Application) : this(
        application = application,
        storage = inject(application)
    )

    fun removeAllData(){
        viewModelScope.launch(Dispatchers.Main) { storage.removeAllData() }
    }

    fun updateSearch(query: String) {
        viewModelScope.launch(Dispatchers.Main) { allEventsQuery.emit(query) }
    }
}