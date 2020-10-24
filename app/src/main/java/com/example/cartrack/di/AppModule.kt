package com.example.cartrack.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.dao.AppUserDao
import com.example.cartrack.util.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var context: CartrackApplication) {

    @Provides
    fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("APP_USER",Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "cartrack.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesAppUserDao(database: AppDatabase): AppUserDao {
        return database.appUserDao()
    }
}