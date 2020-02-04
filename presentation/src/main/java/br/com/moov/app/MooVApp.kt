package br.com.moov.app

import android.app.Application
import android.os.StrictMode
import br.com.moov.app.util.logd

class MooVApp : Application(), AppComponentProvider {

    override val appComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
        super.onCreate()
        logd { "onCreate" }
    }
}
