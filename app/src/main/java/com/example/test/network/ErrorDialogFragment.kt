package com.example.test.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.test.R
import com.example.test.databinding.FragmentErrorBinding

class ErrorDialogFragment : DialogFragment() {
    private var _binding: FragmentErrorBinding? = null
    private val binding: FragmentErrorBinding get() = requireNotNull(_binding)

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    companion object {
        const val TAG = "ErrorDialogFragment"
    }
}