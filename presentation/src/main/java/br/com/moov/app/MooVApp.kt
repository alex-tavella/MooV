package br.com.moov.app

import android.app.Application
import android.os.StrictMode
import br.com.moov.app.util.logd
import br.com.moov.data.dataModule
import br.com.moov.domain.domainModule
import org.koin.standalone.StandAloneContext.startKoin


class MooVApp : Application() {

  override fun onCreate() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
          .detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()   // or .detectAll() for all detectable problems
          .penaltyLog()
          .build())
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .penaltyDeath()
          .build())
    }
    super.onCreate()
    logd { "onCreate" }

    startKoin(listOf(dataModule(this), domainModule(), presentationModule()))
  }
}
