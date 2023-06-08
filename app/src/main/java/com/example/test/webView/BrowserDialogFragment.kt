package com.example.test.webView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.example.test.R
import com.example.test.databinding.FragmentWebviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrowserDialogFragment : DialogFragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding: FragmentWebviewBinding get() = requireNotNull(_binding)

    private val viewModel: BrowserViewModel by viewModel()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.webView.saveState(outState)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = WebViewClient()

        setUrlResultListener()

        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState)
        else
            viewModel.url.observe(viewLifecycleOwner) {
                binding.webView.loadUrl(it)
            }

        CookieManager.getInstance().setAcceptCookie(true)
        setWebViewSettings()
    }

    private fun setUrlResultListener() {
        parentFragmentManager.setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            viewModel.setUrl(
                bundle.getString(TAG) ?: ""
            )
        }
    }

    private fun setWebViewSettings() {
        with(binding.webView) {
            @SuppressLint("SetJavaScriptEnabled")
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.databaseEnabled = true
            settings.setSupportZoom(false)
            settings.allowFileAccess = true
            settings.allowContentAccess = true
        }
    }

    companion object {
        const val REQUEST_KEY = "url"

        const val TAG = "BrowserDialogFragment"
    }
}