package com.example.calorietracker.ui.adapters

import android.content.Context
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
            var totalCalories : Double = 0.0
            for(ingredient in recipeWithIngredients.ingredients){
                totalCalories += ingredient.calories
            }
            itemView.tvRecipeCalories.text = context.getString(R.string.kcal, "%.2f".format(totalCalories))
        }
    }

}