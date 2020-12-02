package com.example.cryptos.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptos.view.fragments.News
import com.example.cryptos.view.fragments.Tickers


class ViewStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            Tickers()
        } else News()
    }

    override fun getItemCount(): Int {
        return 2
    }
}