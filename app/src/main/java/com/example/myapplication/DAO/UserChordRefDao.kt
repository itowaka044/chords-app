package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.models.UserChordRef

@Dao
interface UserChordRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserChordRef(ref: UserChordRef)

    @Query("DELETE FROM UserChordRef WHERE userId = :userId AND chordId = :chordId")
    suspend fun deleteUserChord(userId: Long, chordId: Long)
}
