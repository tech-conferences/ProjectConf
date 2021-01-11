/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.search

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun EditText.getTextChangeObservable(): Observable<String> {

    val subject = PublishSubject.create<String>()

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { subject.onNext(it.toString()) }
        }
    })

    setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            subject.onComplete()
            return@OnEditorActionListener true
        }
        return@OnEditorActionListener false
    })

    return subject
}
