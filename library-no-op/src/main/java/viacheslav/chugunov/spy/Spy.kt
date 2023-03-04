package viacheslav.chugunov.spy

import android.content.Context

class Spy {

    constructor(applicationContext: Context, config: SpyConfig)

    constructor(applicationContext: Context)

    fun success(message: String, vararg meta: SpyMeta) = Unit

    fun success(message: String, meta: Collection<SpyMeta>) = Unit

    fun info(message: String, vararg meta: SpyMeta) = Unit

    fun info(message: String, meta: Collection<SpyMeta>) = Unit

    fun warning(message: String, vararg meta: SpyMeta) = Unit

    fun warning(message: String, meta: Collection<SpyMeta>) = Unit

    fun error(message: String, vararg meta: SpyMeta) = Unit

    fun error(message: String, meta: Collection<SpyMeta>) = Unit

    fun error(error: Throwable, vararg meta: SpyMeta) = Unit

    fun error(error: Throwable, meta: Collection<SpyMeta>) = Unit
}