package com.example.cryptos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptos.database.Ticker
import com.example.cryptos.databinding.TickerDialogBinding
import com.example.cryptos.viewmodel.TickersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TickerDialog(private var ticker: Ticker) : BottomSheetDialogFragment() {
    private lateinit var model: TickersViewModel
    private lateinit var binding: TickerDialogBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = model
        model.getTickersByMarker(ticker)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TickerDialogBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        model = ViewModelProvider(this)[TickersViewModel::class.java]
        return binding.root
    }


    fun newInstance(): TickerDialog {
        return TickerDialog(this.ticker)
    }

}