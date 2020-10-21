package com.example.cartrack.entitys

import androidx.room.*
import com.example.cartrack.entitys.UserOfApp.Companion.Email
import com.example.cartrack.entitys.UserOfApp.Companion.TABLE_NAME


@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [Email], unique = true)])
data class UserOfApp(
    @PrimaryKey val UID:Int,
    @ColumnInfo(name = "User_Name") val user_name: String?,
    @ColumnInfo(name = "Phone") val user_phone: String?,
    @ColumnInfo(name = "Address") val user_address: String?,
    @ColumnInfo(name = "Password") val user_password: String?,
    @ColumnInfo(name = Email) var user_email: String) {

    constructor() : this(0,"", "", "","","")

    companion object {
        const val TABLE_NAME = "Users_App"
        const val Email = "Email"
    }
}
