package com.hfad.virtualmurabbiy.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.flStart.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_exerciseFragment)
        }

        binding.flBmi.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_BMIFragment)
        }

        binding.flHistory.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_historyFragment)
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.welcome_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_info -> findNavController().navigate(R.id.action_welcomeFragment_to_infoFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}