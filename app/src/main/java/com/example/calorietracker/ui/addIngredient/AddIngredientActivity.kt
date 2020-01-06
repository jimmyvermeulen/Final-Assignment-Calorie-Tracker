package com.example.calorietracker.ui.addIngredient

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
import com.example.calorietracker.ui.adapters.FoodAdapter
import com.example.calorietracker.R
import com.example.calorietracker.model.Hints
import com.example.calorietracker.model.Ingredient

import kotlinx.android.synthetic.main.activity_add_ingredient.*
import kotlinx.android.synthetic.main.content_add_ingredient.*

class AddIngredientActivity : AppCompatActivity() {

    private val hints = arrayListOf<Hints>()

    private val foodAdapter =
        FoodAdapter(hints) { hint ->
            onFoodClick(hint)
        }

    private lateinit var selectedIngredient: Ingredient
    private lateinit var viewModel : AddIngredientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)
        setSupportActionBar(toolbar)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        //fabAddIngredient.setOnClickListener { onSaveClick() }
        rvFoodList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvFoodList.adapter = foodAdapter
        rvFoodList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        var extraIngredient = intent.getParcelableExtra<Ingredient>(com.example.calorietracker.ui.ingredients.EXTRA_INGREDIENT)
        extraIngredient?.let{ selectedIngredient = extraIngredient}
        btnSearchFood.setOnClickListener{onSearchClick()}
    }

    private fun onSaveClick() {

        if(::selectedIngredient.isInitialized) {
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_INGREDIENT, selectedIngredient)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        else{
            Toast.makeText(this,R.string.message_select_ingredient, Toast.LENGTH_LONG).show()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddIngredientViewModel::class.java)
        viewModel.foodList.observe(this, Observer {
            hints.clear()
            hints.addAll(it.hints)
            foodAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        const val EXTRA_INGREDIENT = "EXTRA_INGREDIENT"
    }

    private fun onFoodClick(hints: Hints){
        if(::selectedIngredient.isInitialized){
            selectedIngredient.name = hints.food.label
            selectedIngredient.calories = hints.food.nutrients.eNERC_KCAL
        }
        else
            selectedIngredient = Ingredient(hints.food.label, hints.food.nutrients.eNERC_KCAL)
        onSaveClick()
    }

    private fun onSearchClick(){
        if(etIngredientName.text.isNullOrBlank()){
            Toast.makeText(this,R.string.message_fill_search, Toast.LENGTH_LONG).show()
        }
        else{
            viewModel.getFoodList(etIngredientName.text.toString())
        }
    }

}
