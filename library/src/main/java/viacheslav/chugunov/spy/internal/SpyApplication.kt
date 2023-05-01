package viacheslav.chugunov.spy.internal

import android.app.Application

class SpyApplication: Application() {
    private var isFirstLaunch = true

    fun getIsFirstLaunch(): Boolean {
        val temp = isFirstLaunch
        isFirstLaunch = false
        return temp
    }
}