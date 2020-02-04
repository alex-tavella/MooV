package br.com.moov.app.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.moov.app.AppComponent
import br.com.moov.app.AppComponentProvider

fun AppCompatActivity.appComponent(): AppComponent {
    return (application as AppComponentProvider).appComponent
}

fun Fragment.appComponent(): AppComponent {
    return (requireContext().applicationContext as AppComponentProvider).appComponent
}
