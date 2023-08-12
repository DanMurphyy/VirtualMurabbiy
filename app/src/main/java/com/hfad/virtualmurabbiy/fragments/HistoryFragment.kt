package com.hfad.virtualmurabbiy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.hfad.virtualmurabbiy.data.HistoryAdapter
import com.hfad.virtualmurabbiy.data.HistoryData
import com.hfad.virtualmurabbiy.data.HistoryViewModel
import com.hfad.virtualmurabbiy.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private val mHistoryViewModel: HistoryViewModel by viewModels()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        mHistoryViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })
        emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseView(it)
        })

        binding.btnDeleteAll.setOnClickListener {
            confirmRemoval()
        }


        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView2.loadAd(adRequest)
        return binding.root
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ha") { _, _ ->
            mHistoryViewModel.deleteAll()
            Toast.makeText(requireContext(), "Barchasi muvaffaqiyatli o'chirildi!",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Yoq") { _, _ -> }
        builder.setTitle("O'chirish so'rovi?")
        builder.setMessage("Barchasini o'chirib tashlamoqchimisiz?")
        builder.create().show()
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
    fun checkIfDatabaseEmpty(historyData: List<HistoryData>) {
        emptyDatabase.value = historyData.isEmpty()
    }

    private fun showEmptyDatabaseView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.noTitleData.visibility = View.VISIBLE
            binding.titlesData.visibility = View.INVISIBLE
        } else {
            binding.noTitleData.visibility = View.INVISIBLE
            binding.titlesData.visibility = View.VISIBLE
        }
    }
}