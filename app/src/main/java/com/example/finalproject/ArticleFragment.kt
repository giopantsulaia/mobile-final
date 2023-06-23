package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

private const val URL = "Url"

class ArticleFragment : Fragment() {
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false);

        val webView = view.findViewById<WebView>(R.id.web_view);
        val progressBar = view.findViewById<ProgressBar>(R.id.article_fragment_progress_bar)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }

        webView.loadUrl(url!!);

        return view;
    }

    companion object {
        fun newInstance(url: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
    }
}