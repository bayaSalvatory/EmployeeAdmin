package com.tonny.employeeadmin.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val role: String = "",
    val department: String = "",
    val itooget: String = ""
) {
    val fullName: String
        get() = "$firstName, $lastName"
}
