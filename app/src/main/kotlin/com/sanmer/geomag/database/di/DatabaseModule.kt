package com.sanmer.geomag.database.di

import android.content.Context
import com.sanmer.geomag.database.AppDatabase
import com.sanmer.geomag.database.dao.RecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.build(context)

    @Provides
    @Singleton
    fun providesRecordDao(db: AppDatabase): RecordDao = db.recordDao()
}