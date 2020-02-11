package br.com.core.android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified D> AppCompatActivity.dependencies(): D {
    return application.dependencies()
}

inline fun <reified D> Fragment.dependencies(): D {
    return requireContext().applicationContext.dependencies()
}

inline fun <reified D> Context.dependencies(): D {
    require(this is DependenciesProvider<*>) {
        "$javaClass is not a subtype of ${DependenciesProvider::class.java}"
    }
    val deps = dependencies()
    require(deps is D) {
        "${deps?.javaClass} is not a subtype of ${D::class.java}"
    }
    return deps
}

interface DependenciesProvider<out D> {
    fun dependencies(): D
}
