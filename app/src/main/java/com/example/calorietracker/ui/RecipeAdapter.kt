package com.example.calorietracker.ui

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
    var itemViewList = mutableListOf<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(recipes[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {itemViewList.add(itemView)
            itemView.setOnClickListener {
                onClick(recipes[adapterPosition])
                //Set background of selected ingredient to blue
                for (tempItemView in itemViewList) {
                    if (itemViewList[this.adapterPosition] === tempItemView) {
                        tempItemView.setBackgroundResource(R.color.colorSelected)
                    } else {
                        tempItemView.setBackgroundResource(R.color.colorDefault)
                    }
                }
            }
        }

        fun bind(recipeWithIngredients: RecipeWithIngredients) {
            itemView.tvRecipeName.text = recipeWithIngredients.recipe.name
        }
    }

}