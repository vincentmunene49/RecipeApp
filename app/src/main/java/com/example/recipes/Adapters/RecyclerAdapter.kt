package com.example.recipes.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes.Interfaces.viewClickedListener
import com.example.recipes.Pojo.Hit
import com.example.recipes.R


class RecyclerAdapter(
    val data_list: List<Hit>,
    private var listener: viewClickedListener
) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current_item = data_list[position]
        holder.setData(current_item)


    }


    override fun getItemCount(): Int = data_list.size

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        fun setData(data: Hit) {
            val desc_text = view.findViewById<TextView>(R.id.Desc_txt_in_like)
            val imageView = view.findViewById<ImageView>(R.id.Desc_pic_in_like)



//set data
            desc_text.text = data.recipe.label
            Glide.with(view.context).load(data.recipe.image).override(400, 200).into(imageView)

        }


        init {
            view.setOnClickListener(this)

        }


        override fun onClick(item: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onViewClicked(position)

            }



        }



    }




}
