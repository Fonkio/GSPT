package fr.fonkio.gspt.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PieceTractorPair(
    @Embedded
    var Piece: Piece,
    @Relation(
        parentColumn = "reference",
        entity = Tractor::class,
        entityColumn = "tractorId",
        associateBy = Junction(
            value = PieceWithTractors::class,
            parentColumn = "reference",
            entityColumn = "tractorId"
        )
    )
    var tractors: List<Tractor>
)
