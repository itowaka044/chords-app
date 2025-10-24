package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.myapplication.models.Chord
import com.example.myapplication.models.User

@Entity(
    primaryKeys = ["userId", "chordId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Chord::class,
            parentColumns = ["id"],
            childColumns = ["chordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("chordId")]
)
data class UserChordRef(
    val userId: Long,
    val chordId: Long,
    val favorite: Boolean = false
)
