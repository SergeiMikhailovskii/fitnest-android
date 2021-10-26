package com.mikhailovskii.kmmtest.repository

import com.fitnest.domain.functional.Either
import com.fitnest.domain.functional.Failure
import com.fitnest.domain.entity.LoginData
import com.fitnest.domain.repository.NetworkRepository
import com.mikhailovskii.kmmtest.network.Endpoints
import com.fitnest.domain.service.NetworkService
import io.ktor.client.call.*
import org.kodein.di.DI
import org.kodein.di.instance

class NetworkRepository(val di: DI) : NetworkRepository {

    private val networkService: NetworkService by di.instance()

    override suspend fun loginUser(data: LoginData) {
        val url = "https://10.0.2.2:8080/auth/login"
        val result = networkService.sendData(url, data).receive<String>()
    }

    override suspend fun generateToken() = networkService.fetchData(Endpoints.Main.name)

}