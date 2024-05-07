package ru.nurdaulet.dummyproducts.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.nurdaulet.dummyproducts.R
import kotlin.math.roundToInt

fun Double.roundTo2digits(): Double {
    return this.times(100).roundToInt().toDouble().div(100)
}

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (context != null) context!!.showToast(text, duration)
}

fun ImageView.loadWithGlide(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.iv_placeholder)
        .error(R.drawable.iv_error)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

fun View.viewGone() {
    this.visibility = View.GONE
}

fun View.viewVisible() {
    this.visibility = View.VISIBLE
}

