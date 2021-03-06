package com.fitnest.domain.usecase.private_area

import com.fitnest.domain.entity.response.NotificationsPageResponse
import com.fitnest.domain.repository.NetworkRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class GetNotificationsPageUseCase(
    private val repository: NetworkRepository,
    private val json: Json
) {

    suspend operator fun invoke() = runCatching {
        repository.getNotificationsPage()
    }.map {
        val decoded = json.decodeFromJsonElement<NotificationsPageResponse>(it.data)
        decoded.widgets?.notificationsWidget?.notifications
    }
}