package com.rodrigoguerrero.istate

/** Here we use sealed classes to be our event Handler and handle just one particular event here. This is simpler to the MVI Android architecture. **/

sealed class ClearAction {
    object OnCleared : ClearAction()
}