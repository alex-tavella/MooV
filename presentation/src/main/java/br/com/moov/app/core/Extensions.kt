package br.com.moov.app.core

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.moov.app.AppComponent
import br.com.moov.app.AppComponentProvider

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(
    viewModelProviderFactory: ViewModelProvider.Factory
): Lazy<T> {
    return viewModels { viewModelProviderFactory }
}

inline fun <reified VM : ViewModel> Fragment.viewModel(
    viewModelProviderFactory: ViewModelProvider.Factory
): Lazy<VM> {
    return viewModels { viewModelProviderFactory }
}

inline fun <reified VM : ViewModel> Fragment.activityViewModel(
    viewModelProviderFactory: ViewModelProvider.Factory
): Lazy<VM> {
    return activityViewModels { viewModelProviderFactory }
}

fun AppCompatActivity.appComponent(): AppComponent {
    return (application as AppComponentProvider).appComponent
}

fun Fragment.appComponent(): AppComponent {
    return (requireContext().applicationContext as AppComponentProvider).appComponent
}
