
package com.rodrigoguerrero.istate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigoguerrero.istate.models.RegistrationFormDataState
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

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        UserListScreen(navController)
                    }
                    composable("form") {
                        val formViewModel: FormViewModel by viewModels()
                        val registrationFormData by formViewModel.formData.observeAsState(initial = RegistrationFormDataState())

                        RegistrationFormScreen(
                            registrationFormData = registrationFormData,
                            onEmailChanged = formViewModel::onEmailChanged,
                            onUserNameChanged = formViewModel::onUsernameChanged
                        )
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
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            floatingActionButton = {
                FabAddUser(navController)
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            UserList()
        }
    }
}