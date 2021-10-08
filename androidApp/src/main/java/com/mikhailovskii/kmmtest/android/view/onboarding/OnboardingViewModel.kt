package com.mikhailovskii.kmmtest.android.view.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikhailovskii.kmmtest.android.R
import com.mikhailovskii.kmmtest.entity.OnboardingState

class OnboardingViewModel : ViewModel() {

    private val _stateLiveData = MutableLiveData<OnboardingState>()
    internal val stateLiveData: LiveData<OnboardingState> = _stateLiveData

    internal fun updateScreenState(type: String) {
        when(type) {
            "FIRST" -> {
                _stateLiveData.value = OnboardingState(
                    imageResId = R.drawable.ic_onboarding_first,
                    title = R.string.onboarding_first_title,
                    description = R.string.onboarding_first_description,
                    progress = 0
                )
            }
            "SECOND" -> {
                _stateLiveData.value = OnboardingState(
                    imageResId = R.drawable.ic_onboarding_second,
                    title = R.string.onboarding_second_title,
                    description = R.string.onboarding_second_description,
                    progress = 1
                )
            }
            "THIRD" -> {
                _stateLiveData.value = OnboardingState(
                    imageResId = R.drawable.ic_onboarding_third,
                    title = R.string.onboarding_third_title,
                    description = R.string.onboarding_third_description,
                    progress = 2
                )
            }
        }

    }

}