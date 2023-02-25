package viacheslav.chugunov.spy

/**
 * The configuration that will set the Spy settings. An instance can be obtained through the builder.
 * @see Builder
 * */
class SpyConfig private constructor(
    val initialMeta: List<SpyMeta>,
    val showSpyNotification: Boolean,
    val isNotificationsImportant: Boolean
) {

    /**
     * Builder to create the configuration.
     * */
    class Builder {
        private var initialMeta: List<SpyMeta> = emptyList()
        private var showSpyNotification: Boolean = false
        private var isNotificationsImportant: Boolean = false

        /**
         * Specifies the meta to be passed to each subsequent event.
         * */
        fun setInitialMeta(initialMeta: List<SpyMeta>): Builder =
            apply { this.initialMeta = initialMeta }

        /**
         * Specifies the meta to be passed to each subsequent event.
         * */
        fun setInitialMeta(vararg initialMeta: SpyMeta): Builder =
            apply { this.initialMeta = initialMeta.toList() }

        /**
         * When you create Spy, you will see a notification that cannot be closed. After clicking
         * on this notification will be open the screen with Spy events.
         * */
        fun showOpenSpyNotification(show: Boolean): Builder =
            apply { this.showSpyNotification = show }

        /**
         * The first time Spy is created, a channel will be created for notifications with the given
         * importance. If the channel is marked as important, then you will see each subsequent
         * notification from Spy on top of your UI, otherwise - the notification will be created in
         * the background.
         * */
        fun isNotificationsImportant(isImportant: Boolean): Builder =
            apply { this.isNotificationsImportant = isImportant }

        /**
         * Creates a final SpyConfig instance
         * */
        fun build(): SpyConfig = SpyConfig(
            initialMeta,
            showSpyNotification,
            isNotificationsImportant
        )
    }
}