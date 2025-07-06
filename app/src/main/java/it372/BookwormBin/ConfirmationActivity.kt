package it372.BookwormBin
/*
Mya Von Behren
Final Project
June 11, 2024

The ConfirmationActivity.kt is responsible for displaying the inputted information after the user
submits the data in the MainActivity.kt. It retrieves data passed from the MainActivity through intent
and displays it in multiple TextViews. There are various methods to view all entries, clear the
displayed data and database entries, and navigate back to MainActivity to submit more readings.
 */
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import it372.BookwormBin.R.*

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    private var title: String = ""
    private  var author: String = ""
    private var link: String = ""
    private var notes: String = ""
    private var rating: String = ""
    private var genre: String = ""
    private var date: String = ""
    private var status: String = ""
    private lateinit var allEntriesTextView : TextView
    private lateinit var entriesBuilder : StringBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_confirmation)

        // Initialize database helper and get a readable database
        val dbHelper = ReadingDbHelper(this)
        db = dbHelper.readableDatabase

        // Restore data if phone is rotated
        if (savedInstanceState != null) {
            title = savedInstanceState.getString("title").toString()
            author = savedInstanceState.getString("author").toString()
            link = savedInstanceState.getString("link", link)
            notes = savedInstanceState.getString("notes", notes)
            rating = savedInstanceState.getString("rating", rating)
            genre = savedInstanceState.getString("genre", genre)
            date = savedInstanceState.getString("date", date)
            status = savedInstanceState.getString("status", status)
        } else {
            // Retrieve data from the intent
            title = intent.getStringExtra("title").toString()
            author = intent.getStringExtra("author").toString()
            link = intent.getStringExtra("link").toString()
            notes = intent.getStringExtra("notes").toString()
            rating = intent.getStringExtra("rating").toString()
            genre = intent.getStringExtra("genre").toString()
            date = intent.getStringExtra("date").toString()
            status = intent.getStringExtra("status").toString()
        }

        // Display retrieved data
        findViewById<TextView>(id.title_result).text = title
        findViewById<TextView>(id.author_result).text = author
        findViewById<TextView>(id.link_result).text = link
        findViewById<TextView>(id.notes_result).text = notes
        findViewById<TextView>(id.rating_result).text = rating
        findViewById<TextView>(id.genre_result).text = genre
        findViewById<TextView>(id.date_result).text = date
        findViewById<TextView>(id.status_result).text = status

        displayAllEntries()

    }

    // Query all entries from the database and display them
    private fun displayAllEntries() {
        val cursor = db.query("readings", null, null, null, null, null, null)
        allEntriesTextView = findViewById<TextView>(id.all_entries_result)
        entriesBuilder = StringBuilder()

        // Loop through the cursor and append each entry to the entriesBuilder
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val link = cursor.getString(cursor.getColumnIndexOrThrow("link"))
            val notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"))
            val rating = cursor.getString(cursor.getColumnIndexOrThrow("rating"))
            val genre = cursor.getString(cursor.getColumnIndexOrThrow("genre"))
            val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
            val status = cursor.getString(cursor.getColumnIndexOrThrow("status"))

            entriesBuilder.append("Title: $title\nAuthor: $author\nType: $type\nLink: $link\nNotes: $notes\nRating: $rating\nGenre: $genre\nDate: $date\nStatus: $status\n\n")
        }
        cursor.close()
    }

    // Display all entries
    fun viewAll(view: View){
        allEntriesTextView.text = entriesBuilder.toString()
    }

    // Clear displayed data and delete all entries from the database
    fun clear(view: View){
        allEntriesTextView.text = ""
        findViewById<TextView>(id.title_result).text = ""
        findViewById<TextView>(id.author_result).text = ""
        findViewById<TextView>(id.link_result).text = ""
        findViewById<TextView>(id.notes_result).text = ""
        findViewById<TextView>(id.rating_result).text = ""
        findViewById<TextView>(id.genre_result).text = ""
        findViewById<TextView>(id.date_result).text = ""
        findViewById<TextView>(id.status_result).text = ""
        val clearDBQuery = "DELETE FROM readings"
        db.execSQL(clearDBQuery)
        entriesBuilder.clear()

    }

    // Return to the Main Acitivty
    fun back(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // Restore data if phone is rotated
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("title", title)
        outState.putString("author", author)
        outState.putString("link", link)
        outState.putString("notes", notes)
        outState.putString("rating", rating)
        outState.putString("genre", genre)
        outState.putString("date", date)
        outState.putString("status", status)
    }

}