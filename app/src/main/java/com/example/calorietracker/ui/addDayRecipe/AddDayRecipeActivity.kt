package com.example.calorietracker.ui.addDayRecipe

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
import com.example.calorietracker.model.RecipeWithIngredients
import com.example.calorietracker.ui.adapters.RecipeAdapter
import com.example.calorietracker.ui.recipes.RecipesViewModel

import kotlinx.android.synthetic.main.activity_add_day_recipe.*
import kotlinx.android.synthetic.main.content_add_day_recipe.*
import kotlinx.android.synthetic.main.content_add_recipe_ingredient.*

class AddDayRecipeActivity : AppCompatActivity() {

    private val recipesWithIngredients = arrayListOf<RecipeWithIngredients>()
    private val recipesWithIngredientsAdapter =
        RecipeAdapter(recipesWithIngredients) { recipeWithIngredients ->
            onRecipeClick(recipeWithIngredients)
        }

    private lateinit var  selectedRecipe: RecipeWithIngredients
    private lateinit var  recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_day_recipe)
        setSupportActionBar(toolbar)
        initViews()
        initViewModel()
    }

    private fun initViews(){
        rvDayRecipe.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvDayRecipe.adapter = recipesWithIngredientsAdapter
        rvDayRecipe.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun initViewModel(){
        recipesViewModel =
            ViewModelProviders.of(this).get(RecipesViewModel::class.java)
        recipesViewModel.recipesWithIngredients.observe(this, Observer { recipesWithIngredients ->
            this@AddDayRecipeActivity.recipesWithIngredients.clear()
            this@AddDayRecipeActivity.recipesWithIngredients.addAll(recipesWithIngredients)
            recipesWithIngredientsAdapter.notifyDataSetChanged()
        })

    }

    private fun endActivity(){
        if(::selectedRecipe.isInitialized){
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_RECIPE, selectedRecipe)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        else{
            Toast.makeText(this,R.string.message_select_meal, Toast.LENGTH_LONG).show()
        }
    }

    private fun onRecipeClick(recipeWithIngredients: RecipeWithIngredients){
        selectedRecipe = recipeWithIngredients
        endActivity()
    }

    companion object {
        const val EXTRA_RECIPE = "EXTRA_RECIPE"
    }
}
