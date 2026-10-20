package com.example.practicequizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ResultActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        Log.d(TAG, "onCreate: экран результатов создан")

        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)
            ?: getString(R.string.result_missing_name)

        val score = intent.getIntExtra(QuizActivity.EXTRA_SCORE, 0)
        val total = intent.getIntExtra(QuizActivity.EXTRA_TOTAL, 5)

        Log.i(TAG, "Получен результат: $name, $score/$total")

        val greetingText = findViewById<TextView>(R.id.tvResultGreeting)
        val scoreText = findViewById<TextView>(R.id.tvResultScore)
        val messageText = findViewById<TextView>(R.id.tvResultMessage)
        val progressBar = findViewById<ProgressBar>(R.id.resultProgress)

        val retryButton = findViewById<Button>(R.id.btnRetry)
        val homeButton = findViewById<Button>(R.id.btnHome)

        greetingText.text = getString(R.string.result_greeting, name)

        scoreText.text = getString(R.string.result_score, score, total)

        progressBar.max = total
        progressBar.progress = score

        val percentage = if (total > 0) {
            score.toFloat() / total
        } else {
            0f
        }

        messageText.text = when {
            percentage >= 0.8f -> getString(R.string.result_excellent)
            percentage >= 0.5f -> getString(R.string.result_good)
            else -> getString(R.string.result_try_again)
        }

        retryButton.setOnClickListener {
            Log.i(TAG, "Пользователь начал викторину заново")

            val quizIntent = Intent(this, QuizActivity::class.java)
            quizIntent.putExtra(MainActivity.EXTRA_NAME, name)

            startActivity(quizIntent)
            finish()
        }

        homeButton.setOnClickListener {
            Log.i(TAG, "Пользователь вернулся на главный экран")

            val homeIntent = Intent(this, MainActivity::class.java)

            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP

            startActivity(homeIntent)
            finish()
        }
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