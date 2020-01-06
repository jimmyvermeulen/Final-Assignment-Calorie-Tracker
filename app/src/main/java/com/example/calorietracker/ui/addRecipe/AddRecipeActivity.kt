package com.example.calorietracker.ui.addRecipe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient
import com.example.calorietracker.model.Recipe
import com.example.calorietracker.model.RecipeWithIngredients
import com.example.calorietracker.ui.adapters.RecipeIngredientAdapter
import com.example.calorietracker.ui.addRecipeIngredient.AddRecipeIngredientActivity
import com.example.calorietracker.ui.ingredients.ADD_INGREDIENT_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.content_add_recipe.*

const val ADD_INGREDIENT_REQUEST_CODE = 100

class AddRecipeActivity : AppCompatActivity() {

    private var recipeIngredients = mutableListOf<Ingredient>()
    private var recipeIngredientAmounts = mutableListOf<Double>()
    private val recipeIngredientAdapter =
        RecipeIngredientAdapter(
            recipeIngredients,
            recipeIngredientAmounts
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        fabAddRecipe.setOnClickListener { onSaveClick() }
        ivAddRecipeIngredient.setOnClickListener{ startAddIngredientActivity() }
        rvRecipeIngredients.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRecipeIngredients.adapter = recipeIngredientAdapter
        rvRecipeIngredients.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvRecipeIngredients)
    }

    private fun onSaveClick() {
        val recipe = Recipe(etRecipeName.text.toString(), etRecipeInstructions.text.toString())
        val recipeWithIngredients = RecipeWithIngredients(recipe, recipeIngredients)

        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_RECIPE, recipeWithIngredients)
        resultIntent.putExtra(EXTRA_AMOUNTS, recipeIngredientAmounts.toDoubleArray())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_INGREDIENT_REQUEST_CODE -> {
                    recipeIngredients.add(data!!.getParcelableExtra<Ingredient>(AddRecipeIngredientActivity.EXTRA_INGREDIENT))
                    recipeIngredientAmounts.add(data!!.getDoubleExtra(AddRecipeIngredientActivity.EXTRA_AMOUNT, 0.0))
                    recipeIngredientAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun startAddIngredientActivity(){
        val intent = Intent(this, AddRecipeIngredientActivity::class.java)
        startActivityForResult(intent, ADD_INGREDIENT_REQUEST_CODE)
    }

    companion object {
        const val EXTRA_RECIPE = "EXTRA_RECIPE"
        const val EXTRA_AMOUNTS = "EXTRA_AMOUNTS"
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
                recipeIngredients.removeAt(position)
                recipeIngredientAmounts.removeAt(position)
                recipeIngredientAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }
}
