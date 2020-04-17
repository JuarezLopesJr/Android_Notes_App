package com.example.notekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // populating the spinner dropdown list
        val adapterCourses = ArrayAdapter<CourseInfo>(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        listCourses.adapter = adapterCourses

        /* if there's note in the state, persist that state when the app is destroyed and recreated
        * if not display the current state
        * */
        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?: intent.getIntExtra(
            NOTE_POSITION,
            POSITION_NOT_SET
        )

        if (notePosition != POSITION_NOT_SET) {
            displayNote()
        } else {
            DataManager.notes.add(NoteInfo()) // editing "in place" creates new note
            notePosition = DataManager.notes.lastIndex // will be put in the last position by the saveNote()
        }
    }

    // to persist the state when changing screen orientation (landscape mode)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_next -> {
                moveNext()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /* keeps track of menu items at runtime, if the state changes it'll be called with another method to
    *  reflect the change in the app. invalidateOptionsMenu() is the method that change the menu dynamically
    * */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)

            if (menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.ic_block_black_24dp)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /* Lifecycle used when the activity is not in the foreground (when user press back button)
    * i'm calling this method to be able to edit and persist the texts changed by the user
    * */
    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = noteTitle.text.toString()
        note.text = noteText.text.toString()
        // casting to CourseInfo to be able to access the values
        note.course = listCourses.selectedItem as CourseInfo
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu() // called to dynamically change the menu
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        noteTitle.setText(note.title)
        noteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        listCourses.setSelection(coursePosition)
    }
}
