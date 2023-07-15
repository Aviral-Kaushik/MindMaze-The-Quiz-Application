package com.aviral.mindmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aviral.mindmaze.R
import com.aviral.mindmaze.databinding.FragmentResultBinding
import com.aviral.mindmaze.viewModels.QuestionViewModel


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("quizId")?.let { viewModel.setQuizId(it) }

        viewModel.getResult()

        viewModel.getResultLiveData().observe(viewLifecycleOwner) { value ->

            val correct: Int = value["correct"] ?: 0
            val wrong: Int = value["wrong"] ?: 0
            val notAnswered: Int = value["notAnswered"] ?: 0

            binding.correctAnswerTv.text = correct.toString()
            binding.wrongAnswersTv.text = wrong.toString()
            binding.notAnsweredTv.text = notAnswered.toString()

            val total = correct + wrong + notAnswered

            val percentage = (correct * 100) / total

            binding.resultPercentageTv.text = (percentage).toString()

            binding.resultCoutProgressBar.progress = percentage

        }

        binding.homeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(
                R.id.action_resultFragment_to_listFragment
            )
        }
    }

}