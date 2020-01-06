package com.example.calorietracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.model.*
import com.example.calorietracker.ui.adapters.DayRecipeAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val todaysRecipes = arrayListOf<Recipe>()
    private val recipeAdapter =
        DayRecipeAdapter(todaysRecipes)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabHome.setOnClickListener {
        }
        rvMeals.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvMeals.adapter = recipeAdapter
        rvMeals.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private fun initViewModel(){
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.setDay()
        /*homeViewModel.dayWithRecipes.observe(this, Observer { dayWithRecipes ->
            this@HomeFragment.todaysRecipes.clear()
            this@HomeFragment.todaysRecipes.addAll(dayWithRecipes.recipes)
            recipeAdapter.notifyDataSetChanged()
        })*/
    }


}