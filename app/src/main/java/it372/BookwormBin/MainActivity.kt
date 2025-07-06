package it372.BookwormBin
/*
Mya Von Behren
Final Project
June 11, 2024

The MainActivity.kt is the main screen of my reading tracker app. It sets up the user interface,
initializes the database through a helper class, and handles and stores the user's reading data.

It includes an onClickEnterData for the submit button to handle the data entry process. Then, it
initiates a transition to the ConfirmationActivity.kt and passes the entered data to the next
screen,

 */
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import it372.BookwormBin.R

class MainActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    private lateinit var editTitle: EditText
    private lateinit var editAuthor: EditText
    private lateinit var editType: Spinner
    private lateinit var editLink: EditText
    private lateinit var editNotes: EditText
    private lateinit var editRatingBar: RatingBar
    private lateinit var editGenre: ChipGroup
    private lateinit var editDate: EditText
    private lateinit var editStatus: Spinner
    private lateinit var submitButton: Button
    private lateinit var cursor: Cursor
    private lateinit var fiction: Chip
    private lateinit var nonfiction: Chip
    private lateinit var mystery: Chip
    private lateinit var romance: Chip
    private lateinit var scifi: Chip
    private lateinit var comedy: Chip
    private lateinit var horror: Chip
    private lateinit var title: String
    private lateinit var author: String
    private lateinit var link: String
    private lateinit var notes: String
    private lateinit var rating: String
    private lateinit var type: String
    private lateinit var date: String
    private lateinit var status: String
    private lateinit var genre: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database helper and get writable database
        val dbHelper: SQLiteOpenHelper = ReadingDbHelper(this)
        db = dbHelper.writableDatabase

        // Initialize widgets
        editTitle = findViewById(R.id.editTitle)
        editAuthor = findViewById(R.id.editAuthor)
        editType = findViewById(R.id.type)
        editLink = findViewById(R.id.editLink)
        editNotes = findViewById(R.id.editNotes)
        editRatingBar = findViewById(R.id.rating)
        editGenre = findViewById(R.id.chipGroup)
        editDate = findViewById(R.id.date)
        editStatus = findViewById(R.id.status)
        submitButton = findViewById(R.id.Submit)
        fiction = findViewById(R.id.Fiction)
        nonfiction = findViewById(R.id.NonFiction)
        mystery = findViewById(R.id.Mystery)
        romance = findViewById(R.id.Romance)
        scifi = findViewById(R.id.SciFi)
        comedy = findViewById(R.id.Comedy)
        horror = findViewById(R.id.Horror)

        if (savedInstanceState != null) {
            savedInstanceState.getString("title")
            savedInstanceState.getString("author")
            savedInstanceState.getString("link")
            savedInstanceState.getString("notes")
            savedInstanceState.getString("rating")
            savedInstanceState.getString("genre")
            savedInstanceState.getString("date")
            savedInstanceState.getString("status")
        } else {
            title = ""
            author = ""
            link = ""
            notes = ""
            rating = ""
            genre = ""
            date = ""
            status = ""
        }

        // Query the database
        cursor = db.query("readings", arrayOf("title", "author", "type", "link", "notes", "rating", "genre", "date", "status"), null, null, null, null, null)

    }


    // Handles the submit button onClick event
    fun onClickEnterData(view: View) {
        // Get data from input fields
        title = editTitle.text.toString()
        author = editAuthor.text.toString()
        type = editType.selectedItem.toString()
        link = editLink.text.toString()
        notes = editNotes.text.toString()
        rating = editRatingBar.rating.toString()
        date = editDate.text.toString()
        status = editStatus.selectedItem.toString()
        // Get selected genres from Chip Group
        val selectedChipIds = editGenre.checkedChipIds
        val selectedGenres = mutableListOf<String>()
        for (chipId in selectedChipIds) {
            val chip = findViewById<Chip>(chipId)
            selectedGenres.add(chip.text.toString())
        }
        genre = selectedGenres.joinToString(", ")

        // Create ContentValues object to store the reading's details
        val readingValues = ContentValues().apply {
            put ("title", title)
            put ("author", author)
            put ("type", type)
            put ("link", link)
            put ("notes", notes)
            put ("rating", rating)
            put ("genre", genre)
            put ("date", date)
            put ("status", status)
        }

        // Insert the reading's details into the database
        db.insert("readings", null, readingValues)

        // Create intent to start the second activity - Confirmation Activity
        val intent = Intent(this, ConfirmationActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("author", author)
        intent.putExtra("type", type)
        intent.putExtra("link", link)
        intent.putExtra("notes", notes)
        intent.putExtra("rating", rating)
        intent.putExtra("genre", genre)
        intent.putExtra("date", date)
        intent.putExtra("status", status)

        startActivity(intent)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
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