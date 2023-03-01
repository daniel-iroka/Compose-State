
package com.rodrigoguerrero.istate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigoguerrero.istate.models.RegistrationFormDataState
import com.rodrigoguerrero.istate.models.User
import com.rodrigoguerrero.istate.ui.composables.FabAddUser
import com.rodrigoguerrero.istate.ui.composables.RegistrationFormScreen
import com.rodrigoguerrero.istate.ui.composables.UserList
import com.rodrigoguerrero.istate.ui.theme.IStateTheme
import com.rodrigoguerrero.istate.viewModels.FormViewModel
import com.rodrigoguerrero.istate.viewModels.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            IStateTheme {
                val navController = rememberNavController()
                val users by mainViewModel.users.observeAsState(emptyList())

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {

                        Column {
                            ListTopAppBar()

                            UserListScreen(navController = navController, users = users)
                        }
                    }

                    composable("form") {

                        Column {
                            FormTopAppBar()

                            // Instantiating our FormViewModel and getting reference to our RegistrationDataFormState.
                            val formViewModel: FormViewModel by viewModels()
                            val registrationFormData by formViewModel.formData.observeAsState(initial = RegistrationFormDataState())

                            RegistrationFormScreen(
                                registrationFormDataState = registrationFormData,
                                onEmailChanged = formViewModel::onEmailChanged,
                                onUserNameChanged = formViewModel::onUsernameChanged,
                                onStarWarsSelectedChange = formViewModel::onStarWarsSelectedChanged,
                                onFavouriteAvengerChanged = formViewModel::onFavoriteAvengerChanged,
                                onClearClicked = formViewModel::onClearClicked,
                                onRegisteredClicked = { user ->
                                    formViewModel.onClearClicked()
                                    mainViewModel.addUser(user)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// This is the composable that displays or emits everything on the Screen
@Composable
fun UserListScreen(
    navController: NavController,
    users : List<User>
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            floatingActionButton = {
                FabAddUser(navController)
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            UserList(users)
        }
    }
}

// Then below are our AppBars or ToolBars.
@Composable
fun FormTopAppBar() {
    TopAppBar(
        title = {
            Text(text = "Form")
        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = Color.White
    )
}

@Composable
fun ListTopAppBar() {
    TopAppBar(
        title = {
            Text(text = "List")
        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = Color.White
    )
}