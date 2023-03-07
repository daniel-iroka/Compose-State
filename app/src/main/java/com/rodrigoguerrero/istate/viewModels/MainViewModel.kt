package com.rodrigoguerrero.istate.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoguerrero.istate.ClearAction
import com.rodrigoguerrero.istate.models.User

// This is our 'MainViewModel' for just our User.

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun addUser(user: User) {
        val users = _users.value?.toMutableList() ?: mutableListOf()
        users.add(user)
        _users.value = users
    }

    fun onClearedAction(onAction : ClearAction) {
        when(onAction) {
            is ClearAction.OnCleared -> clearList()
        }
    }

    // This is our function to removeAllLists in the user collection
    private fun clearList() {
        _users.value?.toMutableList()?.clear()

        // Todo - When I come back, I will complete testing this by running the App and seeing the Number of lists in the logcat.

        Log.i(TAG, "The number of lists are :${_users.value?.toMutableList()?.count()}")
    }
}