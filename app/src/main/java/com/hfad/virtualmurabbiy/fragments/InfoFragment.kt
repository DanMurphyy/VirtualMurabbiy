package com.hfad.virtualmurabbiy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        gif2()
        gif1()
        gif()

        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView1.loadAd(adRequest)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun gif2() {
        val gifImageView: ImageView = binding.gif2
        Glide.with(this)
            .asGif()
            .load(R.drawable.giphy2) // Assuming "fire.gif" is the name of your animated GIF file
            .into(gifImageView)
    }

    fun gif1() {
        val gifImageView: ImageView = binding.gif1
        Glide.with(this)
            .asGif()
            .load(R.drawable.giphy1) // Assuming "fire.gif" is the name of your animated GIF file
            .into(gifImageView)
    }

    fun gif() {
        val gifImageView: ImageView = binding.gif
        Glide.with(this)
            .asGif()
            .load(R.drawable.giphy) // Assuming "fire.gif" is the name of your animated GIF file
            .into(gifImageView)
    }
}