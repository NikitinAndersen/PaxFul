package com.example.test1.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Px
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.test1.R
import com.example.test1.ui.base.StateAwareResponse

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

inline fun <T> LiveData<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline value: (t: T) -> Unit
) {
    observe(lifecycleOwner, Observer<T> {
        value(it)
    })
}

@Px
fun Fragment.toDp(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun String.getNullIfBlank(): String? {
    if (isBlank()) {
        return null
    }
    return this
}

fun View.visible(isVisible: Boolean) {
    visibility = if (!isVisible) View.GONE else View.VISIBLE
}

inline fun <T> StateAwareResponse<T>.isSuccess(block: (T) -> Unit): StateAwareResponse<T> {
    if (this is StateAwareResponse.Loaded) {
        block(this.data)
    }
    return this
}

inline fun <T> StateAwareResponse<T>.isError(block: (Throwable) -> Unit): StateAwareResponse<T> {
    if (this is StateAwareResponse.Error) {
        block(this.throwable)
    }
    return this
}

fun <T> MutableLiveData<T>.asImmutable(): LiveData<T> = this

fun Context.shareData(sharedText: String?) {
    if (sharedText.isNullOrBlank()) return
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, sharedText)
        type = TEXT_PLAIN
    }
    try {
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_joke_title)))
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        showToast(getString(R.string.share_exception))
    }
}

fun Context.showToast(text: String, showShort: Boolean = true) {
    Toast.makeText(this, text, if (showShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()

}

const val TEXT_PLAIN = "text/plain"
