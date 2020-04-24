package jay2468.maskmap.data.db.dao

import androidx.room.*
import jay2468.maskmap.data.db.entity.MaskEntity

@Dao
interface MaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(maskEntity: MaskEntity): Long

    @Query("select * from Mask")
    fun getAllAdress(): List<MaskEntity>

    @Query("select * from Mask where address like '%'||:county||'%'||:town||'%'")
    fun getAdressByCity(county: String, town: String): List<MaskEntity>

    @Query("select * from Mask where name = :name")
    fun getPharmacyByName(name: String): MaskEntity
}