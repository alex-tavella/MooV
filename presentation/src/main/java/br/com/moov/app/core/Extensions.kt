package br.com.moov.app.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.moov.app.AppComponent
import br.com.moov.app.AppComponentProvider

inline fun <reified T : ViewModel> AppCompatActivity.createViewModel(
    viewModelProviderFactory: ViewModelProvider.Factory
): T {
  return T::class.java.let { clazz ->
    ViewModelProviders.of(this, viewModelProviderFactory).get(clazz)
  }
}

inline fun <reified VM : ViewModel> Fragment.createViewModel(
    viewModelProviderFactory: ViewModelProvider.Factory
): VM {
  return VM::class.java.let { clazz ->
    ViewModelProviders.of(this, viewModelProviderFactory).get(clazz)
  }
}

fun AppCompatActivity.appComponent(): AppComponent {
  return (application as AppComponentProvider).appComponent
}

fun Fragment.appComponent(): AppComponent {
  return (requireContext().applicationContext as AppComponentProvider).appComponent
}
