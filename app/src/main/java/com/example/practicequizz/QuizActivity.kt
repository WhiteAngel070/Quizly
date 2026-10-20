package com.example.practicequizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "QuizActivity"
        const val EXTRA_SCORE = "EXTRA_SCORE"
        const val EXTRA_TOTAL = "EXTRA_TOTAL"
    }

    data class Question(
        val text: String,
        val answers: List<String>,
        val correctIndex: Int
    )

    private val questions = listOf(
        Question(
            "Какой язык используется для разработки Android-приложений?",
            listOf("Kotlin", "HTML", "CSS", "SQL"),
            0
        ),
        Question(
            "Что означает HTML?",
            listOf(
                "High Text Machine Language",
                "HyperText Markup Language",
                "Hyper Transfer Main Link",
                "Home Tool Markup Language"
            ),
            1
        ),
        Question(
            "Какой тип данных хранит true или false?",
            listOf("String", "Int", "Boolean", "Double"),
            2
        ),
        Question(
            "Какое ключевое слово объявляет неизменяемую переменную в Kotlin?",
            listOf("var", "let", "const", "val"),
            3
        ),
        Question(
            "Какой метод вызывается при создании Activity?",
            listOf("onCreate()", "onStop()", "onPause()", "onDestroy()"),
            0
        )
    )

    private var currentQuestion = 0
    private var score = 0
    private var answered = false

    private lateinit var questionText: TextView
    private lateinit var progressText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextButton: Button
    private lateinit var answerButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        Log.d(TAG, "onCreate: экран викторины создан")

        questionText = findViewById(R.id.tvQuestion)
        progressText = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        nextButton = findViewById(R.id.btnNext)

        answerButtons = listOf(
            findViewById(R.id.btnAnswer1),
            findViewById(R.id.btnAnswer2),
            findViewById(R.id.btnAnswer3),
            findViewById(R.id.btnAnswer4)
        )

        answerButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                checkAnswer(index)
            }
        }

        nextButton.setOnClickListener {
            currentQuestion++

            if (currentQuestion < questions.size) {
                showQuestion()
            } else {
                finishQuiz()
            }
        }

        showQuestion()
    }

    private fun showQuestion() {
        answered = false

        val question = questions[currentQuestion]

        questionText.text = question.text

        progressText.text = getString(
            R.string.quiz_progress,
            currentQuestion + 1,
            questions.size
        )

        progressBar.max = questions.size
        progressBar.progress = currentQuestion + 1

        answerButtons.forEachIndexed { index, button ->
            button.text = question.answers[index]
            button.setBackgroundResource(R.drawable.answer_default)
            button.setTextColor(getColor(R.color.text_primary))
            button.isEnabled = true
        }

        nextButton.visibility = View.GONE

        Log.d(TAG, "Показан вопрос ${currentQuestion + 1}")
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (answered) return

        answered = true

        val correctIndex = questions[currentQuestion].correctIndex

        if (selectedIndex == correctIndex) {
            score++
            Log.i(TAG, "Правильный ответ. Баллы: $score")
        } else {
            Log.i(TAG, "Неправильный ответ на вопрос ${currentQuestion + 1}")
        }

        answerButtons.forEachIndexed { index, button ->
            button.isEnabled = false

            when {
                index == correctIndex -> {
                    button.setBackgroundResource(R.drawable.answer_correct)
                    button.setTextColor(getColor(R.color.answer_correct_text))
                }

                index == selectedIndex -> {
                    button.setBackgroundResource(R.drawable.answer_wrong)
                    button.setTextColor(getColor(R.color.answer_wrong_text))
                }
            }
        }

        nextButton.visibility = View.VISIBLE

        if (currentQuestion == questions.lastIndex) {
            nextButton.text = getString(R.string.show_result)
        } else {
            nextButton.text = getString(R.string.next_question)
        }
    }

    private fun finishQuiz() {
        val name = intent.getStringExtra(MainActivity.EXTRA_NAME) ?: ""

        Log.i(TAG, "Викторина завершена. Результат: $score/${questions.size}")

        val resultIntent = Intent(this, ResultActivity::class.java)

        resultIntent.putExtra(MainActivity.EXTRA_NAME, name)
        resultIntent.putExtra(EXTRA_SCORE, score)
        resultIntent.putExtra(EXTRA_TOTAL, questions.size)

        startActivity(resultIntent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}