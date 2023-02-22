package viacheslav.chugunov.spy.internal.data

import androidx.core.view.isVisible
import viacheslav.chugunov.spy.SpyMeta
import viacheslav.chugunov.spy.internal.data.room.entity.SpyEventEntity
import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity
import viacheslav.chugunov.spy.internal.domain.SpyEntityFactory
import viacheslav.chugunov.spy.internal.presentation.detail.SpyEventDetailAdapter
import viacheslav.chugunov.spy.internal.presentation.list.SpyEventsAdapter
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

internal data class SpyEvent(
    private val timestamp: Long,
    private val message: String,
    private val type: SpyEventType,
    private val meta: List<SpyMeta>
) : SpyEntityFactory, SpyEventsAdapter.Item, SpyEventDetailAdapter.Binder, Serializable {

    constructor(message: String, type: SpyEventType, vararg meta:SpyMeta) : this(
        timestamp = System.currentTimeMillis(),
        message = message,
        type = type,
        meta = meta.toList()
    )

    constructor(event: SpyEventEntity, meta: List<SpyMetaEntity>) : this(
        timestamp = event.timestamp,
        message = event.message,
        type = SpyEventType.from(event.type),
        meta = meta
            .filter { it.eventId == event.id }
            .map { SpyMeta(key = it.key, field = it.field) }
    )

    override val spyEventAdapterViewType: Int
        get() = when(type) {
            SpyEventType.INFO -> SpyEventsAdapter.ViewType.INFO
            SpyEventType.WARNING -> SpyEventsAdapter.ViewType.WARNING
            SpyEventType.ERROR -> SpyEventsAdapter.ViewType.ERROR
        }

    override fun bindSpyEventViewHolder(
        holder: SpyEventsAdapter.ViewHolder,
        showDivider: Boolean,
        listener: SpyEventsAdapter.Listener
    ) {
        holder.itemView.setOnClickListener { listener.onItemClick(this) }
        holder.message.text = message
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH)
        val date = Date(timestamp)
        holder.date.text = formatter.format(date)
        holder.divider.isVisible = showDivider
    }

    override fun createSpyEventEntity(): SpyEventEntity =
        SpyEventEntity(timestamp, message, type.name)

    override fun createSpyMetaEntity(eventId: Long): List<SpyMetaEntity> =
        meta.map { it.toSpyMetaEntity(eventId) }

    override val spyMetaAdapterItemCount: Int
        get() = meta.size

    override fun bindSpyMetaViewHolder(holder: SpyEventDetailAdapter.ViewHolder, position: Int) {
        val meta = meta[position]
        holder.key.text = meta.key
        holder.field.text = meta.field
    }
}