/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.rodrigoguerrero.istate.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodrigoguerrero.istate.models.User

@Composable
fun FabAddUser(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate("form") },
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "",
            tint = Color.White
        )
    }
}

// This composable will be responsible for displaying a list of USERS
@Composable
fun UserList(users: List<User> = emptyList()) {

    LazyColumn {
        items(
            items = users,
            key = { user -> user.email }
        ) { user ->
            ItemUser(user = user)
            Divider()
        }
    }
}

// Todo - When I come back next time, I will add some more modifications to this like making the lists and the texts look more organized and better.

@Composable
fun ItemUser(user: User) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = user.username,
            style = MaterialTheme.typography.h4
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.h5
        )
        val likes = if (user.likesStarWars) "Star Wars" else "Star Trek"
        Text(
            text = "Likes $likes",
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Avenger: ${user.favoriteAvenger}",
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
@Preview
fun PreviewItemUser() {
    val user = User("username", "email@email.com", "Iron Man", true)
    ItemUser(user = user)
}

@Composable
@Preview
fun PreviewUserList() {
    UserList()
}