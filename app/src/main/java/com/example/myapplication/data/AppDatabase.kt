package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.DAO.ChordDao
import com.example.myapplication.DAO.UserChordRefDao
import com.example.myapplication.DAO.UserDao
import com.example.myapplication.models.Chord
import com.example.myapplication.models.User
import com.example.myapplication.models.UserChordRef

@Database(
    entities = [User::class, Chord::class, UserChordRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun chordDao(): ChordDao
    abstract fun userChordRefDao(): UserChordRefDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}



