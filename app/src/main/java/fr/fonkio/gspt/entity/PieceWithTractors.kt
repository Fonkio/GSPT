package fr.fonkio.gspt.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["reference", "tractorId"])
data class PieceWithTractors(
    val reference: String,
    val tractorId: Long
)
