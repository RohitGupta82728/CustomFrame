package com.example.customframe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FrameAdapter(val context: Context, var list: List<FrameList>, val onClick: OnClick) :
    RecyclerView.Adapter<FrameAdapter.FrameViewHolder>() {
    class FrameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.frameItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        return FrameViewHolder(
            LayoutInflater.from(context).inflate(R.layout.frame_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        Glide.with(context).load(list.get(position).drawable).into(holder.imageView)

        holder.imageView.setOnClickListener {
            onClick.frameClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}