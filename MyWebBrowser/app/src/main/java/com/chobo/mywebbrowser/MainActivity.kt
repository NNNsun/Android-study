package com.chobo.mywebbrowser
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.chobo.mywebbrowser.databinding.ActivityMainBinding
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import browse
import email
import sendSMS
import share


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //웹뷰기본설정
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {     //웹뷰에 페이지 표시하기
                override fun onPageFinished(view: WebView, url: String) {
                    binding.urlEditText.setText(url)
                }
            }
        }

        binding.webView.loadUrl("https://www.google.com")
        //컨텍스트 메뉴 등록
        registerForContextMenu(binding.webView)

        //검색창에 url을 입력하고 소프트 키보드의 검색아이콘을 클릭하여 웹페이지가 웹뷰에 표시되게 하기
        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            } else {
                false
            }
        }
    }
    //이전페이지 돌아가기
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {    //이전페이지로 갈수있다면
            binding.webView.goBack()        //이전페이지로 이동
        } else {
            super.onBackPressed()           //아니라면 앱 종료
        }
    }

    //옵션메뉴 리소스 지정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    //메뉴작성
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_google, R.id.action_home -> {
                binding.webView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver -> {
                binding.webView.loadUrl("https://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                binding.webView.loadUrl("https://www.daum.net")
                return true
            }
            R.id.action_call->{
                //전화걸기 암시적 인텐트
                val intent= Intent(Intent.ACTION_DIAL)
                intent.data= Uri.parse("tel:031-123-4567")
                if(intent.resolveActivity(packageManager)!=null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text-> {
                //문자 보내기
                binding.webView.url?.let { url ->
                    sendSMS("031-123-4567", url)
                }
                return true
            }
            R.id.action_email-> {
                binding.webView.url?.let { url ->
                    //이메일 보내기
                    email("test@example.com", "좋은 사이트", url)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //컨텍스트 메뉴 작성
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context,menu)
    }
    //컨텍스트 메뉴 클릭 이벤트 처리
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share->{
                //페이지 공유
                binding.webView.url?.let { url-> share(url)
                }
                return true
            }
            R.id.action_browser->{
                //기본 웹 브라우저에서 열기
                binding.webView.url?.let{url-> browse(url)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}