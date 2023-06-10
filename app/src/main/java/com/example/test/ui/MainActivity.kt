package com.example.test.ui

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.test.R
import com.example.test.databinding.ActivityMainBinding
import com.example.test.diary.DiaryFragment
import com.example.test.network.ErrorDialogFragment
import com.example.test.stopwatch.StopwatchFragment
import com.example.test.webView.BrowserDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by viewModel()

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        configureFirebase()

        bindViewModel()
    }

    private fun configureFirebase() {
        analytics = Firebase.analytics
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            viewModel.init()
        }
    }

    private fun bindViewModel() {
        viewModel.openPlugCommand.observe(this) {
            if (it) {
                supportFragmentManager.commit {
                    add(binding.stopwatchFragmentContainerView.id, StopwatchFragment())
                    add(binding.trainingDiaryFragmentContainerView.id, DiaryFragment())
                }
            }
        }

        viewModel.url.observe(this) {
            supportFragmentManager.setFragmentResult(
                BrowserDialogFragment.REQUEST_KEY,
                bundleOf(BrowserDialogFragment.TAG to it)
            )
            BrowserDialogFragment().show(supportFragmentManager, BrowserDialogFragment.TAG)
        }

        viewModel.showErrorPageCommand.observe(this) {
            if (it)
                ErrorDialogFragment().show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }
}