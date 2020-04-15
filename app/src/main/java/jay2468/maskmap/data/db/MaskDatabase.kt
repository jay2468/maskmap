package jay2468.maskmap.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jay2468.maskmap.common.dbName
import jay2468.maskmap.data.db.dao.MaskDao
import jay2468.maskmap.data.db.entity.MaskEntity

@Database(entities = [MaskEntity::class], version = 1)
abstract class MaskDatabase : RoomDatabase() {
    abstract fun getMaskDao(): MaskDao

    companion object {
        @Volatile private var instance: MaskDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MaskDatabase::class.java, dbName)
                .build()
    }
}