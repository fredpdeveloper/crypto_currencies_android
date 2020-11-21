package com.example.cryptos.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.cryptos.R
import okhttp3.internal.format

class UtilsHelper {
    companion object {
        fun isOnline(context: Context) : Boolean {
            val connectivityManager  : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return  networkInfo != null && networkInfo.isConnected
        }

        fun intoString(
            textReal: String,
            pos: Int
        ): String? {
            val stringBuilder = StringBuilder(textReal)
            stringBuilder.insert(pos, "/")
            return "Precio $stringBuilder"
        }

        fun price(
            market: String,
            bid: String
        ): String? {

            var formatter = ""
            when {
                market.contains("CLP") -> {
                    formatter= "$${bid} CLP"
                }
                market.contains("EUR") -> {
                    formatter = "€${bid} EUR"

                }
                market.contains("BRL") -> {
                    formatter = "R$${bid} BRL"

                }
                market.contains("MXN") -> {
                    formatter = "$${bid} MXN"
                }
                market.contains("ARS") -> {
                    formatter = "$${bid} ARS"

                }
            }

            return formatter
        }
    }


}