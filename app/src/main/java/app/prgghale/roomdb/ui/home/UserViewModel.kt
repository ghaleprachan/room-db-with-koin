package app.prgghale.roomdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.domain.UserProfessionT
import app.prgghale.roomdb.data.repository.UserRepository
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.utils.UiStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    /**
     * Adds user into database*/
    private val _addUser = MutableStateFlow<UiStates<Boolean>>(UiStates.None<Boolean>()) // Provide type argument and call constructor
    val addUser: StateFlow<UiStates<Boolean>> = _addUser.asStateFlow()
    fun addUser(user: UserTable) = viewModelScope.launch {
        _addUser.value = UiStates.Loading() // Assuming Loading() can infer or also takes <Boolean>()
        // If Loading is also generic: _addUser.value = UiStates.Loading<Boolean>()
        _addUser.value = userRepository.addUser(user = user)
    }
    /**
     * gets list of users*/
    private val _users = MutableStateFlow<UiStates<List<UserTable>>>(UiStates.None()) // Initial state
    val users: StateFlow<UiStates<List<UserTable>>> = _users.asStateFlow()

    fun getUsers() = viewModelScope.launch {
        _users.value = UiStates.Loading()
        delay(900) // Keep delay if it's for simulating network or debouncing
        _users.value = userRepository.getUsers()
    }

    /**
     * List of users by joining table with profession*/
    private val _userProfession =
        MutableStateFlow<UiStates<List<UserProfession>>>(UiStates.None()) // Initial state, UiStates.Loading() if it should load immediately
    val userProfession: StateFlow<UiStates<List<UserProfession>>> = _userProfession.asStateFlow()

    fun getUserProfession() = viewModelScope.launch {
        _userProfession.value = UiStates.Loading()
        delay(1000) // Keep delay if it's for simulating network or debouncing
        _userProfession.value = userRepository.getUserProfession()
    }

    fun refreshUser() = viewModelScope.launch {
        _userProfession.value = userRepository.getUserProfession()
        _delete.value = UiStates.Loading()
        _updateTable.value = UiStates.Loading()
    }

    /**
    Deletes User*/
    private val _delete = MutableStateFlow<UiStates<Boolean>>(UiStates.None()) // Initial state
    val delete: StateFlow<UiStates<Boolean>> = _delete.asStateFlow()

    fun deleteUser(user: UserTable) = viewModelScope.launch {
        _delete.value = UiStates.Loading()
        _delete.value = userRepository.deleteUser(user = user)
    }

    /**
     * List of all professions*/
    private val _professions = MutableStateFlow<List<ProfessionTable>>(emptyList())
    val professions: StateFlow<List<ProfessionTable>> = _professions.asStateFlow()
    fun getProfessions() = viewModelScope.launch {
        val fetchedProfessions = userRepository.getProfessions()
        _professions.value = fetchedProfessions
    }

    /**
     * Update table*/
    private val _updateTable = MutableStateFlow<UiStates<Boolean>>(UiStates.Loading()) // Initial state
    val updateTable: StateFlow<UiStates<Boolean>> = _updateTable.asStateFlow()
    fun updateTable(user: UserTable) = viewModelScope.launch {
        _updateTable.value = UiStates.Loading()
        _updateTable.value = userRepository.updateUser(user)
    }

    /**
     * Get list of favorite users*/
    private val _favoriteUsers = MutableStateFlow<List<UserTable>>(emptyList())
    val favoriteUsers: StateFlow<List<UserTable>> = _favoriteUsers.asStateFlow()
    fun getFavoriteUsers() = viewModelScope.launch {
        _favoriteUsers.value = userRepository.getFavoriteUsers()
    }

    /**
     * NOTE:
     *
     * 1. This part is not in used in code
     * 2. This is sample how to call data from room DB in parallel manner*/
    private val usersF: Flow<UiStates<List<UserTable>>> = flow {
        userRepository.getUsers()
    }
    private val professionF: Flow<UiStates<List<ProfessionTable>>> = flow {
        userRepository.getProfessions()
    }

    // NOTE not working do more research on it
    val userProfessionT: Flow<UiStates<UserProfessionT>> =
        usersF.combine(professionF) { user, profession ->
            UiStates.Success(data = UserProfessionT(user = user.data, profession = profession.data))
        }.flowOn(context = Dispatchers.IO)
}