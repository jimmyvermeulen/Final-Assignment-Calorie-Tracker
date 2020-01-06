package com.example.calorietracker.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.RecipeWithIngredients
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(private val recipes: List<RecipeWithIngredients>, private val onClick: (RecipeWithIngredients) -> Unit) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(recipes[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick(recipes[adapterPosition])
            }
        }

        fun bind(recipeWithIngredients: RecipeWithIngredients) {
            itemView.tvRecipeName.text = recipeWithIngredients.recipe.name
            var totalCalories = 0.0
            for(i in recipeWithIngredients.ingredients.indices){
                totalCalories += recipeWithIngredients.ingredients[i].calories * recipeWithIngredients.quantities[i].amount / 100
            }
            itemView.tvRecipeCalories.text = context.getString(R.string.kcal, "%.2f".format(totalCalories))
        }
    }

}