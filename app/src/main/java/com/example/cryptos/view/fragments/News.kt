package com.example.cryptos.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptos.view.adapter.NewsListAdapter
import com.example.cryptos.databinding.FragmentNewsBinding
import com.example.cryptos.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class News : Fragment() {

    private lateinit var model: NewsViewModel
    private lateinit var binding: FragmentNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this)[NewsViewModel::class.java]
        binding.viewModel = model
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.recyclerview.adapter = NewsListAdapter(
            NewsListAdapter.OnClickListener { article ->
                Log.e("article", article.title)
            }
        )
        return binding.root
    }

}