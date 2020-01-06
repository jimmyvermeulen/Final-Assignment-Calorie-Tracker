package com.example.calorietracker.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.*
import com.example.calorietracker.ui.adapters.DayRecipeAdapter
import com.example.calorietracker.ui.addDayRecipe.AddDayRecipeActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

const val ADD_DAY_RECIPE_REQUEST_CODE = 100

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var dayWithRecipes: DayWithRecipes = DayWithRecipes(Day(Calendar.getInstance().time, 0), listOf<Recipe>())
    private val recipeAdapter = DayRecipeAdapter(dayWithRecipes.recipes)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabHome.setOnClickListener {
            startAddActivity()
        }
        rvMeals.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvMeals.adapter = recipeAdapter
        rvMeals.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private fun initViewModel(){
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        /*homeViewModel.dayWithRecipes.observe(this, Observer { day ->
            this@HomeFragment.dayWithRecipes = day
            recipeAdapter.notifyDataSetChanged()
        })*/
    }

    private fun startAddActivity(){
        val intent = Intent(activity, AddDayRecipeActivity::class.java)
        startActivityForResult(intent, ADD_DAY_RECIPE_REQUEST_CODE)
    }




}