package com.tonny.employeeadmin.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tonny.employeeadmin.data.Employee
import com.tonny.employeeadmin.data.EmployeeRepo
import io.reactivex.android.schedulers.AndroidSchedulers

sealed class Validation {
    object DepartmentMissing : Validation()
    object FirstNameEmpty : Validation()
    object LastNameEmpty : Validation()
    object EmailEmpty : Validation()
    object IstoogeEmpty : Validation()
    object PasswordDontMatch : Validation()
    object RoleEmpty : Validation()
    object Ok : Validation()
}

class EmployeeDetailsViewModel @ViewModelInject constructor(
    private val employeeRepo: EmployeeRepo
) : ViewModel() {

    val roles = MutableLiveData<List<String>>(employeeRepo.getRoles())
    val departments = MutableLiveData<List<String>>(employeeRepo.departments())
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val istooge = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val department = MutableLiveData<String>()
    val role = MutableLiveData<String>()
    val isCreateMode = MutableLiveData<Boolean>(false)
    val validations = MutableLiveData<Validation>()

    private val employee = MutableLiveData<Employee>()

    fun initEmployeeById(employeeId: Long) {
        val createMode = employeeId == -1L
        isCreateMode.postValue(createMode)
        if (createMode) return
        employeeRepo
            .getEmployeeById(employeeId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(::setEmployee)
            .subscribe()
    }

    private fun setEmployee(it: Employee) {
        employee.value = it
        firstName.value = it.firstName
        lastName.value = it.lastName
        email.value = it.email
        istooge.value = it.itooget
        password.value = it.password
        passwordConfirmation.value = it.password
        department.value = it.department
        role.value = it.role
    }

    fun updateEmployee() {
        if (isCreateMode.value == true) {
            val new = Employee(
                firstName = firstName.value ?: "",
                lastName = lastName.value ?: "",
                email = email.value ?: "",
                itooget = istooge.value ?: "",
                password = password.value ?: "",
                department = department.value ?: "",
                role = role.value ?: ""
            )
            val validation = validate(new, onSuccess = {
                employeeRepo
                    .createEmployee(new)
                    .subscribe()
            })
            validations.value = validation

        } else {
            val update = employee.value?.copy(
                firstName = firstName.value ?: "",
                lastName = lastName.value ?: "",
                email = email.value ?: "",
                itooget = istooge.value ?: "",
                password = password.value ?: "",
                department = department.value ?: "",
                role = role.value ?: ""
            ) ?: return

            val validation = validate(update, onSuccess = {
                employeeRepo
                    .updateEmployee(update)
                    .subscribe()
            })
            validations.value = validation
        }
    }

    private fun validate(
        employee: Employee,
        onSuccess: () -> Unit
    ): Validation {
        val validation = doValidation(employee)
        if (validation == Validation.Ok) {
            onSuccess()
        }
        return validation
    }

    private fun doValidation(employee: Employee): Validation = when {
        employee.firstName.isBlank() -> Validation.FirstNameEmpty
        employee.lastName.isBlank() -> Validation.LastNameEmpty
        employee.email.isBlank() -> Validation.EmailEmpty
        employee.password.isBlank() -> Validation.PasswordDontMatch
        employee.role.isBlank() -> Validation.RoleEmpty
        employee.itooget.isBlank() -> Validation.IstoogeEmpty
        employee.department.isBlank() || employee.department.toLowerCase()
            .contains("none") -> Validation.DepartmentMissing
        else -> Validation.Ok
    }

    fun setRole(item: String?) {
        if (item == null) return
        role.value = item
    }
}