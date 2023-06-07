package com.example.test.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.test.databinding.ActivityMainBinding
import com.example.test.diary.DiaryFragment
import com.example.test.stopwatch.StopwatchFragment
import com.example.test.webView.WebViewDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewModel.init()

        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.openWebViewCommand.observe(this) {
            if (it)
                WebViewDialogFragment().show(supportFragmentManager, WebViewDialogFragment.TAG)
            else
                supportFragmentManager.commit {
                    add(binding.stopwatchFragmentContainerView.id, StopwatchFragment())
                    add(binding.trainingDiaryFragmentContainerView.id, DiaryFragment())
                }
        }
    }
}