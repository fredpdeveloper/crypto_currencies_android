package com.example.cryptos.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickers_table")
data class Ticker(
    @PrimaryKey(autoGenerate = true) var tickerId: Int = 0,
    @ColumnInfo(name = "timestamp")val timestamp: String,
    @ColumnInfo(name = "market")val market: String,
    @ColumnInfo(name = "bid")val bid: String,
    @ColumnInfo(name = "ask")val ask: String,
    @ColumnInfo(name = "last_price")val last_price: String,
    @ColumnInfo(name = "low")val low: String,
    @ColumnInfo(name = "high")val high: String,
    @ColumnInfo(name = "volume")val volume: String,
    @ColumnInfo(name = "dayPercent")val dayPercent: String?
)