package br.com.moov.app

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import br.com.moov.app.movie.MoviesFragment

class MainActivity : AppCompatActivity() {

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.navigation_home -> {
        supportFragmentManager.beginTransaction().replace(R.id.container, MoviesFragment()).commit()
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_dashboard -> {
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_notifications -> {
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val navigation = findViewById<BottomNavigationView>(R.id.navigation)
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    supportFragmentManager.beginTransaction().replace(R.id.container, MoviesFragment()).commit()
  }
}
