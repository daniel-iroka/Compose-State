package com.rodrigoguerrero.istate.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rodrigoguerrero.istate.R
import com.rodrigoguerrero.istate.models.RegistrationFormDataState
import com.rodrigoguerrero.istate.models.User
import com.rodrigoguerrero.istate.models.avengersList

/** This is the Composable for the Registration form which is basically the UI for the 'Registration' part of the App. **/

@Composable
fun RegistrationFormScreen(
    registrationFormDataState: RegistrationFormDataState,
    onEmailChanged : (String) -> Unit,
    onUserNameChanged : (String) -> Unit,
    onStarWarsSelectedChange : (Boolean) -> Unit,
    onFavouriteAvengerChanged : (String) -> Unit,
    onRegisteredClicked : (User) -> Unit,
    onClearClicked : () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        EditTextField(
            value = registrationFormDataState.email,
            isError = !registrationFormDataState.isValidEmail,
            onValueChanged = {
                onEmailChanged(it)
            },
            leadingIcon = Icons.Default.Email,
            placeholder = R.string.email
        )

        EditTextField(
            value = registrationFormDataState.username,
            isError = false,
            onValueChanged = {
                onUserNameChanged(it)
            },
            leadingIcon = Icons.Default.AccountBox,
            placeholder = R.string.username,
            modifier = Modifier.padding(top = 16.dp),
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {

            RadioButtonWithText(
                text = R.string.star_wars,
                isSelected = registrationFormDataState.isStarWarsSelected,
                onClick = { onStarWarsSelectedChange(true) }
            )

            RadioButtonWithText(
                text = R.string.star_trek,
                isSelected = !registrationFormDataState.isStarWarsSelected,
                onClick = { onStarWarsSelectedChange(false) }
            )
        }

        DropDown(
            menuItems = avengersList,
            selectedItem = registrationFormDataState.favoriteAvenger,
            onItemSelected = { selectedAvenger ->
                onFavouriteAvengerChanged(selectedAvenger)
            }
        )

        /** These are our Outlined buttons for Register and Clear. **/

        OutlinedButton(
            // This will basically just add a new 'User' when we click on it based on the values the User put in.
            onClick = {
                onRegisteredClicked(
                    User(
                        username = registrationFormDataState.username,
                        email = registrationFormDataState.email,
                        favoriteAvenger = registrationFormDataState.favoriteAvenger,
                        likesStarWars = registrationFormDataState.isStarWarsSelected
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = registrationFormDataState.isRegisterEnabled
        ) {
            Text(stringResource(R.string.register))
        }
        OutlinedButton(
            onClick = {
                onClearClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.clear))
        }
    }
}


/**
 *  These Composables below are just generic Composables we setup so that we can be able to reuse them across our App.
 */

@Composable
fun EditTextField(
    value : String,
    isError : Boolean,
    onValueChanged : (String) -> Unit,
    leadingIcon: ImageVector,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = value,
        isError = isError,
        onValueChange = {
            onValueChanged(it)
        },
        leadingIcon = { Icon(leadingIcon, contentDescription = "") },
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(placeholder)) }
    )
}

@Composable
fun RadioButtonWithText(
    isSelected : Boolean,
    onClick : () -> Unit,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {

    RadioButton(
        selected = isSelected,
        onClick = { onClick }
    )
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.body1.merge(),
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    )
}

// DropDownMenu is now a hybrid composable, which I think means has its own state and Implements State hoisting at the same time.
@Composable
fun DropDown(
    selectedItem : String,
    onItemSelected : (String) -> Unit,
    menuItems: List<String>,
    modifier: Modifier = Modifier
) {

    val isExpanded = remember {
        mutableStateOf(false)
    }

    Box {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable { isExpanded.value = true }
        ) {

            // This text composable will just display the 'option' the user selects in the DropDownMenu
            Text(selectedItem)

            Icon(Icons.Filled.ArrowDropDown, contentDescription = "")

            DropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false },
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                menuItems.forEachIndexed { index, name ->

                    DropdownMenuItem(onClick = {
                        onItemSelected(menuItems[index])
                        isExpanded.value = false
                    }) {

                        Text(text = name)
                    }
                }
            }
        }
    }
}
