package com.maxkrass.stundenplankotlinrefactor.extensions;

import android.annotation.TargetApi;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.maxkrass.stundenplankotlinrefactor.R;

import splitties.material.lists.TwoLinesIconListItem;
import xyz.louiscad.selectableviewgroups.widget.SelectableLinearLayout;

/**
 * Max made this for StundenplanKotlinRefactor on 19.09.2017.
 */

public class SingleLineSubjectListItem extends SelectableLinearLayout {
	public SingleLineSubjectListItem(@NonNull Context context) {
		super(context);
		init(context);
	}

	public SingleLineSubjectListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SingleLineSubjectListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@TargetApi(25)
	public SingleLineSubjectListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	private View     subjectColor;
	private TextView subjectName;

	private void init(final Context context) {
		inflate(context, R.layout.content_subject_view, this);
		subjectColor = findViewById(R.id.subject_color);
		subjectName = findViewById(R.id.subject_name);
	}

	@NonNull
	public View getSubjectColor() {
		return subjectColor;
	}

	/**
	 * The single-line list item keeps the "subjectName" name for it's only TextView to make
	 * switching to {@link TwoLinesIconListItem} easier.
	 */
	@NonNull
	public TextView getSubjectName() {
		return subjectName;
	}
}
