package fr.fonkio.gspt.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tractor(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var libelle: String
){
    constructor() : this(0,"")
}
