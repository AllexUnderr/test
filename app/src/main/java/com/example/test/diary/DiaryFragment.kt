package com.example.test.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.test.converter.LocalDateConverter
import com.example.test.databinding.FragmentDiaryBinding
import com.example.test.diary.model.Exercise
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null
    private val binding: FragmentDiaryBinding get() = requireNotNull(_binding)

    private val viewModel: DiaryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initViews() {
        binding.submitButton.setOnClickListener {
            viewModel.onSubmitButton(getExercise())
        }
    }

    private fun bindViewModel() {
        val exerciseAdapter = ExerciseRecyclerAdapter()
        binding.exerciseRecyclerView.adapter = exerciseAdapter
        viewModel.exercises.observe(viewLifecycleOwner) {
            exerciseAdapter.submitList(it)
        }

        viewModel.hasEmptyField.observe(viewLifecycleOwner) {
            binding.hasEmptyFieldTextView.isVisible = it
        }
    }

    private fun getExercise(): Exercise =
        Exercise(
            name = binding.nameEditText.text.toString(),
            weight = binding.weightEditText.text.toString().toIntOrNull() ?: 0,
            repetition = binding.repetitionEditText.text.toString().toIntOrNull() ?: 0,
            date = stringToDate(binding.dateEditText.text.toString())
        )

    private fun stringToDate(string: String) =
        if (string.isEmpty())
            LocalDate.now()
        else
            LocalDateConverter.convert(string)
}