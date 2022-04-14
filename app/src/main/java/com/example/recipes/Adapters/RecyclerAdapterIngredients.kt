package com.example.recipes.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes.Pojo.Ingredient
import com.example.recipes.R

class RecyclerAdapterIngredients(
    val ingridients: Array<Ingredient>
) : RecyclerView.Adapter<RecyclerAdapterIngredients.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = ingridients[position]
        holder.setData(currentItem)
    }

    override fun getItemCount(): Int = ingridients.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setData(ingredient: Ingredient) {
            val image = view.findViewById<ImageView>(R.id.ingredientImage)
            val name = view.findViewById<TextView>(R.id.Name)
            val quantity = view.findViewById<TextView>(R.id.quantity)
            val category = view.findViewById<TextView>(R.id.category)
            val measure = view.findViewById<TextView>(R.id.measure)
            val text = view.findViewById<TextView>(R.id.test)


            //checking for null values

            if ((ingredient.foodCategory) == null) {
                ingredient.foodCategory = "Unknown"

            }

            if ((ingredient.measure) == null) {

                ingredient.measure = "Suitable"

            }


            name.text = "Name: " + ingredient.food
            quantity.text = "Quanity: " + ingredient.quantity.toString()
            category.text = "Category: " + ingredient.foodCategory
            measure.text = "Measure: " + ingredient.measure
            text.text = "Description: " + ingredient.text

            Glide.with(view.context).load(ingredient.image).override(400, 200).into(image)

        }

    }


}
