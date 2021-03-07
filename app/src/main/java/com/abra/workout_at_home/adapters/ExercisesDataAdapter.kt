package com.abra.workout_at_home.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abra.workout_at_home.R
import com.abra.workout_at_home.data.ExercisesData
import com.abra.workout_at_home.databinding.ItemExerciseInfoBinding
import com.bumptech.glide.Glide

class ExercisesDataAdapter(private val workTime: Long) :
    RecyclerView.Adapter<ExercisesDataAdapter.ExercisesDataViewHolder>() {
    private var exercisesList = listOf<ExercisesData>()
    lateinit var onItemClickListener: (ExercisesData) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesDataViewHolder {
        val binding =
            ItemExerciseInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExercisesDataViewHolder(binding, onItemClickListener, workTime)
    }

    override fun onBindViewHolder(holder: ExercisesDataViewHolder, position: Int) {
        holder.bind(exercisesList[position])
    }

    override fun getItemCount() = exercisesList.size

    fun updateList(list: List<ExercisesData>) {
        exercisesList = list
        notifyDataSetChanged()
    }

    fun getList() = exercisesList

    class ExercisesDataViewHolder(
        private val binding: ItemExerciseInfoBinding,
        private val onItemClickListener: (ExercisesData) -> Unit,
        private val workTime: Long
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(info: ExercisesData) {
            with(binding) {
                tvExerciseName.text = info.name
                tvExerciseTime.text =
                    "${binding.root.resources.getString(R.string.work_time)} $workTime ${
                        binding.root.resources.getString(R.string.seconds)
                    }"
                Glide.with(binding.root.context).load(Uri.parse(info.path)).into(ivExercise)
            }
            itemView.setOnClickListener {
                onItemClickListener.invoke(info)
            }
        }
    }
}