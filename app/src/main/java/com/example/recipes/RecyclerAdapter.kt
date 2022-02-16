package com.example.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val data_list: ArrayList<data_source>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {
        val current_item= data_list[position]
        holder.setData(current_item)


    }


    override fun getItemCount(): Int = data_list.size

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setData(data: data_source) {
            val desc_text = view.findViewById<TextView>(R.id.Desc_txt)
            val imageView = view.findViewById<ImageView>(R.id.Desc_pic)

            desc_text.text = data.description
            imageView.setImageDrawable(view.context.getDrawable(data.image))
        }


    }
}