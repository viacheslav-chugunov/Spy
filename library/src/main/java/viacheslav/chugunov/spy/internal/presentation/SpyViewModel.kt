package viacheslav.chugunov.spy.internal.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import viacheslav.chugunov.spy.internal.data.EventStorage
import viacheslav.chugunov.spy.internal.data.inject

internal class SpyViewModel(application: Application) : AndroidViewModel(application) {
    private val storage: EventStorage by inject()

    val allEventsFlow = storage.getAllEventsFlow()

}