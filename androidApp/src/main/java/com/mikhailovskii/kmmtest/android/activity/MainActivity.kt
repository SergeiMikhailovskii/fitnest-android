package com.mikhailovskii.kmmtest.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.mikhailovskii.kmmtest.android.di.viewModelModule
import com.mikhailovskii.kmmtest.android.base.FitnestApp
import org.kodein.di.*


class MainActivity : ComponentActivity(), DIAware {

    override val diContext: DIContext<*> = diContext(this)

    override val di by DI.lazy {
        import(viewModelModule)
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnestApp()
        }
    }

}
