package com.hfad.virtualmurabbiy.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.virtualmurabbiy.R
import com.hfad.virtualmurabbiy.databinding.FragmentExerciseBinding
import com.hfad.virtualmurabbiy.manager.Constants
import com.hfad.virtualmurabbiy.manager.ExerciseModel
import com.hfad.virtualmurabbiy.manager.ExerciseStatusAdapter
import java.util.*

class ExerciseFragment : Fragment(), TextToSpeech.OnInitListener {
    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!
    private val adapter: ExerciseStatusAdapter by lazy { ExerciseStatusAdapter() }

    private lateinit var exerciseList: List<ExerciseModel>
    private var currentExercisePosition = -1

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimeDuration: Long = 1
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimeDuration: Long = 3

    private lateinit var tts: TextToSpeech
    private var player: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
        showRecyclerView()
        tts = TextToSpeech(context, this)
        return binding.root
    }

    private fun setUpRestView() {
        try {
            val soundURI =
                Uri.parse("android.resource://com.hfad.virtualmurabbiy/" + R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(requireContext(), soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvCurrentExerciseName.visibility = View.INVISIBLE
        binding.flCurrentExerciseView.visibility = View.INVISIBLE
        binding.tvImage.visibility = View.INVISIBLE
        binding.tvUpcomingLabel.visibility = View.VISIBLE
        binding.tvUpcomingExerciseName.visibility = View.VISIBLE

        binding.tvUpcomingExerciseName.text = exerciseList[currentExercisePosition + 1].getName()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()

    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(restTimeDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList[currentExercisePosition].setIsSelected(true)
                adapter.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpExerciseView() {
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvCurrentExerciseName.visibility = View.VISIBLE
        binding.flCurrentExerciseView.visibility = View.VISIBLE
        binding.tvImage.visibility = View.VISIBLE
        binding.tvUpcomingLabel.visibility = View.INVISIBLE
        binding.tvUpcomingExerciseName.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList[currentExercisePosition].getName())
        binding.tvImage.setImageResource(exerciseList[currentExercisePosition].getImage())
        binding.tvCurrentExerciseName.text = exerciseList[currentExercisePosition].getName()
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding.currentProgressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimeDuration * 1000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.currentProgressBar.progress = 30 - exerciseProgress
                binding.tvCurrentTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList.size - 1) {
                    exerciseList[currentExercisePosition].setIsSelected(false)
                    exerciseList[currentExercisePosition].setIsCompleted(true)
                    adapter.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    findNavController().navigate(R.id.action_exerciseFragment_to_finishFragment)
                }
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        tts.stop()
        tts.shutdown()

        if (player != null){
            player!!.stop()
        }

        _binding = null
    }

    private fun showRecyclerView() {
        val recyclerView = binding.rvExerciseStatus
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter.setData(exerciseList)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization failed!")
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}