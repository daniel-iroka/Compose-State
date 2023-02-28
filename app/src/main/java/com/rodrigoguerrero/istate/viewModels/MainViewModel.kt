package com.rodrigoguerrero.istate.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoguerrero.istate.models.User

// This is our 'MainViewModel' for just our User.

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun addUser(user: User) {
        val users = _users.value?.toMutableList() ?: mutableListOf()
        users.add(user)
        _users.value = users
    }
}