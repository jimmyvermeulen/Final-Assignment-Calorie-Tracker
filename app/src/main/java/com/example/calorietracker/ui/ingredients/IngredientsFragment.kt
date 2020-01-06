package com.example.calorietracker.ui.ingredients

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.ui.addIngredient.AddIngredientActivity
import com.example.calorietracker.ui.adapters.IngredientAdapter
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient
import kotlinx.android.synthetic.main.fragment_ingredients.*

const val ADD_INGREDIENT_REQUEST_CODE = 100
const val UPDATE_INGREDIENT_REQUEST_CODE = 100
const val EXTRA_INGREDIENT = "EXTRA_INGREDIENT"

class IngredientsFragment : Fragment() {

    private val ingredients = arrayListOf<Ingredient>()
    private val ingredientAdapter =
        IngredientAdapter(ingredients) { ingredient ->
            onIngredientClick(ingredient)
        }
    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val root = inflater.inflate(R.layout.fragment_ingredients, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_notifications)
        ingredientsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabIngredients.setOnClickListener {
            startAddActivity()
        }
        rvIngredients.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvIngredients.adapter = ingredientAdapter
        rvIngredients.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvIngredients)
    }

    private fun startAddActivity(){
        val intent = Intent(activity, AddIngredientActivity::class.java)
        startActivityForResult(intent, ADD_INGREDIENT_REQUEST_CODE)
    }

    private fun startUpdateActivity(ingredient: Ingredient){
        val intent = Intent(activity, AddIngredientActivity::class.java)
        intent.putExtra(EXTRA_INGREDIENT, ingredient)
        startActivityForResult(intent, UPDATE_INGREDIENT_REQUEST_CODE)
    }

    private fun initViewModel(){
        ingredientsViewModel =
            ViewModelProviders.of(this).get(IngredientsViewModel::class.java)
        ingredientsViewModel.ingredients.observe(this, Observer { ingredients ->
            this@IngredientsFragment.ingredients.clear()
            this@IngredientsFragment.ingredients.addAll(ingredients)
            ingredientAdapter.notifyDataSetChanged()
        })
    }

    private fun onIngredientClick(ingredient : Ingredient){
        startUpdateActivity(ingredient)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_INGREDIENT_REQUEST_CODE -> {
                    val ingredient = data!!.getParcelableExtra<Ingredient>(AddIngredientActivity.EXTRA_INGREDIENT)

                    ingredientsViewModel.insertIngredient(ingredient)
                }

                UPDATE_INGREDIENT_REQUEST_CODE -> {
                    val ingredient = data!!.getParcelableExtra<Ingredient>(AddIngredientActivity.EXTRA_INGREDIENT)

                    ingredientsViewModel.updateIngredient(ingredient)
                }
            }
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val ingredientToDelete = ingredients[position]
                ingredientsViewModel.deleteIngredient(ingredientToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }
}