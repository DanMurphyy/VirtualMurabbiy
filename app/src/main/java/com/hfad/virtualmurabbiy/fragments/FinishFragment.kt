package com.hfad.virtualmurabbiy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.data.HistoryData
import com.hfad.virtualmurabbiy.data.HistoryViewModel
import com.hfad.virtualmurabbiy.databinding.FragmentFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    private val mHistoryViewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        addDatatoDatabse()

        binding.btnFinish.setOnClickListener {
            findNavController().navigate(R.id.action_finishFragment_to_welcomeFragment)
        }

        return binding.root
    }

    private fun addDatatoDatabse() {
        val c = Calendar.getInstance()
        val dataTime = c.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dataTime)
        val newData = HistoryData(date)
        mHistoryViewModel.insertData(newData)
    }
}