package com.tonny.employeeadmin.util

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.tonny.employeeadmin.R

@BindingAdapter(
    value = ["items", "selectedItem", "selectedItemAttrChanged"],
    requireAll = false
)
fun spinnerBinding(
    spinner: Spinner,
    items: List<String>?,
    selectedItem: String?,
    listener: InverseBindingListener
) {
    if (items == null) return
    spinner.adapter = ArrayAdapter(spinner.context, R.layout.spinner_item_view, items)
    setCurrentSelection(spinner, selectedItem)
    setSpinnerListener(spinner, listener)
}

@InverseBindingAdapter(attribute = "selectedItem")
fun getSelectedItem(spinner: Spinner): String {
    return spinner.selectedItem as String
}

private fun setSpinnerListener(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
            listener.onChange()

        override fun onNothingSelected(adapterView: AdapterView<*>) = listener.onChange()
    }
}

private fun setCurrentSelection(spinner: Spinner, selectedItem: String?): Boolean {
    if (selectedItem == null || selectedItem.isEmpty() || selectedItem.isBlank()) {
        spinner.setSelection(0)
        return true
    }

    for (index in 0 until spinner.adapter.count) {
        if (spinner.getItemAtPosition(index) == selectedItem) {
            spinner.setSelection(index)
            return true
        }
    }
    return false
}
