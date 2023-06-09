package com.example.test.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test.databinding.FragmentStopwatchBinding

class StopwatchFragment : Fragment() {

    private var _binding: FragmentStopwatchBinding? = null
    private val binding: FragmentStopwatchBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initViews() {
        var difference = 0L
        var isChronometerRunning = false

        binding.startButton.setOnClickListener {
            if (!isChronometerRunning) {
                binding.stopwatchChronometer.base = difference + SystemClock.elapsedRealtime()
                binding.stopwatchChronometer.start()
            }
            isChronometerRunning = true
        }

        binding.stopButton.setOnClickListener {
            if (isChronometerRunning) {
                binding.stopwatchChronometer.stop()
                difference = binding.stopwatchChronometer.base - SystemClock.elapsedRealtime()
            }
            isChronometerRunning = false
        }

        binding.resetButton.setOnClickListener {
            binding.stopwatchChronometer.base = SystemClock.elapsedRealtime()
        }
    }
}