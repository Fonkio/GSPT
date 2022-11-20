package fr.fonkio.gspt.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Piece(
    @PrimaryKey var reference: String,
    var libelle: String,
    var case: String,
    var price: Double,
    var comment: String,
    var number: Int
){
    constructor() : this("","","",0.0,"",0)
}
