package com.tonny.employeeadmin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class AdminDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AdminDatabase? = null

        fun getInstance(context: Context): AdminDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AdminDatabase::class.java,
            "AdminDatabase.db"
        ).build()
    }
}