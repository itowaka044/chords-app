package com.example.myapplication.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.myapplication.models.UserChordRef
import com.example.myapplication.models.Chord
import com.example.myapplication.models.User

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
