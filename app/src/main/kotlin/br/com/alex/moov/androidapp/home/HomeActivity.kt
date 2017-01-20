package br.com.alex.moov.androidapp.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.alex.moov.R
import br.com.alex.moov.TMDBApiKeyHolder
import br.com.alex.moov.androidapp.MooVApplication
import br.com.alex.moov.databinding.ActivityHomeBinding
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  val mBinding: ActivityHomeBinding by lazy {
    DataBindingUtil.setContentView<ActivityHomeBinding>(this,
        R.layout.activity_home)
  }

  @Inject lateinit var apiKeyHolder: TMDBApiKeyHolder

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (applicationContext as MooVApplication).getAppComponent().inject(this)

    val drawerLayout = mBinding.drawerLayout
    val toolbar = mBinding.appBar.toolbar

    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
        this, drawerLayout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    mBinding.navView.setNavigationItemSelectedListener(this)
  }

  override fun onBackPressed() {
    val drawer = mBinding.drawerLayout
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_camera -> {
      }
      R.id.nav_gallery -> {
      }
      R.id.nav_slideshow -> {
      }
      R.id.nav_manage -> {
      }
      R.id.nav_share -> {
      }
      R.id.nav_send -> {
      }
      else -> {
      }
    }

    Answers.getInstance().logCustom(CustomEvent("Navigation item selected")
        .putCustomAttribute("Context", "Home drawer")
        .putCustomAttribute("item", item.toString()))

    Timber.d("Api read access token is - ${apiKeyHolder.getApiApiReadAccessToken()}")
    Timber.d("Api key is - ${apiKeyHolder.getApiKey()}")

    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}
