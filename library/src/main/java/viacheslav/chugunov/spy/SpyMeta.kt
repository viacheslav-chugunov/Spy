package viacheslav.chugunov.spy

import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity
import java.io.Serializable

data class SpyMeta(
    internal val key: String,
    internal val field: String
) : Serializable {

    internal fun toSpyMetaEntity(eventId: Long): SpyMetaEntity =
        SpyMetaEntity(key, field, eventId)

}