package viacheslav.chugunov.spy.internal.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SPY_META_ENTITY")
internal data class SpyMetaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Long,
    @ColumnInfo(name = "KEY")
    val key: String,
    @ColumnInfo(name = "FIELD")
    val field: String,
    @ColumnInfo("EVENT_ID")
    val eventId: Long
) {

    constructor(key: String, field: String, eventId: Long) : this(0, key, field, eventId)

}