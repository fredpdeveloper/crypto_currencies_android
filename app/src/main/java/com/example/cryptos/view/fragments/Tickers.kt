package com.example.cryptos.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptos.R
import com.example.cryptos.adapter.TickerListAdapter
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.RecyclerViewCallback
import com.example.cryptos.network.model.CryptoApiStatus.*
import com.example.cryptos.view.TickerDialog
import com.example.cryptos.viewmodel.CryptoViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_ticker.*
import kotlinx.android.synthetic.main.filter.*
import kotlinx.android.synthetic.main.filter.view.*


@AndroidEntryPoint
class Tickers : Fragment(), RecyclerViewCallback {

    private lateinit var model: CryptoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ticker, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TickerListAdapter(activity!!.applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        adapter.setOnCallbackListener(this)

        model = ViewModelProvider(this).get(CryptoViewModel::class.java)
        model.getTickers()
        model.newsResponse.observe(viewLifecycleOwner, Observer { ResponseTickers ->
            model.insert(ResponseTickers.data)
            adapter.setTickers(ResponseTickers.data)
        })

        model.status.observe(viewLifecycleOwner, Observer { CryptoApiStatus ->

            when (CryptoApiStatus) {
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
        view.chips_group_country.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerFilterChanged(adapter)

            }
        }

        view.chips_group_exchange.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerExchangeFilterChanged(adapter)

            }
        }
        return view
    }

    /*TODO dejar solo una funcion register*/
    private fun registerFilterChanged(adapter: TickerListAdapter) {
        val ids = chips_group_country.getCheckedChipIds()

        val titles = mutableListOf<CharSequence>()

        ids.forEach { id ->
            titles.add(chips_group_country.findViewById<Chip>(id).text)
        }

        if (titles.isNotEmpty()) {
            adapter.filter.filter(titles.joinToString(", "))
        } else {
            adapter.filter.filter(null)
        }

    }

    /*TODO dejar solo una funcion register*/
    private fun registerExchangeFilterChanged(adapter: TickerListAdapter) {
        val ids = chips_group_exchange.getCheckedChipIds()

        val titles = mutableListOf<CharSequence>()

        ids.forEach { id ->
            titles.add(chips_group_exchange.findViewById<Chip>(id).text)
        }

        if (titles.isNotEmpty()) {
            adapter.filter.filter(titles.joinToString(", "))
        } else {
            adapter.filter.filter(null)
        }

    }

    override fun onRecycleViewItemClick(ticker: Ticker, position: Int) {
        model.getTickersByMarker(ticker.market).observe(this, Observer { tickers ->
            // Update the cached copy of the tickers in the adapter.
            tickers?.let {
                val tickerDialog: TickerDialog = TickerDialog(tickers, ticker).newInstance()
                tickerDialog.show(
                    activity?.supportFragmentManager!!,
                    "ticker_dialog"
                )
            }
        })
    }

}