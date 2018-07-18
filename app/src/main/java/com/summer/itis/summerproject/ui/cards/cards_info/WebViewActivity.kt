package com.summer.itis.summerproject.ui.cards.cards_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebSettings.PluginState
import android.webkit.WebView
import android.webkit.WebViewClient
import com.summer.itis.summerproject.ui.base.BaseActivity

/**
 * Created by Home on 14.07.2018.
 */
class WebViewActivity : BaseActivity() {

    companion object {
        fun start(context: Context,url: String){
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("URL",url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var webView: WebView = WebView(this)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        webView.getSettings().setJavaScriptEnabled(true)
        webView.getSettings().setPluginState(PluginState.ON)
        webView.loadUrl(intent.getStringExtra("URL"))
        setContentView(webView)
    }
}