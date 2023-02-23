package viacheslav.chugunov.spy.internal.data

import androidx.annotation.DrawableRes
import viacheslav.chugunov.spy.R

internal enum class SpyEventType(
    @DrawableRes val iconRes: Int
) {
    SUCCESS(R.drawable.ic_spy_success),
    INFO(R.drawable.ic_spy_info),
    WARNING(R.drawable.ic_spy_waning),
    ERROR(R.drawable.ic_spy_error);

    companion object {
        fun from(type: String): SpyEventType = when(type) {
            SUCCESS.name -> SUCCESS
            INFO.name -> INFO
            WARNING.name -> WARNING
            ERROR.name -> ERROR
            else -> throw IllegalArgumentException()
        }
    }
}