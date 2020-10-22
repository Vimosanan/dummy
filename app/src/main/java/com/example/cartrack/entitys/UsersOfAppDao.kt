package com.example.cartrack.entitys

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersOfAppDao {

    @Query("SELECT * FROM Users_App WHERE User_Name = :id LIMIT 1")
    suspend fun findDirectorById(id: String): UserOfApp?

    @Query("SELECT * FROM Users_App WHERE User_Name = :fullName LIMIT 1")
    suspend fun findDirectorByName(fullName: String?): UserOfApp?

    @Query("SELECT * FROM Users_App WHERE Email = :email AND Password = :password LIMIT 1")
    abstract fun getSingleUser(email: String, password: String?): UserOfApp?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(users_App: UserOfApp): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(users_App: UserOfApp)

    @Query("DELETE FROM Users_App")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM Users_App ORDER BY User_Name ASC")
    val allUsers: List<UserOfApp>
}