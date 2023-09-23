package com.slayton.msu.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.slayton.msu.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var correctAnswerCounter = 0
    private var submittedAnswersCounter = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )
    private var currentIndex = 0
    private var numberOfQuestionsIndex = questionBank.size - 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            recordAnswer(true)
            toggleButtons("trueButton")

        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
            recordAnswer(false)
            toggleButtons("falseButton")

        }
        binding.nextButton.setOnClickListener{
            currentIndex=(currentIndex + 1) % questionBank.size
            updateQuestion()
            toggleButtons("nextButton")
        }
        binding.getScoreButton.setOnClickListener{
            calculateAndDisplayScore(correctAnswerCounter)
            //Log.d("TAG", "This is your score: $score.")
        }
        updateQuestion()
    }

    private fun calculateAndDisplayScore(correctAnswers: Int) {

        val numberOfQuestions = questionBank.size
        val score = (correctAnswers.toFloat() / numberOfQuestions.toFloat()) * 100
        val formattedScore = String.format("%.1f", score)
        Log.d("TAG", "This is your score: $formattedScore.")
    }

    private fun recordAnswer(submittedAnswer: Boolean){
        // check answer against the answer in the question class
        val correctAnswer = questionBank[currentIndex].answer
        submittedAnswersCounter++
        // if the answers match, increment the correctAnswerCounter
        if(correctAnswer == submittedAnswer) {
            correctAnswerCounter++
            //Log.d("TAG", correctAnswerCounter.toString())
        }
    }

    private fun toggleButtons(buttonID: String){
        if(buttonID == "trueButton" || buttonID == "falseButton") {
            //disable the buttons
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
            //Log.d("TAG", "This is how many answers have been submitted: $submittedAnswersCounter.")
            if(submittedAnswersCounter == questionBank.size) {
                binding.nextButton.isEnabled = false
                binding.getScoreButton.isVisible = true
            }
        }
        else if (buttonID == "nextButton"){
            //enable the buttons
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer (userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        }
        else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}