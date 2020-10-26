package com.tonny.employeeadmin.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tonny.employeeadmin.data.Employee
import com.tonny.employeeadmin.databinding.ItemEmployeeBinding

class EmployeesAdapter : ListAdapter<Employee, RecyclerView.ViewHolder>(EmployeeDiffCallback()) {

    interface Callbacks {
        fun onDelete(employee: Employee)
    }

    var callback: Callbacks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmployeeViewHolder(
            ItemEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val employee = getItem(position)
        (holder as EmployeeViewHolder).bind(employee)
    }

    fun delete(position: Int) {
        callback?.onDelete(getItem(position))
    }

    class EmployeeViewHolder(
        private val binding: ItemEmployeeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clickListener = View.OnClickListener {
                binding.employee?.let { plant ->
                    navigateToEmployee(plant, it)
                }
            }
        }

        private fun navigateToEmployee(employee: Employee, it: View?) {
            if (employee.id == null) return
            val directions =
                EmployeesFragmentDirections.actionEmployeesFragmentToEmployeeDetailsFragment(
                    employee.id
                )
            it?.findNavController()
                ?.navigate(directions)
        }

        fun bind(item: Employee) {
            binding.apply {
                employee = item
                executePendingBindings()
            }
        }
    }
}

private class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}