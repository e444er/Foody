package com.droidli.foody.ui.fragments.detailfragment.childfragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        binding.apply {
            Glide.with(root).load(myBundle.image).into(mainImageView)
            titleTextView.text = myBundle.title
            heartTextView.text = myBundle.aggregateLikes.toString()
            timeTextView.text = myBundle.readyInMinutes.toString()
            myBundle.summary.let {
                val summary = Jsoup.parse(it).text()
                summaryTextView.text = summary
            }
        }
        setColors(myBundle)
    }

    private fun setColors(myBundle: Result) {
        updateColors(myBundle.vegetarian, binding.vegetarianTextView, binding.vegetarianImageView)
        updateColors(myBundle.vegan, binding.veganTextView, binding.veganImageView)
        updateColors(myBundle.cheap, binding.cheapTextView, binding.cheapImageView)
        updateColors(myBundle.dairyFree, binding.dairyFreeTextView, binding.dairyFreeImageView)
        updateColors(myBundle.glutenFree, binding.glutenFreeTextView, binding.glutenFreeImageView)
        updateColors(myBundle.veryHealthy, binding.healthyTextView, binding.healthyImageView)
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }
}