package com.example.myapplication.repository

import com.example.myapplication.DAO.ChordDao
import com.example.myapplication.DAO.UserChordRefDao
import com.example.myapplication.DAO.UserDao
import com.example.myapplication.model.Chord
import com.example.myapplication.model.User
import com.example.myapplication.models.UserChordRef
import com.example.myapplication.models.UserWithChords
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao,
    private val chordDao: ChordDao,
    private val userChordRefDao: UserChordRefDao
) {

    suspend fun getUserById(userId: Long): User =
        userDao.getUserById(userId)

    fun getUserWithChords(userId: Long): Flow<UserWithChords> =
        userDao.getUserWithChords(userId)
    suspend fun insertUser(user: User) =
        userDao.insertUser(user)

    suspend fun updateUser(user: User) =
        userDao.updateUser(user)

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    fun getAllChords(): Flow<List<Chord>> =
        chordDao.getAllChords()

    suspend fun addInitialChords() {
        val count = chordDao.getChordCount()
        if (count == 0) {
            val initialChords = listOf(
                Chord(title = "Wonderwall", artist = "Oasis", content = "__________cifra________"),
                Chord(title = "Creep", artist = "Radiohead", content = "__________cifra________"),
                Chord(title = "Hotel California", artist = "Eagles", content = "__________cifra________"),
                Chord(title = "Smells Like Teen Spirit", artist = "Nirvana", content = "__________cifra________"),
                Chord(title = "Fix You", artist = "Coldplay", content = "__________cifra________")
            )
            initialChords.forEach { chordDao.insertChord(it) }
        }
    }

    suspend fun insertChord(chord: Chord) =
        chordDao.insertChord(chord)


    // funções tabela UserChordRef:

    suspend fun addChordToUser(userId: Long, chordId: Long) =
        userChordRefDao.insertUserChordRef(UserChordRef(userId, chordId))

    suspend fun addFavorite(userId: Long, chordId: Long) =
        userChordRefDao.insertUserChordRef(UserChordRef(userId, chordId))

    suspend fun removeFavorite(userId: Long, chordId: Long) =
        userChordRefDao.deleteUserChord(userId, chordId)
}
