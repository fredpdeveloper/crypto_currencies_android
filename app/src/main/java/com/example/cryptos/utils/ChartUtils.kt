package com.example.cryptos.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptos.R
import com.example.cryptos.adapter.NewsListAdapter
import com.example.cryptos.adapter.TickerListAdapter
import com.example.cryptos.database.Ticker
import com.example.cryptos.api.model.Article
import com.example.cryptos.view.TooltipView
import com.example.cryptos.view.fragments.Tickers
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.DecimalFormat

object ChartUtils {

    fun renderData(mChart: LineChart, tickers: List<Ticker>) {
        mChart.setTouchEnabled(true)
        mChart.setPinchZoom(true)
        mChart.description.isEnabled = false
        val mv = TooltipView(
            mChart.context,
            R.layout.tooltip_view
        )
        mv.chartView = mChart
        mChart!!.marker = mv

        val llXAxis = LimitLine(10f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        val xAxis = mChart.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        /*xAxis.axisMaximum = 10f
        xAxis.axisMinimum = 0f*/
        xAxis.setDrawLimitLinesBehindData(true)
        val ll1 = LimitLine(215f, "Maximum Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        val ll2 = LimitLine(70f, "Minimum Limit")
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
        val leftAxis = mChart.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll1)
        leftAxis.addLimitLine(ll2)
        /*leftAxis.axisMaximum = 350f
        leftAxis.axisMinimum = 0f*/
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
        mChart.axisRight.isEnabled = false
        setData(mChart,tickers)


    }

    private fun setData(mChart: LineChart,tickers: List<Ticker>) {
        val values: ArrayList<Entry> = ArrayList()
        var count = 0
        tickers.forEach {
            values.add(Entry(count.toFloat(), it.bid.toFloat()))
            count++
        }
        val set1: LineDataSet
        if (mChart.data != null &&
            mChart.data.dataSetCount > 0
        ) {
            set1 = mChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            mChart.data.notifyDataChanged()
            mChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "Precio")
            set1.color = Color.DKGRAY
            set1.setCircleColor(Color.DKGRAY)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.setDrawValues(false)
            set1.formLineWidth = 1f
            // set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            val drawable = mChart.context?.let { ContextCompat.getDrawable(it, R.drawable.fade) }
            set1.fillDrawable = drawable
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1)
            val data = LineData(dataSets)
            mChart.data = data
        }
    }


}