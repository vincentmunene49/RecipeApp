package com.example.recipes.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes.Interfaces.viewClickedListener
import com.example.recipes.R
import com.example.recipes.RoomDb.Recipe

class RecylerAdapterLikedList(
    val listener: viewClickedListener,
):RecyclerView.Adapter<RecylerAdapterLikedList.myViewHolder>(){
    private var recipe_list = emptyList<Recipe>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {

        return (myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.liked_list_items,parent,false)))
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = recipe_list[position]
        holder.setData(currentItem)
    }

    override fun getItemCount() = recipe_list.size

    inner class myViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun setData(data: Recipe){
            val desc_image = view.findViewById<ImageView>(R.id.Desc_pic_in_like)
            val desc_text = view.findViewById<TextView>(R.id.Desc_txt_in_like)

            desc_text.text = data.lable
            Glide.with(view.context).load(data.image).override(400, 200).into(desc_image)
        }

        init {
            view.setOnClickListener{
                val position: Int = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onViewClicked(position)

                }
            }


        }


    }


    //set the list
    fun setData(recipe:List<Recipe>){
        this.recipe_list = recipe
        notifyDataSetChanged()
    }
//return currentItem
    fun getRecipe(position: Int):Recipe{
        return recipe_list[position]
    }

}