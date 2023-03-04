package viacheslav.chugunov.spy

import java.io.Serializable

data class SpyMeta(
    internal val key: String,
    internal val field: String
) : Serializable