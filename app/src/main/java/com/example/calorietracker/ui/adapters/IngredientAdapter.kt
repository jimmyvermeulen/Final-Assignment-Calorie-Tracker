package com.example.calorietracker.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient
import kotlinx.android.synthetic.main.item_food.view.*


class IngredientAdapter(private val ingredients: List<Ingredient>, private val onClick: (Ingredient) -> Unit) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(ingredients[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick(ingredients[adapterPosition])
            }
        }

        fun bind(ingredient: Ingredient) {
            itemView.tvFoodName.text = ingredient.name
            itemView.tvCalories.text = context.getString(R.string.kcal100g, "%.2f".format(ingredient.calories))
        }
    }

}