package com.tonny.employeeadmin.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tonny.employeeadmin.data.Employee
import com.tonny.employeeadmin.data.EmployeeRepo
import io.reactivex.android.schedulers.AndroidSchedulers


class EmployeesViewModel @ViewModelInject constructor(
    private val employeeRepo: EmployeeRepo
) : ViewModel() {

    val employees = MutableLiveData<List<Employee>>()

    init {
        refresh()
    }

    private fun refresh() {
        employeeRepo.getEmployees()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { employees.postValue(it) }
            .subscribe()
    }

    fun deleteEmployee(employee: Employee) {
        employeeRepo.deleteEmployee(employee)
            .subscribe()
    }
}