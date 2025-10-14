package com.kriscg.laboratorio8.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kriscg.laboratorio8.data.datastore.UserPreferences
import com.kriscg.laboratorio8.data.datastore.dataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userPreferences = UserPreferences(application.dataStore)

    val userName = userPreferences.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun saveUserName(name: String) {
        viewModelScope.launch {
            userPreferences.saveUserName(name)
        }
    }

    fun clearUserName() {
        viewModelScope.launch {
            userPreferences.clearUserName()
        }
    }
}
