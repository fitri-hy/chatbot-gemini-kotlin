package com.hytech.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var questionInput: EditText
    private lateinit var searchButton: Button
    private lateinit var answerText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionInput = findViewById(R.id.question_input)
        searchButton = findViewById(R.id.search_button)
        answerText = findViewById(R.id.answer_text)
        progressBar = findViewById(R.id.progress_bar)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hy-tech.my.id/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val geminiApiService = retrofit.create(GeminiApiService::class.java)

        searchButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val question = questionInput.text.toString()
                if (question.isNotEmpty()) {
                    progressBar.visibility = View.VISIBLE

                    lifecycleScope.launch {
                        val response = geminiApiService.getAnswer(question)
                        progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            answerText.text = response.body()?.text ?: ""
                        } else {
                            answerText.text = getString(R.string.error)
                        }
                    }
                }
            }
        })
    }
}