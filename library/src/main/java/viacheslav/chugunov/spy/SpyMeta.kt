package viacheslav.chugunov.spy

import viacheslav.chugunov.spy.internal.room.entity.SpyMetaEntity

data class SpyMeta(private val key: String, private val field: String) {

    internal fun toSpyMetaEntity(eventId: Long): SpyMetaEntity =
        SpyMetaEntity(key, field, eventId)

}