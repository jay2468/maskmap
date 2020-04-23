package jay2468.maskmap.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Mask")
data class MaskEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
    var mask_adult: Int,
    var mask_child: Int,
    var updated: String,
    var available: String,
    var note: String,
    var custom_note: String,
    var website: String,
    var county: String,
    var town: String,
    val cunli: String,
    val service_periods: String,
    var Latitude: Double,
    var Longitude: Double
):Serializable