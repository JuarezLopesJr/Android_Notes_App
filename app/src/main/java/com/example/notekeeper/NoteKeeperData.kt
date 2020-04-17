package com.example.notekeeper

// overriding toString method, in order to display course titles inside the dropdown spinner list
class CourseInfo(val courseId: String, val title: String) {
    // if the class is declared as data class, i don't need to override toString method, it's done for me
    override fun toString(): String {
        return title
    }
}

// toString method is already defined using data class modifier
data class NoteInfo(var course: CourseInfo? = null, var title: String? = null, var text: String? = null)