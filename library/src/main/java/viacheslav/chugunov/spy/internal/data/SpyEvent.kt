package viacheslav.chugunov.spy.internal.data

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import viacheslav.chugunov.spy.R
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
    val message: String,
    private val type: SpyEventType,
    private val meta: List<SpyMeta>
) : SpyEntityFactory, SpyEventsAdapter.Item, SpyEventDetailAdapter.Binder, Serializable {
    val hasMeta = meta.isNotEmpty()

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
            SpyEventType.SUCCESS -> SpyEventsAdapter.ViewType.SUCCESS
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
        val bgColorRes = if (position % 2 == 0) R.color.spy_res_white else R.color.spy_res_gray_100
        val context = holder.itemView.context
        holder.root.setBackgroundColor(ContextCompat.getColor(context, bgColorRes))
    }

    fun share(): Intent {
        val text = StringBuilder()
        if (message.isNotEmpty()) {
            text.append(message)
            if (meta.isNotEmpty()) {
                text.append("\n\n")
            }
        }
        meta.forEachIndexed { index, meta ->
            text.append("${index + 1}. ${meta.key}:\n${meta.field}")
            if (index != this.meta.lastIndex) {
                text.append("\n\n")
            }
        }
        return Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, message)
            .putExtra(Intent.EXTRA_TEXT, text.toString())
    }
}