package com.rodrigoguerrero.istate.viewModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoguerrero.istate.models.RegistrationFormDataState

const val favoriteAvengerDefault = "Select favorite Avenger"

// This is our 'State handler' or ViewModel which will receive events upwards, modify the State's and then send it back to the Composables.

class FormViewModel : ViewModel() {

    private val _formData = MutableLiveData(RegistrationFormDataState())
    val formData: LiveData<RegistrationFormDataState>
        get() = _formData

    fun onClearClicked() {
        _formData.value = RegistrationFormDataState()
    }

    fun onDropDownClicked() {
        _formData.value = _formData.value?.copy(showDropDownMenu = true)
    }

    fun onDropDownDismissed() {
        _formData.value = _formData.value?.copy(showDropDownMenu = false)
    }

    fun onEmailChanged(email: String) {
        val isFormValid = _formData.value?.run {
            isFormValid(email, isValidEmail, username, favoriteAvenger)
        } ?: false
        _formData.value = _formData.value?.copy(
            email = email,
            isValidEmail = validateEmail(email),
            isRegisterEnabled = isFormValid
        )
    }

    fun onFavoriteAvengerChanged(value: String) {
        val isFormValid = _formData.value?.run {
            isFormValid(email, isValidEmail, username, value)
        } ?: false
        _formData.value = _formData.value?.copy(
            favoriteAvenger = value,
            showDropDownMenu = false,
            isRegisterEnabled = isFormValid
        )
    }

    fun onUsernameChanged(username: String) {
        val isFormValid = _formData.value?.run {
            isFormValid(email, isValidEmail, username, favoriteAvenger)
        } ?: false
        _formData.value = _formData.value?.copy(username = username, isRegisterEnabled = isFormValid)
    }

    fun onStarWarsSelectedChanged(isStarWarsSelected: Boolean) {
        _formData.value = _formData.value?.copy(isStarWarsSelected = isStarWarsSelected)
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFormValid(
        email: String,
        isValidEmail: Boolean,
        username: String,
        favoriteAvenger: String
    ) =
        email.isNotEmpty() && isValidEmail && username.isNotEmpty() && favoriteAvenger != favoriteAvengerDefault
}

