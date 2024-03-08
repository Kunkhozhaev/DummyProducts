package ru.nurdaulet.dummyproducts.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.navigation.NavController
import kotlin.math.roundToInt

fun EditText.afterTextChanged(afterTextChanged: (Editable) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            editable?.let { afterTextChanged.invoke(it) }
        }
    })
}

fun NavController.navigateSafely(@IdRes actionId: Int) {
    currentDestination?.getAction(actionId)?.let {
        Log.d("LOG_Notification", "actionId: $actionId")
        navigate(actionId)
    }
}

fun Double.roundTo2digits(): Double {
    return this.times(100).roundToInt().toDouble().div(100)
}
