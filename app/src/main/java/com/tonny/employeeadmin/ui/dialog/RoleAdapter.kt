package com.tonny.employeeadmin.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tonny.employeeadmin.databinding.ItemRadioViewBinding


class RoleAdapter : ListAdapter<String, RecyclerView.ViewHolder>(RadioItemDiffCallback()) {

    interface ItemSelectionListener {
        fun onItemSelected(item: String)
    }

    var onItemSelectionListener: ItemSelectionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RadioItemViewHolder(
            ItemRadioViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var checkedPosition = -1

    fun selectItem(item: String) {
        if (item.isBlank()) return
        selectItemByPosition(getPosition(item))
    }

    private fun getPosition(item: String): Int {
        for (i in 0 until itemCount) {
            if (item == getItem(i)) return i
        }
        return -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as String
        (holder as RadioItemViewHolder).apply {
            bind(item, position == checkedPosition)
            callback = object : RadioItemViewHolder.Callbacks {
                override fun onItemChecked(position: Int) {
                    selectItemByPosition(position)
                    onItemSelectionListener?.onItemSelected(getItem(position))
                }
            }
        }
    }

    private fun selectItemByPosition(position: Int) {
        val oldPosition = checkedPosition
        checkedPosition = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(position)
    }

    class RadioItemViewHolder(private val binding: ItemRadioViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        interface Callbacks {
            fun onItemChecked(position: Int)
        }

        var callback: Callbacks? = null

        init {
            binding.radioBtn.setOnCheckedChangeListener { _, b ->
                if (b) callback?.onItemChecked(adapterPosition)
            }
        }

        fun bind(role: String, isChecked: Boolean) {
            binding.name = role
            binding.checked = isChecked
        }
    }
}

private class RadioItemDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}