package fr.fonkio.gspt.dao

import androidx.room.*
import fr.fonkio.gspt.entity.PieceTractorPair
import fr.fonkio.gspt.entity.PieceWithTractors

@Dao
interface IPieceWithTractorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: PieceWithTractors)

    @Delete
    fun delete(join: PieceWithTractors)

    @Transaction
    @Query("SELECT * FROM Piece")
    fun getPieces(): List<PieceTractorPair>

    @Transaction
    @Query("DELETE FROM PieceWithTractors WHERE reference = :reference")
    fun deleteByReference(reference: String)

    @Transaction
    @Query("SELECT * FROM Piece WHERE reference = :reference LIMIT 1")
    fun findByReference(reference: String): PieceTractorPair
}