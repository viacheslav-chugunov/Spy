package viacheslav.chugunov.spy.internal.data

import androidx.core.view.isVisible
import viacheslav.chugunov.spy.SpyMeta
import viacheslav.chugunov.spy.internal.data.room.entity.SpyEventEntity
import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity
import viacheslav.chugunov.spy.internal.presentation.SpyEventsAdapter
import java.text.SimpleDateFormat
import java.util.*

internal data class SpyEvent(
    private val timestamp: Long,
    private val message: String,
    private val type: SpyEventType,
    private val meta: List<SpyMeta>
) {

    fun bindSpyEventViewHolder(holder: SpyEventsAdapter.ViewHolder, showDivider: Boolean) {
        holder.message.text = message
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH)
        val date = Date(timestamp)
        holder.date.text = formatter.format(date)
        holder.divider.isVisible = showDivider
    }

    val spyEventAdapterViewType: Int
        get() = when(type) {
            SpyEventType.INFO -> SpyEventsAdapter.ViewType.INFO
            SpyEventType.WARNING -> SpyEventsAdapter.ViewType.WARNING
            SpyEventType.ERROR -> SpyEventsAdapter.ViewType.ERROR
        }

    fun toSpyEventEntity(): SpyEventEntity =
        SpyEventEntity(timestamp, message, type.name)

    fun toSpyEventMetaEntities(eventId: Long): List<SpyMetaEntity> =
        meta.map { it.toSpyMetaEntity(eventId) }

    companion object {
        fun from(message: String, type: SpyEventType, vararg meta: SpyMeta): SpyEvent = SpyEvent(
            timestamp = System.currentTimeMillis(),
            message = message,
            type = type,
            meta = meta.toList()
        )

        fun from(event: SpyEventEntity, meta: List<SpyMetaEntity>): SpyEvent = SpyEvent(
            timestamp = event.timestamp,
            message = event.message,
            type = SpyEventType.from(event.type),
            meta = meta
                .filter { it.eventId == event.id }
                .map { SpyMeta(key = it.key, field = it.field)
            }
        )
    }
}