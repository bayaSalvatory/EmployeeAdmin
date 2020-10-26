package com.tonny.employeeadmin.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EmployeeRepoImpl @Inject constructor(
    private val adminDatabase: AdminDatabase
) : EmployeeRepo {

    override fun getEmployees(): Observable<List<Employee>> =
        adminDatabase
            .employeeDao()
            .all()
            .subscribeOn(Schedulers.io())

    override fun updateEmployee(employee: Employee) =
        adminDatabase
            .employeeDao()
            .update(employee)
            .subscribeOn(Schedulers.io())

    override fun getEmployeeById(employeeId: Long): Observable<Employee> =
        adminDatabase
            .employeeDao()
            .get(employeeId)
            .subscribeOn(Schedulers.io())

    override fun createEmployee(employee: Employee): Completable =
        adminDatabase
            .employeeDao()
            .insert(employee)
            .subscribeOn(Schedulers.io())

    override fun deleteEmployee(employee: Employee): Completable =
        adminDatabase
            .employeeDao()
            .delete(employee)
            .subscribeOn(Schedulers.io())

    override fun departments(): List<String> =
        listOf(
            "--None Selected--",
            "Engineering",
            "Accounting",
            "Finance",
            "Project Management"
        )

    override fun getRoles(): List<String> =
        listOf(
            "ADMIN",
            "ACCT_PAY",
            "ACCT_RCV",
            "INVENTORY",
            "SALES",
            "ORDERS",
            "CUSTOMER"

        )
}