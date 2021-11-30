package app.prgghale.roomdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    /**
     * Adds user into database*/
    private val _addUser = MutableLiveData<UiStates<Boolean>>()
    val addUser: LiveData<UiStates<Boolean>> = _addUser
    fun addUser(user: UserTable) = viewModelScope.launch {
        _addUser.value = UiStates.Loading()
        _addUser.value = userRepository.addUser(user = user)
    }

    /**
     * gets list of users*/
    private val _users = MutableLiveData<UiStates<List<UserTable>>>()
    val users: LiveData<UiStates<List<UserTable>>> = _users
    fun getUsers() = viewModelScope.launch {
        _users.value = UiStates.Loading()
        delay(900)
        _users.value = userRepository.getUsers()
    }

    /**
    Deletes User*/
    private val _delete = MutableLiveData<UiStates<Boolean>>()
    val delete: LiveData<UiStates<Boolean>> = _delete
    fun deleteUser(user: UserTable) = viewModelScope.launch {
        _delete.value = UiStates.Loading()
        _delete.value = userRepository.deleteUser(user = user)
    }

    fun resetDelete() {
        _delete.value = UiStates.Loading()
    }
}