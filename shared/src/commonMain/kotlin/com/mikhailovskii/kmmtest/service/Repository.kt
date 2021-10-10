package com.mikhailovskii.kmmtest.service

import com.mikhailovskii.kmmtest.entity.LoginData
import io.ktor.client.call.*
import org.kodein.di.DI

class Repository(val di: DI) {

    private val networkService = NetworkService(di)

    suspend fun loginUser(data: LoginData) {
        val url = "https://10.0.2.2:8080/auth/login"
        val result = networkService.sendData(url, data).receive<String>()
    }

    suspend fun generateToken() {
        val url = "http://10.0.2.2:8080/auth/generate-token"
        val result = networkService.fetchData(url).receive<String>()
    }

}