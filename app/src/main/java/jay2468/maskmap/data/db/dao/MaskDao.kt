package jay2468.maskmap.data.db.dao

import androidx.room.*
import jay2468.maskmap.data.db.entity.MaskEntity

@Dao
interface MaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(maskEntity: MaskEntity): Long

    @Query("select * from Mask")
    suspend fun getAllAdress(): List<MaskEntity>

    @Query("select * from Mask where address like '%'||:county||'%'||:town||'%'")
    suspend fun getAdressByCity(county: String, town: String): List<MaskEntity>

    @Query("select * from Mask where name = :name")
    suspend fun getPharmacyByName(name: String): MaskEntity
}