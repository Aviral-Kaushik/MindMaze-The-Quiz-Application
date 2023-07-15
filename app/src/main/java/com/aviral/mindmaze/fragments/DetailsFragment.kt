package com.aviral.mindmaze.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aviral.mindmaze.R
import com.aviral.mindmaze.databinding.FragmentDetailsBinding
import com.aviral.mindmaze.viewModels.QuizViewModel
import com.bumptech.glide.Glide
import kotlin.properties.Delegates

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private lateinit var viewModel: QuizViewModel

    private var totalQuestionCount by Delegates.notNull<Long>()

    private lateinit var quizId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position: Int = arguments?.getInt("position") ?: 0

        viewModel.getQuizLiveData().observe(viewLifecycleOwner) { value ->

            val quizListModel = value[position]

            binding.detailFragmentDifficulty.text = quizListModel.difficulty
            binding.detailFragmentTitle.text = quizListModel.title
            binding.detailFragmentQuestions.text = quizListModel.questions.toString()

            Glide.with(view)
                .load(quizListModel.image)
                .into(binding.detailFragmentImage)

            val handler = Handler()

            handler.postDelayed({
                binding.detailProgressBar.visibility = View.GONE
            }, 2000)

            totalQuestionCount = quizListModel.questions

            quizId = quizListModel.quizId
        }

        binding.startQuizBtn.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("quizId", quizId)
            bundle.putLong("totalQuestionCount", totalQuestionCount)

            Navigation.findNavController(view)
                .navigate(R.id.action_detailsFragment_to_quizFragment, bundle)

        }

    }

}