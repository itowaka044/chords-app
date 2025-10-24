package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myapplication.models.User
import com.example.myapplication.models.UserWithChords
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM User Where id = :userId")
    suspend fun getUserById(userId: Long): User

    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUserWithChords(userId: Long): Flow<UserWithChords>

    @Update
    suspend fun updateUser(user: User)

}