package com.rodrigoguerrero.istate.models

import com.rodrigoguerrero.istate.viewModels.favoriteAvengerDefault

/** This is our Model or 'State' which holds or represents our data **/

val avengersList = listOf(
    "Iron Man",
    "Capitan America",
    "Hulk",
    "Spiderman",
    "Black Widow",
    "Hawkeye",
    "Thor",
    "Scarlet Witch",
    "Black Panther"
)

data class RegistrationFormDataState(
    val email: String = "",
    val username: String = "",
    val isStarWarsSelected: Boolean = true,
    val avengers: List<String> = avengersList,
    val showDropDownMenu: Boolean = false,
    val favoriteAvenger: String = favoriteAvengerDefault,
    val isRegisterEnabled: Boolean = false,
    val isValidEmail: Boolean = false
)
