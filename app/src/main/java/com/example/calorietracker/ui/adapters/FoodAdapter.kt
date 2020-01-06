package com.example.calorietracker.ui.adapters

import com.example.calorietracker.model.Hints
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import kotlinx.android.synthetic.main.item_food.view.*

class FoodAdapter(private val hints: List<Hints>, private val onClick: (Hints) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private lateinit var context: Context
    var itemViewList = mutableListOf<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        )
    }

    override fun getItemCount(): Int = hints.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(hints[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemViewList.add(itemView)
            itemView.setOnClickListener {
                onClick(hints[adapterPosition])
            }
        }

        fun bind(hint: Hints) {
            itemView.tvFoodName.text = hint.food.label
            itemView.tvCalories.text = context.getString(R.string.kcal100g, "%.2f".format(hint.food.nutrients.eNERC_KCAL))
        }
    }

    fun clearItemViewList(){
        itemViewList.clear()
    }

}