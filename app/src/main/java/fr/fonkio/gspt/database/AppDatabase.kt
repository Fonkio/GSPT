package fr.fonkio.gspt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.fonkio.gspt.dao.IPieceDao
import fr.fonkio.gspt.dao.ITractorDao
import fr.fonkio.gspt.entity.Piece
import fr.fonkio.gspt.entity.Tractor

@Database(entities = [Piece::class, Tractor::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pieceDao(): IPieceDao
    abstract fun tractorDao(): ITractorDao
}