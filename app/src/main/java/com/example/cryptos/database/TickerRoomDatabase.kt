package com.example.cryptos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ticker::class], version = 1)
abstract class TickerRoomDatabase : RoomDatabase() {

    abstract fun tickerDao(): TickerDao

    companion object {
        @Volatile
        private var INSTANCE: TickerRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): TickerRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TickerRoomDatabase::class.java,
                        "tickers_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }
    }
}