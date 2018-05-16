package com.maxkrass.stundenplankotlinrefactor.createsubject;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.maxkrass.stundenplankotlinrefactor.data.Subject;

import java.util.List;

/**
 * Max made this for Stundenplan2 on 20.07.2016.
 */
public class SubjectRepository {

	private final CollectionReference mSubjectRef;
	private final CollectionReference mTeachersRef;

	public SubjectRepository(String uId) {

		DocumentReference userDocumentRef = FirebaseFirestore.getInstance().collection("users").document(uId);

		this.mSubjectRef = userDocumentRef.collection("subjects");
		this.mTeachersRef = userDocumentRef.collection("teachers");
	}

	public void getSubject(@NonNull String name, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
		mSubjectRef.document(name).get().addOnCompleteListener(onCompleteListener);
	}

	public void subjectExists(String name, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
		mSubjectRef.document(name).get().addOnCompleteListener(onCompleteListener);
	}

	public void updateSubject(@NonNull String key, String name, String abbreviation, String color, String teacher, OnCompleteListener<Void> listener) {
		if (!key.equals(name)) {
			mSubjectRef
					.document(key)
					.delete();
		}

		mSubjectRef
				.document(key)
				.set(new Subject(name, abbreviation, teacher, color))
				.addOnCompleteListener(listener);

		addSubjectToTeacher(teacher, name);
	}

	public void createSubject(String name, String abbreviation, String color, String teacher, OnCompleteListener<Void> listener) {
		mSubjectRef
				.document(name)
				.set(new Subject(name, abbreviation, teacher, color).toMap())
				.addOnCompleteListener(listener);

		//addSubjectToTeacher(teacher, name);
	}

	private void addSubjectToTeacher(final String teacher, final String subject) {
		if (!teacher.isEmpty()) {
			mTeachersRef.document(teacher).collection("subjects").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
				@Override
				public void onSuccess(QuerySnapshot documentSnapshots) {
					List<String> subjects = documentSnapshots.toObjects(String.class);
					subjects.remove(subject);
					subjects.add(subject);
					//mTeachersRef.document(teacher).collection("subjects").(subjects);
				}
			});
		}
	}

}
