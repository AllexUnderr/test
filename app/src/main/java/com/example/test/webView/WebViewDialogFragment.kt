package com.example.test.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.example.test.R
import com.example.test.databinding.FragmentWebviewBinding

class WebViewDialogFragment : DialogFragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding: FragmentWebviewBinding get() = requireNotNull(_binding)

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

        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState)
        else
            binding.webView.loadUrl("https://youtube.com")

        CookieManager.getInstance().setAcceptCookie(true)
        setWebViewSettings()
    }

    private fun setWebViewSettings() {
        with(binding.webView) {
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
        const val TAG = "WebViewDialogFragment"
    }
}