package com.hfad.virtualmurabbiy.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hfad.virtualmurabbiy.databinding.FragmentBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIFragment : Fragment() {

    companion object {
        private const val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNIT_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNIT_VIEW

    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        makeUnitsVisible()

        binding.btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }

        return binding.root
    }

    private fun makeUnitsVisible() {
        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMetric.id -> {
                    makeVisibleMetricUnitsView()
                }
                binding.rbUsunit.id -> {
                    makeVisibleUsUnitsView()
                }
            }
        }
    }

    private fun makeVisibleMetricUnitsView(): Boolean {
        currentVisibleView = METRIC_UNIT_VIEW
        binding.clMetric.visibility = View.VISIBLE
        binding.clUsUnit.visibility = View.INVISIBLE

        binding.usWeight.text?.clear()
        binding.usHeightFeet.text?.clear()
        binding.usHeightInch.text?.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE

        return true
    }

    private fun makeVisibleUsUnitsView(): Boolean {
        currentVisibleView = US_UNIT_VIEW
        binding.clUsUnit.visibility = View.VISIBLE
        binding.clMetric.visibility = View.INVISIBLE

        binding.mtHeight.text?.clear()
        binding.mtWeight.text?.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE

        return true
    }

    private fun verifyDataFromUser(weight: String, height: String): Boolean {
        return !(TextUtils.isEmpty(weight) || TextUtils.isEmpty(height))
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        when {
            bmi <= 15f -> {
                bmiLabel = "Xavfli!! Judaham kam vazn "
                bmiDescription = "Voy! O'zingizni ancha oldirib qoyibsiz! Ko'proq ovqatlaning"
            }
            bmi <= 16f -> {
                bmiLabel = "Judaham kam vazn"
                bmiDescription = "Voy! O'zingizni ancha oldirib qoyibsiz! Ko'proq ovqatlaning"
            }
            bmi <= 18.5f -> {
                bmiLabel = "Kam vaznli"
                bmiDescription = "Ana bo'lmasam! Biroz ko'proq ovqatlaning"
            }
            bmi <= 25f -> {
                bmiLabel = "Me'yorida"
                bmiDescription = "Tabriklaymiz! Siz yaxshi holatdasiz!"
            }
            bmi <= 30f -> {
                bmiLabel = "Ortiqcha vazn"
                bmiDescription =
                    "Ana bo'lmasam! Biroz kamroq ovqatlaning! Badantarbiya mashqlarini bajarib turin"
            }
            bmi <= 35f -> {
                bmiLabel = "Semizlik || (O'rtacha semizlik)"
                bmiDescription = "Voy! Kamroq ovqatlaning! Badantarbiya mashqlarini bajarib turin"
            }
            bmi <= 40f -> {
                bmiLabel = "Semizlik || (Ortiqcha semizlik)"
                bmiDescription = "OMG! Siz juda xavfli holatdasiz! Hozirdan harakatni boshlang!"
            }
            else -> {
                bmiLabel = "Semizlik || (Haddan tashqari semizlik)"
                bmiDescription = "OMG! Siz juda xavfli holatdasiz! Hozirdan harakatni boshlang!"
            }
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.apply {
            llDisplayBMIResult.visibility = View.VISIBLE
            tvBmiValue.text = bmiValue
            tvBmiType.text = bmiLabel
            tvBmiDescription.text = bmiDescription
        }
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNIT_VIEW) {
            if (makeVisibleMetricUnitsView()) {
                val metricWeight = binding.mtWeight.text.toString()
                val metricHeight = binding.mtHeight.text.toString()

                if (verifyDataFromUser(metricWeight, metricHeight)) {
                    val weightValue: Float = metricWeight.toFloat()
                    val heightValue: Float = metricHeight.toFloat() / 100
                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(requireContext(),
                        "Iltimos,to'g'ri miqdorni kiritin",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else if (makeVisibleUsUnitsView()) {
            val usWeight = binding.usWeight.text.toString()
            val usFeet = binding.usHeightFeet.text.toString()
            val usInch = binding.usHeightInch.text.toString()
            if (verifyDataFromUser(usWeight, usFeet) && usInch.isNotEmpty()) {
                val weightValue = usWeight.toFloat()
                val feetValue = usFeet.toFloat()
                val inchValue = usInch.toFloat()

                val heightValue = inchValue + feetValue * 12
                val bmi = 703 * (weightValue / (heightValue * heightValue))
                displayBMIResult(bmi)
            } else {
                Toast.makeText(requireContext(),
                    "Iltimos,to'g'ri miqdorni kiritin",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}