package app.prgghale.roomdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
     * List of users by joining table with profession*/
    private val _userProfession =
        MutableStateFlow<UiStates<List<UserProfession>>?>(null)
    val userProfession: StateFlow<UiStates<List<UserProfession>>?> = _userProfession
    fun getUserProfession() = viewModelScope.launch {
        _userProfession.value = UiStates.Loading()
        delay(1000)
        _userProfession.value = userRepository.getUserProfession()
    }

    fun refreshUser() = viewModelScope.launch {
        _userProfession.value = userRepository.getUserProfession()
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

    private val _professions = MutableStateFlow(emptyList<ProfessionTable>())
    val professions: StateFlow<List<ProfessionTable>> = _professions
    fun getProfessions() = viewModelScope.launch {
        _professions.value = userRepository.getProfessions()
    }
}