package com.example.myapplication.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.Chord
import kotlinx.coroutines.flow.Flow

@Dao
interface ChordDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertChord(chord: Chord): Long

   @Query("SELECT * FROM Chord ORDER BY title ASC")
   fun getAllChords(): Flow<List<Chord>>

   @Delete
   suspend fun deleteChord(chord: Chord)

   @Query("SELECT COUNT(*) FROM Chord")
   suspend fun getChordCount(): Int
}
