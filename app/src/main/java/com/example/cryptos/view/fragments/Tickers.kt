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
import com.example.cryptos.view.TickerDialog
import com.example.cryptos.viewmodel.TickersViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_ticker.*
import kotlinx.android.synthetic.main.filter.*
import kotlinx.android.synthetic.main.filter.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tickers.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tickers : Fragment(), RecyclerViewCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var tickersViewModel: TickersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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


        // Get a new or existing ViewModel from the ViewModelProvider.
        tickersViewModel = ViewModelProvider(this).get(TickersViewModel::class.java)
        tickersViewModel.allTickers.observe(viewLifecycleOwner, Observer { tickers ->
            // Update the cached copy of the tickers in the adapter.
            tickers?.let {
                tickersViewModel.insert(it)
                adapter.setTickers(it)
            }
        })
        tickersViewModel.mShowProgressBar.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                recyclerView.visibility = View.GONE
                shimmerFrameLayout.visibility = View.VISIBLE
                shimmerFrameLayout.startShimmerAnimation()
            } else {
                recyclerView.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
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
            adapter.filter.filter(  titles.joinToString(", "))
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
            adapter.filter.filter(  titles.joinToString(", "))
        } else {
            adapter.filter.filter(null)
        }

    }

    override fun onRecycleViewItemClick(ticker: Ticker, position: Int) {
        tickersViewModel.getTickersByMarker(ticker.market).observe(this, Observer { tickers ->
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tickers.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tickers().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}