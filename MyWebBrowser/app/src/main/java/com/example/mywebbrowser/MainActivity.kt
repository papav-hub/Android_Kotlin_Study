package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply{
            settings.javaScriptEnabled = true // 자바 스크립트를 동작시키도록 javaScriptEnabled 기능을 활성화해야 함
            webViewClient = WebViewClient() // webViewClient를 클라이언트로 지정하지 않으면 웹뷰가 아니고 자체 웹 브라우저가 동작하게 됨
        }
        webView.loadUrl("http://www.google.com")

        urlEditText.setOnEditorActionListener{_, actionId, _ -> // 에디트 텍스트에 글자가 입력될 때마다 호출 // 반응하지 않는 경우 _로 표기
            if(actionId == EditorInfo.IME_ACTION_SEARCH){ // 검색 버튼이 눌렸는지 확인
                webView.loadUrl(urlEditText.text.toString())
                true
            }else{
                false
            }
        }


        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){ // 웹뷰가 이전 페이지로 갈 수 있따면
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if(intent.resolveActivity(packageManager)!=null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                webView.url?.let { sendSMS("031-123-4567", it) }
                return true
            }
            R.id.action_email -> {
                webView.url?.let { email("test@example.com", it) }
                return true
            }
        }
        return super.onOptionsItemSelected(item) // when 문에서 분기하지 않은 예외의 경우에는 super 메서드를 호출하는 것이 안드로이드 시스템의 규칙
    }



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                webView.url?.let { share(it) }
            }
            R.id.action_browser -> {
                webView.url?.let { share(it) }
            }
        }
        return super.onContextItemSelected(item)
    }
}