package fr.fonkio.gspt.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fr.fonkio.gspt.entity.Piece
import io.reactivex.Single

@Dao
interface IPieceDao {

    @Query("SELECT COUNT(*) FROM Piece")
    fun sumPieceNumber() : Single<Int>
    @Query("SELECT * FROM Piece WHERE reference = :reference LIMIT 1")
    fun findByReference(reference: String) : Piece?
    @Query("SELECT * FROM Piece")
    fun getAll(): MutableList<Piece>
    @Insert
    fun insertAll(vararg piece: Piece)
    @Delete
    fun delete(piece: Piece)
    @Update
    fun updatePieces(vararg pieces: Piece)
}