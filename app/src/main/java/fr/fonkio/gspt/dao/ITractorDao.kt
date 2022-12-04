package fr.fonkio.gspt.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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
    @Query("SELECT * FROM Tractor")
    fun getAll(): MutableList<Tractor>
    @Query("SELECT * FROM Tractor WHERE model = :model AND brand = :brand LIMIT 1")
    fun findByModelAndBrand(model: String, brand: String): Tractor?
    @Query("SELECT DISTINCT brand FROM Tractor")
    fun getAllBrand(): List<String>
}