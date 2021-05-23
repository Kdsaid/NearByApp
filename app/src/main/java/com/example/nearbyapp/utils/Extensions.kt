package com.example.nearbyapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearbyapp.R


fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun RecyclerView.animateItems() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(
        this.context,
        R.anim.layout_animation_fall_down
    ) as LayoutAnimationController
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


