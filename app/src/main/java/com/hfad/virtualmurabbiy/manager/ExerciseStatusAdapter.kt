package com.hfad.virtualmurabbiy.manager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter : RecyclerView.Adapter<ExerciseStatusAdapter.MyViewHolder>() {

    var dataList = emptyList<ExerciseModel>()

    class MyViewHolder(internal val binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = dataList[position]
        val binding = holder.binding

        binding.tvItem.text = model.getId().toString()
        when {
            model.getIsSelected() -> {
                binding.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_thin_color_accent_border)
                binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))

            }
            model.getIsCompleted() -> {
                binding.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                R.drawable.item_circular_color_blue_background)
                binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                binding.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_gray_background)
                binding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    fun setData(exerciseModel: List<ExerciseModel>) {
        this.dataList = exerciseModel
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}