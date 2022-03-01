package com.example.recipes.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.Interfaces.itemClickedListener
import com.example.recipes.Pojo.Meal

import com.example.recipes.R

class RecylerAdapterLikedList(
    val data_items:ArrayList<Meal>,
    val listener: itemClickedListener
):RecyclerView.Adapter<RecylerAdapterLikedList.myViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        return (myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.liked_list_items,parent,false)))
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = data_items[position]
        holder.setData(currentItem)
    }

    override fun getItemCount() = data_items.size

    inner class myViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun setData(data: Meal){
            val desc_image = view.findViewById<ImageView>(R.id.Desc_pic_in_like)
            val desc_text = view.findViewById<TextView>(R.id.Desc_txt_in_like)


          //  desc_image.setImageDrawable(view.context.getDrawable(data.image))
            //desc_text.text = data.description
        }

        init {
            view.setOnClickListener{
                val position: Int = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position)

                }
            }
        }

    }


}