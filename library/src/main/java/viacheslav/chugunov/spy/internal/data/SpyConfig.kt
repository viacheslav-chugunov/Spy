package viacheslav.chugunov.spy.internal.data

import viacheslav.chugunov.spy.SpyMeta

class SpyConfig private constructor(private val initialMeta: List<SpyMeta>) {
    fun getInitialMeta() = initialMeta

    class Builder {
        private var initialMeta: List<SpyMeta> = emptyList()

        fun setInitialMeta(initialMeta: List<SpyMeta>): Builder =
            apply { this.initialMeta = initialMeta }

        fun setInitialMeta(vararg  initialMeta: SpyMeta): Builder =
            apply { this.initialMeta = initialMeta.toList() }

        fun build(): SpyConfig = SpyConfig(initialMeta)
    }
}