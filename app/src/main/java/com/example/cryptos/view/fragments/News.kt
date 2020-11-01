package com.example.cryptos.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptos.R
import com.example.cryptos.adapter.NewsListAdapter
import com.example.cryptos.network.model.NewsApiStatus.*
import com.example.cryptos.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_ticker.*


@AndroidEntryPoint
class News : Fragment() {

    private lateinit var model: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NewsListAdapter(activity!!.applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        model = ViewModelProvider(this).get(NewsViewModel::class.java)
        model.getNews()
        model.status.observe(viewLifecycleOwner, Observer { NewsApiStatus ->
            when (NewsApiStatus) {
                DONE -> {
                    recyclerView.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE
                    shimmerFrameLayout.stopShimmerAnimation()
                }
                LOADING -> {
                    recyclerView.visibility = View.GONE
                    shimmerFrameLayout.visibility = View.VISIBLE
                    shimmerFrameLayout.startShimmerAnimation()

                }
                ERROR -> {
                    recyclerView.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE
                    shimmerFrameLayout.stopShimmerAnimation()

                }

            }


        })
        model.newsResponse.observe(viewLifecycleOwner, Observer { ResponseNews ->
            ResponseNews.articles?.let { adapter.setNews(it) }
        })

        return view
    }

}