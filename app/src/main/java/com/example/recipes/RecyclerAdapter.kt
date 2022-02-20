package com.example.recipes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    val data_list: ArrayList<data_source>,
    private var listener: itemClickedLister
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
        val liked = view.findViewById<ImageView>(R.id.favourite)

        fun setData(data: data_source) {
            val desc_text = view.findViewById<TextView>(R.id.Desc_txt)
            val imageView = view.findViewById<ImageView>(R.id.Desc_pic)


//set data
            desc_text.text = data.description
            imageView.setImageDrawable(view.context.getDrawable(data.image))
        }


        init {
            view.setOnClickListener(this)
            liked.setOnClickListener(this)

        }


        override fun onClick(item: View?) {
            if (item?.id == R.id.favourite) {
                temp_data_source.getData()[bindingAdapterPosition].favourite =
                    !(temp_data_source.getData()[bindingAdapterPosition].favourite)
                if(temp_data_source.getData()[bindingAdapterPosition].favourite ){
                    liked.setBackgroundColor(Color.parseColor("#ff0000"))
                }else{
                    liked.setBackgroundColor(Color.parseColor("#0ff000"))
                }

            } else {
                val position: Int = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position)

                }
            }


        }


    }

    interface itemClickedLister {
        fun onItemClicked(position: Int)

    }


}
