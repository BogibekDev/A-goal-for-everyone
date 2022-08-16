package com.agoal4every1.agoalforeveryone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.agoal4every1.agoalforeveryone.R
import com.agoal4every1.agoalforeveryone.databinding.FragmentSettingBinding
import com.agoal4every1.agoalforeveryone.manager.PrefManager
import com.agoal4every1.agoalforeveryone.service.SoundService
import com.agoal4every1.agoalforeveryone.utils.Extentions.click
import com.agoal4every1.agoalforeveryone.utils.viewBinding
import dev.b3nedikt.app_locale.AppLocale
import java.util.*

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private val binding by viewBinding { FragmentSettingBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.apply {
            ivMuteOn.click {
                PrefManager(requireContext()).saveBoolean("isMuted", false)
                requireActivity().startService(Intent(requireContext(), SoundService::class.java))
            }
            ivMuteOff.click {
                PrefManager(requireContext()).saveBoolean("isMuted", true)
                requireActivity().stopService(Intent(requireContext(), SoundService::class.java))
            }
            ivVibrationOn.click {
                PrefManager(requireContext()).saveBoolean("isVibrateOn", true)
            }
            ivVibrationOff.click {
                PrefManager(requireContext()).saveBoolean("isVibrateOn", false)
            }
            ivEn.click {
                PrefManager(requireContext()).saveString("language", "en")
                AppLocale.desiredLocale = Locale.ENGLISH
            }
            ivRu.click {
                PrefManager(requireContext()).saveString("language", "ru")
                AppLocale.desiredLocale = Locale.FRENCH
            }
            btnBack.click {
                requireActivity().onBackPressed()
            }
        }
    }


}