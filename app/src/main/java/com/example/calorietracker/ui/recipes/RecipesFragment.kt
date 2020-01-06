package com.example.calorietracker.ui.recipes

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
import com.example.calorietracker.R
import com.example.calorietracker.model.RecipeWithIngredients
import com.example.calorietracker.ui.adapters.RecipeAdapter
import com.example.calorietracker.ui.addRecipe.AddRecipeActivity
import kotlinx.android.synthetic.main.fragment_recipes.*

const val ADD_RECIPE_REQUEST_CODE = 100

class RecipesFragment : Fragment() {

    private val recipesWithIngredients = arrayListOf<RecipeWithIngredients>()
    private val recipeAdapter =
        RecipeAdapter(recipesWithIngredients) { recipeWithIngredients ->
            onRecipeClick(recipeWithIngredients)
        }
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val root = inflater.inflate(R.layout.fragment_recipes, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_dashboard)
        recipesViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabRecipes.setOnClickListener {
            startAddActivity()
        }
        rvRecipes.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvRecipes.adapter = recipeAdapter
        rvRecipes.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvRecipes)
    }

    private fun startAddActivity(){
        val intent = Intent(activity, AddRecipeActivity::class.java)
        startActivityForResult(intent, ADD_RECIPE_REQUEST_CODE)
    }

    private fun initViewModel(){
        recipesViewModel =
            ViewModelProviders.of(this).get(RecipesViewModel::class.java)
        recipesViewModel.recipesWithIngredients.observe(this, Observer { recipes ->
            this@RecipesFragment.recipesWithIngredients.clear()
            this@RecipesFragment.recipesWithIngredients.addAll(recipes)
            recipeAdapter.notifyDataSetChanged()
        })
    }

    private fun onRecipeClick(recipeWithIngredients: RecipeWithIngredients){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_RECIPE_REQUEST_CODE -> {
                    val recipeWithIngredients = data!!.getParcelableExtra<RecipeWithIngredients>(AddRecipeActivity.EXTRA_RECIPE)
                    val ingredientAmounts = data!!.getDoubleArrayExtra(AddRecipeActivity.EXTRA_AMOUNTS).toList()

                    recipesViewModel.insertRecipeWithIngredients(recipeWithIngredients, ingredientAmounts)
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
                val recipeToDelete = recipesWithIngredients[position].recipe
                recipesViewModel.deleteRecipe(recipeToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

}