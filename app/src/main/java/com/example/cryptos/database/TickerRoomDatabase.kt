package com.example.cryptos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Ticker::class], version = 1)
abstract class TickerRoomDatabase : RoomDatabase() {

    abstract fun tickerDao(): TickerDao

    companion object {
        @Volatile
        private var INSTANCE: TickerRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
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
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .addCallback(
                            CountriesDatabaseCallback(
                                scope
                            )
                        )
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }

        private class CountriesDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.tickerDao()
                        )
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        fun populateDatabase(tickerDao: TickerDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //tickerDao.deleteAll()
           /* var ticker = Ticker("params")
            tickerDao.insert(Ticker)*/
        }
    }
}