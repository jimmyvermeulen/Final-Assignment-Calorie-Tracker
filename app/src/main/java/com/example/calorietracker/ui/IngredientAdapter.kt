package com.example.calorietracker.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient
import kotlinx.android.synthetic.main.item_ingredient.view.*





class IngredientAdapter(private val ingredients: List<Ingredient>, private val onClick: (Ingredient) -> Unit) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    private lateinit var context: Context
    var itemViewList = mutableListOf<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false)
        )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(ingredients[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemViewList.add(itemView)
            itemView.setOnClickListener {
                onClick(ingredients[adapterPosition])
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

        fun bind(ingredient: Ingredient) {
            itemView.tvIngredientName.text = ingredient.name
        }
    }

}