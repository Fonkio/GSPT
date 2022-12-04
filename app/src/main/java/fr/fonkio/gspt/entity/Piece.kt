package fr.fonkio.gspt.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Piece(
    @PrimaryKey var reference: String,
    var libelle: String,
    var cases: String,
    var price: Double,
    var comment: String,
    var amount: Int
) : java.io.Serializable {
    constructor() : this("","","",0.0,"",0)
}
