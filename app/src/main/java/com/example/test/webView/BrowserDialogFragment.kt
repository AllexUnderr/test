package com.example.test.webView

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentDialog
import androidx.fragment.app.DialogFragment
import com.example.test.R
import com.example.test.databinding.FragmentWebviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BrowserDialogFragment : DialogFragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding: FragmentWebviewBinding get() = requireNotNull(_binding)

    private val viewModel: BrowserViewModel by viewModel()

    private var webView: WebView? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.webView.saveState(outState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : ComponentDialog(requireContext(), theme) {
            override fun onBackPressed() {
                if (false)
                    super.onBackPressed()

                if (webView?.canGoBack() == true)
                    webView?.goBack()
            }
        }
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

        setUrlResultListener()

        webView = binding.webView
        webView?.webViewClient = WebViewClient()
        webView?.webChromeClient = WebChromeClient()
        val webSettings = binding.webView.settings
        webSettings.apply {
            @SuppressLint("SetJavaScriptEnabled")
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            domStorageEnabled = true
            databaseEnabled = true
            setSupportZoom(true)
            allowFileAccess = true
            allowContentAccess = true
        }

        if (savedInstanceState != null)
            binding.webView.restoreState(savedInstanceState)
        else
            viewModel.url.observe(viewLifecycleOwner) {
                binding.webView.loadUrl(it)
            }

        webSettings.apply {
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }

        CookieManager.getInstance().setAcceptCookie(true)
    }

    private fun setUrlResultListener() {
        parentFragmentManager.setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            viewModel.setUrl(
                bundle.getString(TAG) ?: ""
            )
        }
    }

    companion object {
        const val REQUEST_KEY = "url"

        const val TAG = "BrowserDialogFragment"
    }
}