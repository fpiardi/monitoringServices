package com.fpiardi.monitoringservices.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fpiardi.monitoringservices.model.SourceDetailError

@Database(
    entities = [SourceDetailError::class],
    exportSchema = false,
    version = 1 // Increment the version number
)
abstract class MonitoringDatabase : RoomDatabase() {
    abstract fun sourceErrorsDao(): SourceDetailErrorDao

    companion object {
        const val DATABASE_NAME = "database.db"

        // For Singleton instantiation
        @Volatile
        private var instance: MonitoringDatabase? = null

        fun getInstance(context: Context): MonitoringDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MonitoringDatabase {
            return Room.databaseBuilder(
                context, MonitoringDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
