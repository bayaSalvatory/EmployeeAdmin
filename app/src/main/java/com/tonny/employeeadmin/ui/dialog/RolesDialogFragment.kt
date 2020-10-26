package com.tonny.employeeadmin.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tonny.employeeadmin.databinding.DialogRolesBinding
import com.tonny.employeeadmin.ui.details.EmployeeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RolesDialogFragment : DialogFragment() {

    private val viewModel: EmployeeDetailsViewModel by viewModels()
    private lateinit var binding: DialogRolesBinding

    interface Callbacks {
        fun onItemSelected(item: String)
    }

    var callbacks: Callbacks? = null

    companion object {
        fun display(fragmentManager: FragmentManager, selected: String = ""): RolesDialogFragment {
            val frag = RolesDialogFragment()
            frag.arguments = Bundle().apply { putString("selected", selected) }
            frag.show(fragmentManager, "rolesDialog")
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setRole(arguments?.getString("selected"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogRolesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RoleAdapter()
        binding.rolesRv.adapter = adapter
        adapter.onItemSelectionListener = object : RoleAdapter.ItemSelectionListener {
            override fun onItemSelected(item: String) {
                callbacks?.onItemSelected(item)
            }
        }

        viewModel.roles.observe(viewLifecycleOwner, Observer { roles ->
            adapter.submitList(roles)
            adapter.selectItem(viewModel.role.value ?: "")
        })

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.okBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        showFullScreen()
    }

    private fun showFullScreen() {
        dialog?.apply {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(width, height)
        }
    }
}