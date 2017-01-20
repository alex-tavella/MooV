package br.com.alex.moov.androidapp

import android.app.Application
import android.os.StrictMode
import br.com.alex.moov.BuildConfig
import br.com.alex.moov.TMDBApiKeyHolder
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import timber.log.Timber.DebugTree

class MooVApplication : Application() {

  private val component: AppComponent by lazy {
    DaggerAppComponent.builder()
        .appModule(AppModule(this, TMDBApiKeyHolder()))
        .build()
  }

  override fun onCreate() {

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
          .detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()
          .penaltyLog()
          .build())
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .penaltyDeath()
          .build())

      Timber.plant(DebugTree())
    }

    super.onCreate()

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    LeakCanary.install(this)

    Fabric.with(this, Crashlytics(), CrashlyticsCore(), Answers())
  }

  fun getAppComponent(): AppComponent {
    return component
  }
}