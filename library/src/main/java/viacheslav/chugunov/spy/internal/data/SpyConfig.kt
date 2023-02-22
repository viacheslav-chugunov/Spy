package viacheslav.chugunov.spy.internal.data

import viacheslav.chugunov.spy.SpyMeta

class SpyConfig private constructor(private val initialMeta: List<SpyMeta>) {
    fun getInitialMeta() = initialMeta

    data class SpyConfigBuilder(var initialMeta:List<SpyMeta> = emptyList()){
        fun setInitialMeta(initialMeta:List<SpyMeta>) = apply {this.initialMeta=initialMeta}
        fun build():SpyConfig = SpyConfig(initialMeta)
    }
}