package com.sanmer.geomag.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.sanmer.geomag.database.dao.RecordDao
import com.sanmer.geomag.database.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context,
                AppDatabase::class.java, "geomag")
                .addMigrations(
                    MIGRATION_1_2,
                    MIGRATION_2_3,
                    MIGRATION_3_4,
                    MIGRATION_4_5
                )
                .build()
        }

        private val MIGRATION_1_2 = Migration(1, 2) {
            it.execSQL("DROP TABLE records")
            it.execSQL("CREATE TABLE IF NOT EXISTS records (" +
                    "id REAL NOT NULL, " +
                    "model TEXT NOT NULL, " +
                    "time TEXT NOT NULL, " +
                    "altitude REAL NOT NULL, " +
                    "latitude REAL NOT NULL, " +
                    "longitude REAL NOT NULL, " +
                    "declination REAL NOT NULL, " +
                    "declinationSV REAL NOT NULL, " +
                    "inclination REAL NOT NULL, " +
                    "inclinationSV REAL NOT NULL, " +
                    "horizontalIntensity REAL NOT NULL, " +
                    "horizontalSV REAL NOT NULL, " +
                    "northComponent REAL NOT NULL, " +
                    "northSV REAL NOT NULL, " +
                    "eastComponent REAL NOT NULL, " +
                    "eastSV REAL NOT NULL, " +
                    "verticalComponent REAL NOT NULL, " +
                    "verticalSV REAL NOT NULL, " +
                    "totalIntensity REAL NOT NULL, " +
                    "totalSV REAL NOT NULL, " +
                    "PRIMARY KEY(id))")
        }

        private val MIGRATION_2_3 = Migration(2, 3) {
            it.execSQL("DROP TABLE records")
            it.execSQL("CREATE TABLE IF NOT EXISTS records (" +
                    "model TEXT NOT NULL, " +
                    "time TEXT NOT NULL, " +
                    "altitude REAL NOT NULL, " +
                    "latitude REAL NOT NULL, " +
                    "longitude REAL NOT NULL, " +
                    "declination REAL NOT NULL, " +
                    "declinationSV REAL NOT NULL, " +
                    "inclination REAL NOT NULL, " +
                    "inclinationSV REAL NOT NULL, " +
                    "horizontalIntensity REAL NOT NULL, " +
                    "horizontalSV REAL NOT NULL, " +
                    "northComponent REAL NOT NULL, " +
                    "northSV REAL NOT NULL, " +
                    "eastComponent REAL NOT NULL, " +
                    "eastSV REAL NOT NULL, " +
                    "verticalComponent REAL NOT NULL, " +
                    "verticalSV REAL NOT NULL, " +
                    "totalIntensity REAL NOT NULL, " +
                    "totalSV REAL NOT NULL, " +
                    "PRIMARY KEY(model, time, altitude, latitude, longitude))")
        }

        private val MIGRATION_3_4 = Migration(3, 4) {
            it.execSQL("DROP TABLE records")
            it.execSQL("CREATE TABLE IF NOT EXISTS records (" +
                    "model TEXT NOT NULL, " +
                    "time TEXT NOT NULL, " +
                    "altitude REAL NOT NULL, " +
                    "latitude REAL NOT NULL, " +
                    "longitude REAL NOT NULL, " +
                    "declination REAL NOT NULL, " +
                    "declinationSV REAL NOT NULL, " +
                    "inclination REAL NOT NULL, " +
                    "inclinationSV REAL NOT NULL, " +
                    "horizontalIntensity REAL NOT NULL, " +
                    "horizontalSV REAL NOT NULL, " +
                    "northComponent REAL NOT NULL, " +
                    "northSV REAL NOT NULL, " +
                    "eastComponent REAL NOT NULL, " +
                    "eastSV REAL NOT NULL, " +
                    "verticalComponent REAL NOT NULL, " +
                    "verticalSV REAL NOT NULL, " +
                    "totalIntensity REAL NOT NULL, " +
                    "totalSV REAL NOT NULL, " +
                    "PRIMARY KEY(model, time, altitude, latitude, longitude))")
        }

        private val MIGRATION_4_5 = Migration(4, 5) {
            it.execSQL("DROP TABLE records")
            it.execSQL("CREATE TABLE IF NOT EXISTS records (" +
                    "model TEXT NOT NULL, " +
                    "time TEXT NOT NULL, " +
                    "altitude REAL NOT NULL, " +
                    "latitude REAL NOT NULL, " +
                    "longitude REAL NOT NULL, " +
                    "x REAL NOT NULL, " +
                    "xDot REAL NOT NULL, " +
                    "y REAL NOT NULL, " +
                    "yDot REAL NOT NULL, " +
                    "z REAL NOT NULL, " +
                    "zDot REAL NOT NULL, " +
                    "h REAL NOT NULL, " +
                    "hDot REAL NOT NULL, " +
                    "f REAL NOT NULL, " +
                    "fDot REAL NOT NULL, " +
                    "d REAL NOT NULL, " +
                    "dDot REAL NOT NULL, " +
                    "i REAL NOT NULL, " +
                    "iDot REAL NOT NULL, " +
                    "PRIMARY KEY(model, time, altitude, latitude, longitude))")
        }
    }
}