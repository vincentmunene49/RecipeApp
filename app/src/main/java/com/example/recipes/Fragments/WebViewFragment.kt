package com.example.recipes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.recipes.databinding.WebViewFragmentBinding

class WebViewFragment : Fragment() {
    private var _binding: WebViewFragmentBinding? = null
    private val binding: WebViewFragmentBinding
        get() = _binding!!
    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var web_view:WebView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WebViewFragmentBinding.inflate(inflater, container, false)

        web_view = binding.webView

        web_view.apply {
            loadUrl(args.source)
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
//        (activity as AppCompatActivity?)!!.setSupportActionBar(toolBar)
//        (activity as AppCompatActivity).supportActionBar?.title = "Ingredients"
        (activity as AppCompatActivity?)!!.onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(web_view.canGoBack()){
                    web_view.goBack()
                }else{
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }

        })



        return binding.root

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}