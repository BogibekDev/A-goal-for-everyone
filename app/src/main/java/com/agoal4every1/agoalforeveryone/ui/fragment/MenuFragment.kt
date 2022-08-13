package com.agoal4every1.agoalforeveryone.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.agoal4every1.agoalforeveryone.R
import com.agoal4every1.agoalforeveryone.databinding.FragmentMenuBinding
import com.agoal4every1.agoalforeveryone.utils.viewBinding


class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding { FragmentMenuBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.apply {

            bSetting.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_settingFragment)
            }

            bGame.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
            }
        }
    }

}