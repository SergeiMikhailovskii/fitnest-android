package com.fitnest.domain.repository

import com.fitnest.domain.entity.GetRegistrationResponseData
import com.fitnest.domain.entity.LoginData
import com.fitnest.domain.entity.base.BaseRequest
import com.fitnest.domain.entity.base.BaseResponse
import com.fitnest.domain.entity.request.DeleteNotificationRequest
import com.fitnest.domain.entity.request.PinNotificationRequest
import com.fitnest.domain.entity.response.LoginPageResponse
import com.fitnest.domain.functional.Either
import com.fitnest.domain.functional.Failure

interface NetworkRepository {

    suspend fun generateToken(): Either<Failure, BaseResponse>

    suspend fun getOnboardingStep(): Either<Failure, String>

    suspend fun submitOnboardingStep(): Either<Failure, Unit>

    suspend fun getRegistrationStepData(): Either<Failure, GetRegistrationResponseData>

    suspend fun submitRegistrationStep(request: BaseRequest): Either<Failure, Unit>

    suspend fun getDashboardData(): BaseResponse

    suspend fun getNotificationsPage(): BaseResponse

    suspend fun deactivateNotifications(ids: List<Int>?): BaseResponse

    suspend fun pinNotification(request: PinNotificationRequest): BaseResponse

    suspend fun deleteNotification(request: DeleteNotificationRequest): BaseResponse

    suspend fun getLoginPage(): BaseResponse

    suspend fun loginUser(request: LoginPageResponse.LoginPageFields): BaseResponse

}