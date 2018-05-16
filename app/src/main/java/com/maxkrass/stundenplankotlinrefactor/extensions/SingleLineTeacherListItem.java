package com.maxkrass.stundenplankotlinrefactor.extensions;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxkrass.stundenplankotlinrefactor.R;

import splitties.material.lists.TwoLinesIconListItem;
import xyz.louiscad.selectableviewgroups.widget.SelectableLinearLayout;

/**
 * Max made this for StundenplanKotlinRefactor on 10.10.2017.
 */

public class SingleLineTeacherListItem extends SelectableLinearLayout {


		public SingleLineTeacherListItem(@NonNull Context context) {
			super(context);
			init(context);
		}

		public SingleLineTeacherListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}

		public SingleLineTeacherListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			init(context);
		}

		public SingleLineTeacherListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
			super(context, attrs, defStyleAttr, defStyleRes);
			init(context);
		}

		private ImageView teacherEmail;
		private TextView  teacherName;

		private void init(final Context context) {
			inflate(context, R.layout.content_teacher_view, this);
			teacherEmail = findViewById(R.id.email_teacher);
			teacherName = findViewById(R.id.teacher_name);
		}

		@NonNull
		public ImageView getTeacherEmail() {
			return teacherEmail;
		}

		/**
		 * The single-line list item keeps the "subjectName" name for it's only TextView to make
		 * switching to {@link TwoLinesIconListItem} easier.
		 */
		@NonNull
		public TextView getTeacherName() {
			return teacherName;
		}
	}

