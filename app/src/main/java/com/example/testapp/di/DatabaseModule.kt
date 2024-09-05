package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.data.database.AppDatabase
import com.example.testapp.data.database.DAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providesDAO(appDatabase: AppDatabase): DAO{
        return appDatabase.getDao()
    }
}