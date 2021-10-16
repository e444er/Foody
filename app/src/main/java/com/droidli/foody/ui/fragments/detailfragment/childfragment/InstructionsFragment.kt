package com.droidli.foody.ui.fragments.detailfragment.childfragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.foody.R
import com.droidli.foody.databinding.InstructionsFragmentBinding
import com.droidli.foody.models.Result
import com.droidli.foody.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionsFragment : Fragment(R.layout.instructions_fragment) {

    private val binding by viewBinding(InstructionsFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding.instructionsWebView.webViewClient = object : WebViewClient() {}
        myBundle?.sourceUrl?.let { binding.instructionsWebView.loadUrl(it) }
    }
}