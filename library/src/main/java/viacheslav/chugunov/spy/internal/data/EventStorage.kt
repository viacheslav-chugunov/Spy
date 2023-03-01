package viacheslav.chugunov.spy.internal.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import viacheslav.chugunov.spy.internal.data.room.SpyRoomDatabase
import viacheslav.chugunov.spy.internal.domain.SpyEntityFactory
import kotlin.coroutines.CoroutineContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class EventStorage(
    applicationContext: Context,
    databaseName: String,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)
    private val dao = SpyRoomDatabase.getInstance(applicationContext, databaseName).eventDao

    fun addEvent(event: SpyEntityFactory) {
        coroutineScope.launch {
            val eventEntity = event.createSpyEventEntity()
            dao.addEvent(eventEntity)
            val eventId = dao.getLastEvent().id
            val metaEntities = event.createSpyMetaEntity(eventId)
            dao.addMeta(metaEntities)
        }
    }

    fun getAllEventsFlow(): Flow<List<SpyEvent>> {
        val flow = MutableStateFlow<List<SpyEvent>>(emptyList())
        coroutineScope.launch {
            val eventEntitiesFlow = dao.getAllEventsFlow()
            val metaEntitiesFlow = dao.getAllMetaFlow()
            combine(eventEntitiesFlow, metaEntitiesFlow) { eventEntities, metaEntities ->
                eventEntities.map { SpyEvent(it, metaEntities) }
            }.collect { events ->
                flow.emit(events)
            }
        }
        return flow
    }

    fun removeAllData(){
        coroutineScope.launch {
            dao.removeAllData()
        }
    }
}