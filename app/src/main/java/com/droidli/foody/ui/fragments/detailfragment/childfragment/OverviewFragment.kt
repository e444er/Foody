package com.droidli.foody.ui.fragments.detailfragment.childfragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.droidli.foody.R
import com.droidli.foody.databinding.OverviewFragmentBinding
import com.droidli.foody.models.Result
import com.droidli.foody.utils.Constants.Companion.RECIPE_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.overview_fragment) {

    private val binding by viewBinding(OverviewFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.apply {
            Glide.with(root).load(myBundle?.image).into(mainImageView)
            titleTextView.text = myBundle?.title
            heartTextView.text = myBundle?.aggregateLikes.toString()
            timeTextView.text = myBundle?.readyInMinutes.toString()
            myBundle?.summary.let{
                val summary = Jsoup.parse(it).text()
                summaryTextView.text = summary
            }

            if (myBundle?.vegetarian == true) {
                vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.green))
                veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.vegan == true) {
                veganImageView.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.green))
                veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.glutenFree == true) {
                glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.green))
                glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.green))
            }
            if (myBundle?.dairyFree == true) {
                dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.green))
                dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.green))
            }

            if (myBundle?.veryHealthy == true) {
                healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.green))
                healthyTextView.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.green))
            }

            if (myBundle?.cheap == true) {
                cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(),
                    R.color.green))
                cheapTextView.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.green))
            }
        }
    }
}