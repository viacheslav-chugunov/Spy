package viacheslav.chugunov.spy.internal

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.internal.room.SpyRoomDatabase
import kotlin.coroutines.CoroutineContext

internal class EventStorage(
    applicationContext: Context,
    databaseName: String,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)
    private val dao = SpyRoomDatabase.getInstance(applicationContext, databaseName).eventDao

    fun addEvent(event: SpyEvent) {
        coroutineScope.launch {
            val eventEntity = event.toSpyEventEntity()
            dao.addEvent(eventEntity)
            val eventId = dao.getLastEvent().id
            val metaEntities = event.toSpyEventMetaEntities(eventId)
            dao.addMeta(metaEntities)
        }
    }


}