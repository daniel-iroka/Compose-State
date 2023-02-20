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
import com.rodrigoguerrero.istate.models.avengersList

/** This is the Composable for the Registration form which is basically the UI for the 'Registration' part of the App. **/

@Composable
fun RegistrationFormScreen(
    registrationFormData: RegistrationFormDataState,
    onEmailChanged : (String) -> Unit,
    onUserNameChanged : (String) -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        EditTextField(
            value = registrationFormData.email,
            isError = !registrationFormData.isValidEmail,
            onValueChanged = {
                onEmailChanged(it)
            },
            leadingIcon = Icons.Default.Email,
            placeholder = R.string.email
        )

        EditTextField(
            value = registrationFormData.username,
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
            RadioButtonWithText(text = R.string.star_wars)

            RadioButtonWithText(text = R.string.star_trek)
        }

        DropDown(menuItems = avengersList)

        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        ) {
            Text(stringResource(R.string.register))
        }
        OutlinedButton(
            onClick = { },
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
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {

    val isSelected = remember {
        mutableStateOf(false)
    }

    RadioButton(
        selected = isSelected.value,
        onClick = { isSelected.value = !isSelected.value }
    )
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.body1.merge(),
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    )
}

@Composable
fun DropDown(
    menuItems: List<String>,
    modifier: Modifier = Modifier
) {

    val selectedItem = remember {
        mutableStateOf("Select your favourite Avenger:")
    }

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
            Text(selectedItem.value)

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
                        selectedItem.value = menuItems[index]
                        isExpanded.value = false
                    }) {

                        Text(text = name)
                    }
                }
            }
        }
    }

}
