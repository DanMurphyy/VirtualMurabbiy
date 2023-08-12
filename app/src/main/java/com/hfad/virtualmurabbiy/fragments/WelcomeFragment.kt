package com.hfad.virtualmurabbiy.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        val adInRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),
            "ca-app-pub-8558811277281829/6627365053",
            adInRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded")
                    mInterstitialAd = interstitialAd
                }
            })


        binding.flStart.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_exerciseFragment)
        }

        binding.flBmi.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_BMIFragment)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }

        binding.flHistory.setOnClickListener {

            findNavController().navigate(R.id.action_welcomeFragment_to_historyFragment)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }

        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.welcome_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_info -> findNavController().navigate(R.id.action_welcomeFragment_to_infoFragment)
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val shareText =
            "Virtual Murabbiy ilovasini yuklab oling \nhttps://play.google.com/store/apps/details?id=com.hfad.virtualmurabbiy"
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Share via"))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}