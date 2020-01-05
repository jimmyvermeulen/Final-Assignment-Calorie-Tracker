package com.example.calorietracker.ui.addRecipeIngredient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient
import com.example.calorietracker.ui.IngredientAdapter
import com.example.calorietracker.ui.ingredients.IngredientsViewModel

import kotlinx.android.synthetic.main.activity_add_recipe_ingredient.*
import kotlinx.android.synthetic.main.content_add_recipe_ingredient.*

class AddRecipeIngredientActivity : AppCompatActivity() {

    private val ingredients = arrayListOf<Ingredient>()
    private val ingredientAdapter =
        IngredientAdapter(ingredients) { ingredient ->
            onIngredientClick(ingredient)
    }

    private lateinit var selectedIngredient: Ingredient
    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe_ingredient)
        setSupportActionBar(toolbar)
        initViewModel()
        initViews()
    }

    private fun initViewModel(){
        ingredientsViewModel =
            ViewModelProviders.of(this).get(IngredientsViewModel::class.java)
        ingredientsViewModel.ingredients.observe(this, Observer { ingredients ->
            this@AddRecipeIngredientActivity.ingredients.clear()
            this@AddRecipeIngredientActivity.ingredients.addAll(ingredients)
            ingredientAdapter.notifyDataSetChanged()
        })
    }

    private fun initViews(){
        rvAddRecipeIngredient.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvAddRecipeIngredient.adapter = ingredientAdapter
        rvAddRecipeIngredient.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        fabRecipeIngredient.setOnClickListener{
            endActivity()
        }
    }

    private fun endActivity(){
        if(::selectedIngredient.isInitialized){
            if(!etIngredientAmount.text.isNullOrBlank()) {
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_INGREDIENT, selectedIngredient)
                resultIntent.putExtra(
                    EXTRA_AMOUNT,
                    etIngredientAmount.text.toString().toDouble()
                )
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            else Toast.makeText(this,R.string.message_enter_ingredient_amount, Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this,R.string.message_select_ingredient, Toast.LENGTH_LONG).show()
        }

    }

    private fun onIngredientClick(ingredient: Ingredient){
        selectedIngredient = ingredient
    }

    companion object {
        const val EXTRA_INGREDIENT = "EXTRA_INGREDIENT"
        const val EXTRA_AMOUNT = "EXTRA_AMOUNT"
    }

}
