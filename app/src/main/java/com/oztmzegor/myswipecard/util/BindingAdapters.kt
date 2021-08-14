package com.oztmzegor.myswipecard.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.oztmzegor.myswipecard.R

@BindingAdapter("health_status")
fun setHealthStatus(view: ImageView, healthStatus: String?) {
    view.setImageResource(
            when {
                healthStatus.equals("dead", ignoreCase = true) -> R.drawable.ic_status_dead
                healthStatus.equals("alive", ignoreCase = true) -> R.drawable.ic_status_alive
                else -> R.drawable.ic_status_unkown
            }
    )
}

@BindingAdapter("imageUrl","placeholder")
fun setImageUrl(image: ImageView, imageUrl: String?, placeHolder: Drawable) {
    imageUrl?.let {
        Glide.with(image.context)
                .load(it)
                .placeholder(placeHolder)
                .into(image)
    } ?: image.setImageDrawable(placeHolder)
}