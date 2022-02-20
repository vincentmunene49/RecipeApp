package com.example.recipes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    val data_list: ArrayList<data_source>,
    private var listener: itemClickedListener
) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {
        val current_item = data_list[position]
        holder.setData(current_item)


    }


    override fun getItemCount(): Int = data_list.size

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val liked = view.findViewById<ImageView>(R.id.delete)

        fun setData(data: data_source) {
            val desc_text = view.findViewById<TextView>(R.id.Desc_txt_in_like)
            val imageView = view.findViewById<ImageView>(R.id.Desc_pic_in_like)


//set data
            desc_text.text = data.description
            imageView.setImageDrawable(view.context.getDrawable(data.image))
        }


        init {
            view.setOnClickListener(this)
            liked.setOnClickListener(this)

        }


        override fun onClick(item: View?) {
            if (item?.id == R.id.delete) {
                temp_data_source.getData()[bindingAdapterPosition].favourite =
                    !(temp_data_source.getData()[bindingAdapterPosition].favourite)
                if(temp_data_source.getData()[bindingAdapterPosition].favourite ){
                    liked.setBackgroundColor(Color.parseColor("#ff0000"))
                }else{
                    liked.setBackgroundColor(Color.parseColor("#0ff000"))
                    temp_data_source.likedList.add(temp_data_source.getData()[bindingAdapterPosition])
                    Toast.makeText(view.context, "added to likes", Toast.LENGTH_SHORT).show()
                }

            } else {
                val position: Int = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position)

                }
            }


        }


    }




}
