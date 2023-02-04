package viacheslav.chugunov.spy.internal.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SPY_EVENT")
internal data class SpyEventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("ID")
    val id: Long,
    @ColumnInfo(name = "TIMESTAMP")
    val timestamp: Long,
    @ColumnInfo(name = "MESSAGE")
    val message: String,
    @ColumnInfo(name = "TYPE")
    val type: String
) {

    constructor(timestamp: Long, message: String, type: String) : this(0, timestamp, message, type)

}