package it372.BookwormBin
/*
Mya Von Behren
Final Project
June 11, 2024

Helper class to create a database to store user data.
 */

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReadingDbHelper internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // Called to create the database
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE readings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                author TEXT,
                type TEXT,
                link TEXT,
                notes TEXT,
                rating TEXT,
                genre TEXT,
                date TEXT,
                status TEXT
            );
            """
        )


    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int, newVersion: Int
    ) {

        // This method is required, but not used
        // in this example
    }

    companion object {
        private const val DB_NAME = "readings.db"
        private const val DB_VERSION = 1
    }
}