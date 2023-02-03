package viacheslav.chugunov.spy.internal

internal enum class SpyEventType {
    INFO,
    WARNING,
    ERROR;

    companion object {
        fun from(type: String): SpyEventType = when(type) {
            "INFO" -> INFO
            "WARNING" -> WARNING
            "ERROR" -> ERROR
            else -> throw IllegalArgumentException()
        }
    }
}