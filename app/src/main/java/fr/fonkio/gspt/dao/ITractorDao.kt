package fr.fonkio.gspt.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.fonkio.gspt.entity.Tractor
import io.reactivex.Single

@Dao
interface ITractorDao {

    @Query("SELECT COUNT(*) FROM Tractor")
    fun countTractor() : Single<Int>
    @Insert
    fun insertAll(vararg tractor: Tractor)
    @Delete
    fun delete(tractor: Tractor)
}