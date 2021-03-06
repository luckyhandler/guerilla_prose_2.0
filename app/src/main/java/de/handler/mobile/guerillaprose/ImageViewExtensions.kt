package de.handler.mobile.guerillaprose

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import timber.log.Timber

fun ImageView.loadUrl(picasso: Picasso, url: String?, width: Int? = null, height: Int? = null, onBitmapLoadedAction: ((bitmap: Bitmap?) -> Unit)? = null) {
    Timber.i("ImageView url is $url")
    if (!url.isNullOrBlank()) {
        picasso.load(url)
            .resize(width ?: this.measuredWidth, height ?: this.measuredHeight)
            .into(this, object: Callback {
                override fun onError() {}

                override fun onSuccess() {
                    val bitmap = (drawable as? BitmapDrawable)?.bitmap
                    onBitmapLoadedAction?.invoke(bitmap)

                    val drawable = bitmap?.let { RoundedBitmapDrawableFactory.create(resources, it) }
                    drawable?.cornerRadius = 16F
                    drawable?.setAntiAlias(true)
                    setImageDrawable(drawable)
                }
            })
    }
}