package com.example.cartrack.dao

import androidx.room.*
import com.example.cartrack.entity.AppUser

@Dao
interface AppUserDao {

    @Query("SELECT * FROM user WHERE username = :id LIMIT 1")
    suspend fun findDirectorById(id: String): AppUser?

    @Query("SELECT * FROM user WHERE username = :fullName LIMIT 1")
    suspend fun findDirectorByName(fullName: String?): AppUser?

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    abstract fun findByUserNameAndPassword(email: String, password: String): AppUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users_AppUser: AppUser): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(users_AppUser: AppUser)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM user ORDER BY username ASC")
    val allUserUsers: List<AppUser>
}