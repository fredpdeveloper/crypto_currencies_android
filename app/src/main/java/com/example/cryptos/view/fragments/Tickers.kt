package com.example.cryptos.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import com.example.cryptos.view.adapter.TickerListAdapter
import com.example.cryptos.databinding.FragmentTickerBinding
import com.example.cryptos.view.dialog.TickerDialog
import com.example.cryptos.viewmodel.TickersViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Tickers : Fragment() {

    private lateinit var model: TickersViewModel
    private lateinit var binding: FragmentTickerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this)[TickersViewModel::class.java]
        binding.viewModel = model
        model.getTickers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTickerBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.recyclerview.adapter = TickerListAdapter(
            TickerListAdapter.OnClickListener { ticker ->
                val tickerDialog: TickerDialog =
                    TickerDialog(ticker).newInstance()
                tickerDialog.show(
                    activity?.supportFragmentManager!!,
                    "ticker_dialog"
                )
            }
        )

        binding.include.chipsGroupExchange.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                if (child.isChecked) {
                    val exchange: String =
                        binding.include.chipsGroupExchange.findViewById<Chip>(child.id).text.toString()
                    model.getTickers(exchange)
                } else {
                    model.getTickers()
                }


            }
        }


        return binding.root

    }

}