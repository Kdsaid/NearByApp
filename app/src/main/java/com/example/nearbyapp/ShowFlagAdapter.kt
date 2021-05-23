package com.example.nearbyapp

import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearbyapp.data.models.Venue
import com.example.nearbyapp.utils.inflate
import com.example.nearbyapp.utils.loadImage

class ShowFlagAdapter(  val venues: List<Venue>) :
    RecyclerView.Adapter<ShowFlagAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_row))

    override fun getItemCount(): Int = venues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        val textView = holder.itemView.findViewById<TextView>(R.id.textView)
            textView.apply {
                venues[position].venuePage
            }

        imageView.loadImage(venues[position].categories[0]
            .icon.prefix+venues[position].categories[0].icon.suffix)

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}