package com.example.cryptos.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.cryptos.R
import com.example.cryptos.view.adapter.ViewStateAdapter
import com.example.cryptos.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fm: FragmentManager = supportFragmentManager
        val sa = ViewStateAdapter(fm, lifecycle)

        val pa: ViewPager2 = binding.pager
        pa.adapter = sa


        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_price_title)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_new_title)))

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pa.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        pa.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

}