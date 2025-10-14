package com.example.myapplication.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myapplication.model.Chord
import com.example.myapplication.model.User

data class ChordWithUsers(
    @Embedded val chord: Chord,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserChordRef::class,
            parentColumn = "chordId",
            entityColumn = "userId"
        )
    )
    val users: List<User>
)
