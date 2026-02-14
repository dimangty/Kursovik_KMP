package com.example.kursovikkmp.feature.profile

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.updateValue
import com.example.kursovikkmp.feature.auth.AuthService
import com.example.kursovikkmp.navigation.NavigationAction
import com.example.kursovikkmp.shared.common.extension.asCommonFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authService: AuthService
) : BaseViewModel<ProfileState, ProfileEvents>() {

    private val _effect = MutableSharedFlow<ProfileEffect>()
    val effectFlow = _effect.asCommonFlow()

    override fun initToolbar() {
        var titleBar = state.titleBarState.copy()
        titleBar = titleBar.copy(
            title = titleBar.title.updateValue(getString(MR.strings.scr_profile_tab_title)),
            isNavigateBackVisible = false
        )
        updateState { copy(titleBarState = titleBar) }
    }

    override fun initScreenData() {
        loadProfile()
    }

    override fun initialState(): ProfileState = ProfileState()

    override fun onEvent(event: ProfileEvents) {
        when (event) {
            ProfileEvents.AvatarTapped -> {
                viewModelScope.launch {
                    _effect.emit(ProfileEffect.ShowImageSourceDialog)
                }
            }

            is ProfileEvents.PhotoChanged -> {
                viewModelScope.launch {
                    profileRepository.updatePhoto(event.photoPath)
                    loadProfile()
                }
            }

            ProfileEvents.LogoutTapped -> {
                viewModelScope.launch {
                    authService.logout()
                    navigate(NavigationAction.NavigateToLogin)
                }
            }
        }
    }

    private fun loadProfile() {
        val hasSavedProfile = profileRepository.hasSavedProfile()
        val profile = profileRepository.getProfileOrMock()
        updateState {
            copy(
                fullName = "${profile.firstName} ${profile.lastName}".trim(),
                gender = profile.gender,
                birthDate = profile.birthDate,
                location = listOf(profile.city, profile.country)
                    .filter { it.isNotBlank() }
                    .joinToString(", "),
                email = profile.email,
                phone = profile.phone,
                photoPath = profile.photoPath,
                isMockData = !hasSavedProfile
            )
        }
    }
}
