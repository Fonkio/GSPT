package fr.fonkio.gspt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.fonkio.gspt.dao.IPieceDao
import fr.fonkio.gspt.dao.ITractorDao
import fr.fonkio.gspt.entity.Piece
import fr.fonkio.gspt.entity.Tractor

@Database(entities = [Piece::class, Tractor::class], version = 1)
abstract class AppDatabase : RoomDatabase(), java.io.Serializable {
    abstract fun pieceDao(): IPieceDao
    abstract fun tractorDao(): ITractorDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-gspt"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }
    }
}