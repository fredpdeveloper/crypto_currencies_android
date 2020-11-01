package com.example.cryptos.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.cryptos.R
import com.example.cryptos.database.Ticker
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat


class TickerDialog(tickers: List<Ticker>, lastTicker: Ticker) : BottomSheetDialogFragment() {
    private var tickers = tickers
    private var lastTicker = lastTicker
    private var mChart: LineChart? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.ticker_dialog, container,
            false
        )
        mChart = view.findViewById(R.id.chart)
        val tickerPercent: TextView = view.findViewById(R.id.ticker_percent)
        val tickerImg: ImageView = view.findViewById(R.id.ticker_img)
        val tickerImgCountry: ImageView = view.findViewById(R.id.ticker_img_country)
        mChart!!.setTouchEnabled(true)
        mChart!!.setPinchZoom(true)
        mChart!!.description.isEnabled = false
        val mv = TooltipView(
            context,
            R.layout.tooltip_view
        )
        mv.chartView = mChart
        mChart!!.marker = mv

        tickers.forEach {

        }
        when {
            lastTicker.market.contains("ETH") -> {
                tickerImg.setImageResource(R.drawable.ethereum)
            }
            lastTicker.market.contains("XLM") -> {
                tickerImg.setImageResource(R.drawable.stellar)
            }
            lastTicker.market.contains("BTC") -> {
                tickerImg.setImageResource(R.drawable.bitcoin)
            }
            lastTicker.market.contains("EOS") -> {
                tickerImg.setImageResource(R.drawable.eos)
            }
        }

        when {
            lastTicker.market.contains("CLP") -> {

                tickerImgCountry.setImageResource(R.drawable.chile)

            }
            lastTicker.market.contains("EUR") -> {
                tickerImgCountry.setImageResource(R.drawable.europa)

            }
            lastTicker.market.contains("BRL") -> {
                tickerImgCountry.setImageResource(R.drawable.brasil)

            }
            lastTicker.market.contains("MXN") -> {
                tickerImgCountry.setImageResource(0)
            }
            lastTicker.market.contains("ARS") -> {
                tickerImgCountry.setImageResource(R.drawable.argentina)

            }
        }

        var sum = 0.0
        tickers.forEach {
            sum += it.bid.toDouble()
        }
        var average = sum / tickers.size
        var percent = ((lastTicker.bid.toDouble() * 100) / average) - 100
        val df = DecimalFormat()
        df.maximumFractionDigits = 2
        tickerPercent.text = "%${df.format(percent)} ${lastTicker.market}"

        if (percent < 0) {
            tickerPercent.setTextColor(resources.getColor(R.color.colorRed))
        } else if (percent > 0){
            tickerPercent.setTextColor(resources.getColor(R.color.colorGreen))
        } else {
            tickerPercent.setTextColor(resources.getColor(R.color.colorBlack))
        }
        renderData()

        return view
    }


    fun newInstance(): TickerDialog {
        return TickerDialog(this.tickers,this.lastTicker)
    }

    fun renderData() {
        val llXAxis = LimitLine(10f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        val xAxis = mChart!!.xAxis
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
        val leftAxis = mChart!!.axisLeft
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll1)
        leftAxis.addLimitLine(ll2)
        /*leftAxis.axisMaximum = 350f
        leftAxis.axisMinimum = 0f*/
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
        mChart!!.axisRight.isEnabled = false
        setData()


    }

    private fun setData() {
        val values: ArrayList<Entry> = ArrayList()
        var count = 0
        tickers.forEach {
            values.add(Entry(count.toFloat(), it.bid.toFloat()))
            count++
        }
        val set1: LineDataSet
        if (mChart!!.data != null &&
            mChart!!.data.dataSetCount > 0
        ) {
            set1 = mChart!!.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            mChart!!.data.notifyDataChanged()
            mChart!!.notifyDataSetChanged()
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
            val drawable = this.context?.let { ContextCompat.getDrawable(it, R.drawable.fade) }
            set1.fillDrawable = drawable
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1)
            val data = LineData(dataSets)
            mChart!!.data = data
        }
    }


}