package com.example.practicequizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        const val EXTRA_NAME = "EXTRA_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: главный экран создан")

        val nameInput = findViewById<EditText>(R.id.etName)
        val startButton = findViewById<Button>(R.id.btnStart)

        startButton.setOnClickListener {

            val name = nameInput.text.toString().trim()

            if (name.isEmpty()) {
                nameInput.error = getString(R.string.name_required)
                Log.w(TAG, "Пользователь не ввёл имя")
                return@setOnClickListener
            }

            Log.i(TAG, "Начало викторины")

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra(EXTRA_NAME, name)

            startActivity(intent)
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