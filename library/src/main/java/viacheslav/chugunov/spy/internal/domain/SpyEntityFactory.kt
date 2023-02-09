package viacheslav.chugunov.spy.internal.domain

import viacheslav.chugunov.spy.internal.data.room.entity.SpyEventEntity
import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity

internal interface SpyEntityFactory {
    fun createSpyEventEntity(): SpyEventEntity
    fun createSpyMetaEntity(eventId: Long): List<SpyMetaEntity>
}