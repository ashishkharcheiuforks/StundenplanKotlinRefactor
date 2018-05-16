package com.maxkrass.stundenplankotlinrefactor.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import android.widget.FrameLayout
import com.maxkrass.stundenplankotlinrefactor.R
import kotlinx.android.synthetic.main.checkbox_widget.view.*


/**
 * Max made this for StundenplanKotlinRefactor on 22.05.2017.
 */
class CheckBoxWidget : FrameLayout, Checkable {

    constructor(context: Context) : super(context) {
        init(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
             false,
             "")
    }

    @JvmOverloads constructor(context: Context,
                              attributeSet: AttributeSet,
                              i: Int = 0) : super(context, attributeSet, i) {
        val a: TypedArray = context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.CheckBoxWidget,
                0,
                0)
        val checked: Boolean = a.getBoolean(R.styleable.CheckBoxWidget_checked, false)
        val text: String = a.getString(R.styleable.CheckBoxWidget_label)
        a.recycle()
        init(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
             checked,
             text)
    }

    private fun init(layoutInflater: LayoutInflater, checked: Boolean, text: String) {
        layoutInflater.inflate(R.layout.checkbox_widget, this)
        setTitleView(text)
        isChecked = checked
    }

    private fun setTitleView(s: String) {
        title.text = s
    }

    override fun toggle() {
        checkbox.toggle()
    }

    override fun toString(): String {
        return isChecked.toString()
    }

    override fun isChecked(): Boolean {
        return checkbox.isChecked
    }

    override fun setEnabled(enabled: Boolean) {
        checkbox.isEnabled = enabled
    }

    override fun setChecked(flag: Boolean) {
        checkbox.isChecked = flag
    }

}
