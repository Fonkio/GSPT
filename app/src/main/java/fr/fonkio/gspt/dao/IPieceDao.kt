package fr.fonkio.gspt.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.fonkio.gspt.entity.Piece
import io.reactivex.Single

@Dao
interface IPieceDao {

    @Query("SELECT COUNT(*) FROM Piece")
    fun sumPieceNumber() : Single<Int>
    @Insert
    fun insertAll(vararg piece: Piece)
    @Delete
    fun delete(piece: Piece)
}