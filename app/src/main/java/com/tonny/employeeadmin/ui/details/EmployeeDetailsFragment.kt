package com.tonny.employeeadmin.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tonny.employeeadmin.R
import com.tonny.employeeadmin.databinding.EmployeeDetailsFragmentBinding
import com.tonny.employeeadmin.ui.dialog.RolesDialogFragment
import com.tonny.employeeadmin.util.hideKeyboard
import com.tonny.employeeadmin.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.employee_details_fragment.*

@AndroidEntryPoint
class EmployeeDetailsFragment : Fragment() {

    private val arg: EmployeeDetailsFragmentArgs by navArgs()

    private val viewModel: EmployeeDetailsViewModel by viewModels()
    private lateinit var binding: EmployeeDetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initEmployeeById(arg.employeeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EmployeeDetailsFragmentBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@EmployeeDetailsFragment
        }
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        role_btn.setOnClickListener {
            RolesDialogFragment.display(parentFragmentManager, viewModel.role.value ?: "").apply {
                callbacks = object : RolesDialogFragment.Callbacks {
                    override fun onItemSelected(item: String) {
                        viewModel.setRole(item)
                    }
                }
            }
        }

        cancel_btn.setOnClickListener {
            findNavController().popBackStack()
        }

        update_btn.setOnClickListener {
            viewModel.updateEmployee()
        }

        viewModel.validations.observe(viewLifecycleOwner, Observer {
            when (it) {
                Validation.Ok -> {
                    showSnackBar(binding.root, requireContext().getString(R.string.admin_created))
                    findNavController().popBackStack()
                }
                Validation.DepartmentMissing -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.department_empty)
                )
                Validation.FirstNameEmpty -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.first_name_empty)
                )
                Validation.LastNameEmpty -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.last_name_empty)
                )
                Validation.EmailEmpty -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.email_empty)
                )
                Validation.IstoogeEmpty -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.is_too_ge_empty)
                )
                Validation.PasswordDontMatch -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.password_match)
                )
                Validation.RoleEmpty -> showSnackBar(
                    binding.root,
                    requireContext().getString(R.string.role_empty)
                )
            }
        })
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }
}