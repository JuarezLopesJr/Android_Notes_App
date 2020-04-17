package com.example.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
    }

    private fun initializeCourses(): Unit {
        var course = CourseInfo("android_intents", "Android with Intents")
        courses.set(course.courseId, course)
        /* courses[course.courseId] = course   same as above, using indexing syntax */

        course = CourseInfo("android_async", "Android Async")
        courses[course.courseId] = course

        course = CourseInfo("android_fragments", "Android Fragments")
        courses[course.courseId] = course

        course = CourseInfo("android_jetpack", "Android JetPack")
        courses[course.courseId] = course
    }
}