package viacheslav.chugunov.spy.internal.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import viacheslav.chugunov.spy.internal.data.room.entity.SpyEventEntity
import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity

@Dao
internal interface SpyEventDao {

    @Query("SELECT * FROM SPY_EVENT ORDER BY TIMESTAMP DESC")
    fun getAllEventsFlow(): Flow<List<SpyEventEntity>>

    @Query("SELECT * FROM SPY_META_ENTITY")
    fun getAllMetaFlow(): Flow<List<SpyMetaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: SpyEventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeta(meta: List<SpyMetaEntity>)

    @Query("SELECT * FROM SPY_EVENT ORDER BY ID DESC LIMIT 1")
    suspend fun getLastEvent(): SpyEventEntity
}