package com.maxkrass.stundenplankotlinrefactor.data

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object TeacherRepository {

    private val ref: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("stundenplan")
            .child("publicTeachers")

    val options: FirebaseRecyclerOptions<Teacher> = FirebaseRecyclerOptions.Builder<Teacher>()
            .setQuery(TeacherRepository.ref) { snapshot: DataSnapshot ->
                Teacher(snapshot.key ?: "",
                        snapshot.key ?: "",
                        TeacherSubjects())
            }
            .build()

}