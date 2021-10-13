package com.droidli.foody

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.databinding.RecipesFragmentBinding

class RecipesFragment : Fragment(R.layout.recipes_fragment) {

    private val binding by viewBinding(RecipesFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerRecyclerView.showShimmer()
    }
}