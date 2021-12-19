package com.fitnest.android.di

import androidx.lifecycle.ViewModelProvider
import com.fitnest.android.extension.ViewModelFactory
import com.fitnest.android.screen.login.LoginViewModel
import com.fitnest.android.screen.onboarding.OnboardingViewModel
import com.fitnest.android.screen.proxy.ProxyViewModel
import com.fitnest.android.screen.registration.CreateAccountRegistrationViewModel
import com.fitnest.android.screen.registration.RegistrationScreenState
import com.fitnest.android.screen.splash.SplashViewModel
import com.fitnest.domain.di.useCaseModule
import com.fitnest.domain.di.validatorModule
import org.kodein.di.*

val viewModelModule = DI.Module("view model module") {
    import(useCaseModule)
    import(validatorModule)

    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(di)
    }
    bind<LoginViewModel>() with multiton {
        LoginViewModel(instance())
    }
    bind<SplashViewModel>() with multiton {
        SplashViewModel(instance())
    }
    bind<OnboardingViewModel>() with multiton {
        OnboardingViewModel(instance(), instance())
    }
    bind<CreateAccountRegistrationViewModel>() with multiton {
        CreateAccountRegistrationViewModel(instance(), instance())
    }
    bind<ProxyViewModel>() with multiton {
        ProxyViewModel(instance(), instance(), instance())
    }
}

val stateModule = DI.Module("state module") {
    bind<RegistrationScreenState>() with singleton {
        RegistrationScreenState()
    }
}