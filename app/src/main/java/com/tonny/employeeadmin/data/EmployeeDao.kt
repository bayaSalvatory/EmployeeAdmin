package com.tonny.employeeadmin.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees")
    fun all(): Observable<List<Employee>>

    @Query("SELECT * FROM employees WHERE id =:id")
    fun get(id: Long): Observable<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: Employee): Completable

    @Update
    fun update(employee: Employee): Completable

    @Delete
    fun delete(employee: Employee): Completable
}