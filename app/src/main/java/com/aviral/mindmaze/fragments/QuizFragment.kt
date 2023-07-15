package com.aviral.mindmaze.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aviral.mindmaze.R
import com.aviral.mindmaze.databinding.FragmentQuizBinding
import com.aviral.mindmaze.viewModels.QuestionViewModel
import kotlin.properties.Delegates

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding

    private lateinit var viewModel: QuestionViewModel

    private lateinit var navController: NavController

    private lateinit var countDownTimer: CountDownTimer

    private lateinit var answer: String

    private var totalQuestionCount by Delegates.notNull<Long>()

    private var currentQuestionNumber = 0

    private var canAnswer = false

    private var timerCount: Long = 10L

    private var notAnswered: Int = 0
    private var correctAnswer: Int = 0
    private var wrongAnswer: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        arguments?.getString("quizId")?.let { viewModel.setQuizId(it) }

        viewModel.getQuestions()

        arguments?.getLong("totalQuestionCount")?.let {
            totalQuestionCount = it
        }

        loadData()

        binding.option1Btn.setOnClickListener {
            verifyAnswer(binding.option1Btn)

            notAnswered = 1
        }

        binding.option2Btn.setOnClickListener {
            verifyAnswer(binding.option2Btn)

            notAnswered = 1
        }

        binding.option3Btn.setOnClickListener {
            verifyAnswer(binding.option3Btn)

            notAnswered = 1
        }

        binding.nextQueBtn.setOnClickListener {

            if (currentQuestionNumber == totalQuestionCount.toInt()) {
                submitResult()
            } else {
                currentQuestionNumber++
                loadQuestions(currentQuestionNumber)
                resetOptions()
            }

        }

        binding.closeQuizBtn.setOnClickListener {

            navController.navigate(
                R.id.action_quizFragment_to_listFragment,
            )
        }
    }

    private fun verifyAnswer(button: Button) {

        if (canAnswer) {

            if (answer == button.text) {
                button.background =
                    context?.let { AppCompatResources.getDrawable(it, R.color.green) }

                correctAnswer++

                binding.ansFeedbackTv.text = "Correct Answer"

            } else {

                button.background =
                    context?.let { AppCompatResources.getDrawable(it, R.color.red) }

                wrongAnswer++

                binding.ansFeedbackTv.text = "Wrong Answer \n Correct Answer: $answer"

            }

        }

        canAnswer = false

        countDownTimer.cancel()

        showNextButton()

    }

    private fun resetOptions() {

        binding.ansFeedbackTv.visibility = View.INVISIBLE
        binding.nextQueBtn.visibility = View.INVISIBLE
        binding.nextQueBtn.isEnabled = false

        binding.option1Btn.background =
            context?.let { AppCompatResources.getDrawable(it, R.color.light_sky) }
        binding.option2Btn.background =
            context?.let { AppCompatResources.getDrawable(it, R.color.light_sky) }
        binding.option3Btn.background =
            context?.let { AppCompatResources.getDrawable(it, R.color.light_sky) }

    }

    private fun submitResult() {

        val resultMap = HashMap<String, Int>()
        resultMap["correct"] = correctAnswer
        resultMap["wrong"] = wrongAnswer
        resultMap["notAnswered"] = notAnswered

        viewModel.addResult(resultMap)

        val bundle = Bundle()
        bundle.putString("quizId", arguments?.getString("quizId"))

        navController.navigate(
            R.id.action_quizFragment_to_resultFragment,
            bundle
        )
    }

    private fun loadData() {
        enableOptions()
        loadQuestions(1)
    }

    private fun enableOptions() {
        binding.option1Btn.visibility = View.VISIBLE
        binding.option2Btn.visibility = View.VISIBLE
        binding.option3Btn.visibility = View.VISIBLE

        binding.option1Btn.isEnabled = true
        binding.option2Btn.isEnabled = true
        binding.option3Btn.isEnabled = true

        binding.ansFeedbackTv.visibility = View.INVISIBLE
        binding.nextQueBtn.visibility = View.INVISIBLE
    }

    private fun loadQuestions(i: Int) {

        currentQuestionNumber = i

        viewModel.getQuestionLiveData().observe(viewLifecycleOwner) { value ->

            binding.quizQuestionTv.text = value[i - 1].question

            binding.option1Btn.text = value[i - 1].option_a
            binding.option2Btn.text = value[i - 1].option_b
            binding.option3Btn.text = value[i - 1].option_c
            binding.option1Btn.text = value[i - 1].option_a

            timerCount = value[i - 1].timer
            answer = value[i - 1].answer

            binding.quizQuestionsCount.text = currentQuestionNumber.toString()

            startTimer()

        }

        canAnswer = true
    }

    private fun startTimer() {

        binding.quizCoutProgressBar.visibility = View.VISIBLE

        binding.countTimeQuiz.text = timerCount.toString()

        countDownTimer = object : CountDownTimer(timerCount * 1000, 1000) {

            override fun onTick(p0: Long) {

                binding.countTimeQuiz.text = ((p0 / 1000).toString())

                val percent = p0 / (timerCount * 10)

                binding.quizCoutProgressBar.progress = percent.toInt()

            }

            override fun onFinish() {

                canAnswer = false

                binding.ansFeedbackTv.text = "Time Up !! No answer selected"

                notAnswered++

                showNextButton()

            }
        }.start()
    }

    private fun showNextButton() {

        binding.nextQueBtn.visibility = View.VISIBLE
        binding.nextQueBtn.isEnabled = true

        if (currentQuestionNumber == totalQuestionCount.toInt()) {

            binding.nextQueBtn.text = "Submit"

        } else {

            binding.nextQueBtn.visibility = View.VISIBLE
            binding.ansFeedbackTv.visibility = View.VISIBLE

        }

    }

}