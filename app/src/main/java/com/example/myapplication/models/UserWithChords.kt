package com.example.myapplication.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myapplication.model.Chord
import com.example.myapplication.model.User

data class UserWithChords(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserChordRef::class,
            parentColumn = "userId",
            entityColumn = "chordId"
        )
    )
    val chords: List<Chord>
)
