package com.mikhailovskii.kmmtest.di

import com.mikhailovskii.kmmtest.usecase.LoginUseCase
import com.mikhailovskii.kmmtest.usecase.GenerateTokenUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val useCaseModule = DI.Module("use case module") {
    bind<LoginUseCase>() with singleton {
        LoginUseCase(di)
    }
    bind<GenerateTokenUseCase>() with singleton {
        GenerateTokenUseCase(di)
    }
}