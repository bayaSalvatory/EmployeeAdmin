package com.tonny.employeeadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.tonny.employeeadmin.data.Employee
import com.tonny.employeeadmin.databinding.EmployeesFragmentBinding
import com.tonny.employeeadmin.util.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.employees_fragment.*

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private val viewModel: EmployeesViewModel by viewModels()
    private lateinit var binding: EmployeesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EmployeesFragmentBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@EmployeesFragment
        }
        context ?: return binding.root
        val employeeAdapter = EmployeesAdapter().apply {
            callback = object : EmployeesAdapter.Callbacks {
                override fun onDelete(employee: Employee) {
                    viewModel.deleteEmployee(employee)
                }
            }
        }
        val itemTouchHelper =
            ItemTouchHelper(SwipeToDeleteCallback(requireContext(), employeeAdapter))

        binding.employeesLv.adapter = employeeAdapter
        itemTouchHelper.attachToRecyclerView(binding.employeesLv)
        subscribeUi(employeeAdapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab_btn.setOnClickListener {
            val directions =
                EmployeesFragmentDirections.actionEmployeesFragmentToEmployeeDetailsFragment(-1L)
            findNavController().navigate(directions)
        }
    }

    private fun subscribeUi(employeeAdapter: EmployeesAdapter) {
        viewModel.employees.observe(viewLifecycleOwner, Observer {
            employeeAdapter.submitList(it)
        })
    }
}