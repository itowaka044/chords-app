import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Chord
import com.example.myapplication.models.User
import com.example.myapplication.models.UserWithChords
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class UserChordViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()
    val allChords = repository.getAllChords().asLiveData()

    fun defaultUser() = viewModelScope.launch {
        val existingUser = repository.getUserByEmail("teste@teste.com")
        if (existingUser == null) {
            repository.insertUser(
                User(
                    name = "teste",
                    email = "teste@teste.com",
                    phoneNumber = "123456789"
                )
            )
        } else {
            _currentUser.value = existingUser
        }
    }

    fun insertAndSetUser(user: User) = viewModelScope.launch {
        val id = repository.insertUser(user)
        _currentUser.value = repository.getUserById(id)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
        _currentUser.value = user
    }

    fun seedInitialChords() = viewModelScope.launch {
        repository.addInitialChords()
    }

    // funções tabela UserChordRef:
    fun addFavorite(userId: Long, chordId: Long) = viewModelScope.launch {
        repository.addFavorite(userId, chordId)
    }

    fun getUserFavorites(userId: Long): LiveData<UserWithChords> =
        repository.getUserWithChords(userId).asLiveData()

    fun removeFavorite(userId: Long, chordId: Long) = viewModelScope.launch {
        repository.removeFavorite(userId, chordId)
    }

}

