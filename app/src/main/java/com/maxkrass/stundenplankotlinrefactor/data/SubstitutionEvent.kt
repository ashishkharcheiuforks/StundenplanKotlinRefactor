package com.maxkrass.stundenplankotlinrefactor.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

/**
 * Max made this for Stundenplan2 on 30.08.2016.
 */
data class SubstitutionEvent(
    var period: String = "",
    var subject: String = "",
    var oldTeacher: String = "",
    var sub: String = "",
    var newLocation: String = "",
    var annotation: String = "",
    @Exclude var grade: Grade = Grade.None,
    @Exclude var type: SubstitutionType = SubstitutionType.None
) : FirebaseEntity {

    override fun toMap(): Map<String, Any> =
            mapOf("period" to period,
                    "subject" to subject,
                    "oldTeacher" to oldTeacher,
                    "sub" to sub,
                    "newLocation" to newLocation,
                    "annotation" to annotation)

    @PropertyName("grade")
    var gradeText: String = Grade.None.name
        get() = grade.name
        set(grade) {
            this.grade = Grade.valueOf(grade)
            field = grade
        }

    @PropertyName("type")
    var typeText: String = SubstitutionType.None.name
        get() = type.toString()
        set(value) {
            type = SubstitutionType.searchTypebyString(value)
            field = value
        }
}
