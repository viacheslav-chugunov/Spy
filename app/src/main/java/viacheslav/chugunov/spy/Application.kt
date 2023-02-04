package viacheslav.chugunov.spy

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Spy.install(this)
    }

}