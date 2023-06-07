package com.example.test.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.RecyclerviewExerciseItemBinding
import com.example.test.diary.model.Exercise

class ExerciseRecyclerAdapter :
    ListAdapter<Exercise, ExerciseRecyclerAdapter.ViewHolder>(ExerciseItemCallback()) {

    class ViewHolder(binding: RecyclerviewExerciseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameTextView = binding.nameTextView
        val weightTextView = binding.weightTextView
        val repetitionTextView = binding.repetitionTextView
        val dateTextView = binding.dateTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecyclerviewExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = getItem(position)

        with(holder) {
            nameTextView.text = exercise.name
            weightTextView.text = exercise.weight.toString()
            repetitionTextView.text = exercise.repetition.toString()
            dateTextView.text = exercise.date.toString()
        }
    }

    private class ExerciseItemCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
            oldItem == newItem
    }
}