package com.example.cartrack.entitys

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserOfApp::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val usersOfAppDao: UsersOfAppDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "UserOfApp.db")
            .build()
    }
}