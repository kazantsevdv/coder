package com.kazantsev.coder.util

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun Activity.hideKeyboard() {
    this.currentFocus?.apply {
        ContextCompat.getSystemService(this@hideKeyboard, InputMethodManager::class.java)
//        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Activity?.showKeyboard(view: View) {
    (this?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.apply {
            currentFocus?.clearFocus()
            view.requestFocus()
            showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
}


fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun Fragment.showKeyboard(view: View) {
    activity?.showKeyboard(view)
}

fun Int.toYearsString(): String {
    val years=this
    val digit = years % 10
   return when {
        this in 10..14 -> "$years Лет"
        digit == 1 -> "$years Год"
        digit in 2..4 -> "$years Года"
        else -> "$years Лет"
    }
}


