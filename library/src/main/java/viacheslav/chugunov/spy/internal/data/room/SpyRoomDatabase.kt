package viacheslav.chugunov.spy.internal.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import viacheslav.chugunov.spy.internal.data.room.dao.SpyEventDao
import viacheslav.chugunov.spy.internal.data.room.entity.SpyEventEntity
import viacheslav.chugunov.spy.internal.data.room.entity.SpyMetaEntity

@Database(entities = [SpyEventEntity::class, SpyMetaEntity::class], version = 1)
internal abstract class SpyRoomDatabase : RoomDatabase() {
    abstract val eventDao: SpyEventDao

    companion object {
        @Volatile
        private var INSTANCE: SpyRoomDatabase? = null

        @Synchronized
        fun getInstance(
            applicationContext: Context,
            databaseName: String
        ): SpyRoomDatabase {
            if (INSTANCE != null) return INSTANCE!!
            val newInstance = Room.databaseBuilder(
                applicationContext, SpyRoomDatabase::class.java, databaseName).build()
            INSTANCE = newInstance
            return newInstance
        }
    }

}