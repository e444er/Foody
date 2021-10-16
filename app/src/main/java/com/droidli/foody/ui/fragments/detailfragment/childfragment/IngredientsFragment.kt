package com.droidli.foody.ui.fragments.detailfragment.childfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.R
import com.droidli.foody.adapter.IngredientAdapter
import com.droidli.foody.databinding.IngredientsFragmentBinding
import com.droidli.foody.models.Result
import com.droidli.foody.utils.Constants.Companion.RECIPE_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientsFragment : Fragment(R.layout.ingredients_fragment) {

    private val binding by viewBinding(IngredientsFragmentBinding::bind)
    private val mAdapter: IngredientAdapter by lazy { IngredientAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        setupRecyclerview()
        myBundle?.extendedIngredients?.let { mAdapter.differIn.submitList(it) }
    }

    private fun setupRecyclerview() {
        binding.ingredientsRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}