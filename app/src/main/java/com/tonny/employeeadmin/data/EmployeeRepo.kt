package com.tonny.employeeadmin.data

import io.reactivex.Completable
import io.reactivex.Observable

interface EmployeeRepo {

    /**
     * @return list of employees
     */
    fun getEmployees(): Observable<List<Employee>>

    /**
     * updates employee
     */
    fun updateEmployee(employee: Employee): Completable

    /**
     * Get
     */
    fun getEmployeeById(employeeId: Long): Observable<Employee>

    /**
     *
     */
    fun createEmployee(employee: Employee): Completable

    /**
     *
     */
    fun deleteEmployee(employee: Employee): Completable

    /**
     *
     */
    fun departments(): List<String>

    /**
     *
     */
    fun getRoles(): List<String>
}