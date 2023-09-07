package com.sanmer.geomag.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.sanmer.geomag.database.dao.RecordDao
import com.sanmer.geomag.database.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context,
                AppDatabase::class.java, "geomag")
                .addMigrations(
                    MIGRATION_1_2,
                    MIGRATION_2_3
                )
                .build()
        }

        private val MIGRATION_1_2 = Migration(1, 2) {
            it.execSQL("CREATE TABLE IF NOT EXISTS records_new (" +
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

            it.execSQL("INSERT INTO records_new (" +
                    "id, model, time, altitude, latitude, longitude, " +
                    "declination, declinationSV, inclination, inclinationSV, " +
                    "horizontalIntensity, horizontalSV, northComponent, northSV, " +
                    "eastComponent, eastSV, verticalComponent, verticalSV, " +
                    "totalIntensity, totalSV) " +
                    "SELECT " +
                    "id, model, time, altitude, latitude, longitude, " +
                    "declination, declination_sv, inclination, inclination_sv, " +
                    "horizontal_intensity, horizontal_sv, north_component, north_sv, " +
                    "east_component, east_sv, vertical_component, vertical_sv, " +
                    "total_intensity, total_sv " +
                    "FROM records")

            it.execSQL("DROP TABLE records")
            it.execSQL("ALTER TABLE records_new RENAME TO records")
        }

        private val MIGRATION_2_3 = Migration(2, 3) {
            it.execSQL("CREATE TABLE IF NOT EXISTS records_new (" +
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

            it.execSQL("INSERT INTO records_new (" +
                    "model, time, altitude, latitude, longitude, " +
                    "declination, declinationSV, inclination, inclinationSV, " +
                    "horizontalIntensity, horizontalSV, northComponent, northSV, " +
                    "eastComponent, eastSV, verticalComponent, verticalSV, " +
                    "totalIntensity, totalSV) " +
                    "SELECT " +
                    "model, time, altitude, latitude, longitude, " +
                    "declination, declinationSV, inclination, inclinationSV, " +
                    "horizontalIntensity, horizontalSV, northComponent, northSV, " +
                    "eastComponent, eastSV, verticalComponent, verticalSV, " +
                    "totalIntensity, totalSV " +
                    "FROM records")

            it.execSQL("DROP TABLE records")
            it.execSQL("ALTER TABLE records_new RENAME TO records")
        }
    }
}