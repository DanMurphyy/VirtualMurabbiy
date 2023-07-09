package com.hfad.virtualmurabbiy.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.databinding.RowLayoutBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    var datalist = emptyList<HistoryData>()

    class MyViewHolder(internal val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = datalist[position]
        val binding = holder.binding

        binding.tvItem.text = currentItem.date
        binding.tvPosition.text = (position + 1).toString()

        if (position % 2 == 0) {
            binding.rowBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.lightGray))
        } else {
            binding.rowBackground.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,
                R.color.white))
        }
    }

    fun setData(historyData: List<HistoryData>) {
        this.datalist = historyData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}