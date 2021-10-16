package com.droidli.foody.ui.fragments.detailfragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.R
import com.droidli.foody.adapter.PagerAdapter
import com.droidli.foody.databinding.DetailFragmentBinding
import com.droidli.foody.ui.fragments.detailfragment.childfragment.IngredientsFragment
import com.droidli.foody.ui.fragments.detailfragment.childfragment.InstructionsFragment
import com.droidli.foody.ui.fragments.detailfragment.childfragment.OverviewFragment
import com.droidli.foody.utils.Constants.Companion.RECIPE_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding by viewBinding(DetailFragmentBinding::bind)
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val _adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            activity?.supportFragmentManager!!
        )
        binding.viewPager.adapter = _adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.home){
//            activity?.finish()
//        }
//        return super.onOptionsItemSelected(item)
//    }
}