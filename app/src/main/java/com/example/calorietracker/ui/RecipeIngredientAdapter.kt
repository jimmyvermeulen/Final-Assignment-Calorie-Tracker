package com.example.calorietracker.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient
import com.example.calorietracker.model.RecipeWithIngredients
import kotlinx.android.synthetic.main.item_recipe_ingredient.view.*

class RecipeIngredientAdapter(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recipe_ingredient, parent, false)
        )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(ingredients[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ingredient: Ingredient) {
            itemView.tvRecipeIngredientName.text = ingredient.name
        }
    }

}