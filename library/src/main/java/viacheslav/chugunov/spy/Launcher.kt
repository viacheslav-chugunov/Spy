package viacheslav.chugunov.spy

object Launcher {
    private var isFirstLaunch = true
    private var isCanShowNotification = true

    //TODO must launch when MainAcitivity started
    fun getIsFirstLaunch(): Boolean {
        val temp = isFirstLaunch
        isFirstLaunch = false
        return temp
    }

    fun isCanShowNotification(): Boolean {
        return if(isCanShowNotification){
            isCanShowNotification = false
            true
        } else false
    }
}