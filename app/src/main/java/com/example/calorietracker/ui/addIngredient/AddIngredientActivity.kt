package com.example.calorietracker.ui.addIngredient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calorietracker.R
import com.example.calorietracker.model.Ingredient

import kotlinx.android.synthetic.main.activity_add_ingredient.*
import kotlinx.android.synthetic.main.content_add_ingredient.*

class AddIngredientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        fabAddIngredient.setOnClickListener { onSaveClick() }
    }

    private fun onSaveClick() {

        val ingredient = Ingredient(etIngredientName.text.toString(), etIngredientCalories.text.toString().toDouble())

        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_INGREDIENT, ingredient)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val EXTRA_INGREDIENT = "EXTRA_INGREDIENT"
    }

}
