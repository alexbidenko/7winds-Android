package admire.by.mobi.a7winds.a7winds

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Cicerone
import android.app.Application


class CiceroneApplication : Application() {

    private var cicerone: Cicerone<Router>? = null

    init {
        INSTANCE = this
    }

    val navigatorHolder: NavigatorHolder
        get() = cicerone!!.navigatorHolder

    val router: Router
        get() = cicerone!!.router

    override fun onCreate() {
        super.onCreate()
        cicerone = Cicerone.create()
    }

    companion object {
        var INSTANCE: CiceroneApplication? = null
    }
}