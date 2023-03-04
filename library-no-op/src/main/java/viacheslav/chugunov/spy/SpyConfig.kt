package viacheslav.chugunov.spy

class SpyConfig private constructor(
    val initialMeta: List<SpyMeta>,
    val showSpyNotification: Boolean,
    val isNotificationsImportant: Boolean
) {

    class Builder {
        private var initialMeta: List<SpyMeta> = emptyList()
        private var showSpyNotification: Boolean = false
        private var isNotificationsImportant: Boolean = false

        fun setInitialMeta(initialMeta: List<SpyMeta>): Builder =
            apply { this.initialMeta = initialMeta }

        fun setInitialMeta(vararg initialMeta: SpyMeta): Builder =
            apply { this.initialMeta = initialMeta.toList() }

        fun showOpenSpyNotification(show: Boolean): Builder =
            apply { this.showSpyNotification = show }

        fun isNotificationsImportant(isImportant: Boolean): Builder =
            apply { this.isNotificationsImportant = isImportant }

        fun build(): SpyConfig = SpyConfig(
            initialMeta,
            showSpyNotification,
            isNotificationsImportant
        )
    }
}