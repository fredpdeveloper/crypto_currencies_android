package com.example.cryptos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TickerDao {

    @Query("SELECT * from tickers_table  ORDER BY timestamp ASC")
    fun getAllTickers(): LiveData<List<Ticker>>


    @Query("SELECT  * from tickers_table WHERE market = :marker GROUP BY bid ORDER BY timestamp ASC")
    fun getTickerByMarker(marker: String): List<Ticker>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    fun insertAll(tickers: List<Ticker>)

    @Query("DELETE FROM tickers_table")
    fun deleteAll()
}