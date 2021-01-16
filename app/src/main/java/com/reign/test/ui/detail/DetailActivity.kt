package com.reign.test.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.reign.test.R
import com.reign.test.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val PARAM_URL = "URL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url: String? = intent.getStringExtra(PARAM_URL)

        if (url != null) {
            prepareWebView(url)
        } else {
            prepareFeedBack()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun prepareWebView(url: String) {
        binding.detailWebView.settings.javaScriptEnabled = true
        binding.detailWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        binding.detailWebView.loadUrl(url)
    }

    private fun prepareFeedBack() {
        binding.detailFeedbackMsg.visibility = View.VISIBLE
        binding.detailFeedbackMsg.text = getString(R.string.detail_feedBack_msg)
    }

}