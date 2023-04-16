package com.example.quizzed.activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.example.quizzed.R
import com.example.quizzed.models.Quiz
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    lateinit var quiz:Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setUpViews()
    }

    private fun setUpViews() {
        val quizData=intent.getStringExtra("QUIZ")
        quiz= Gson().fromJson(quizData,Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    @SuppressLint("SetTextI18n")
    private fun calculateScore(){
        var txtScore=findViewById<TextView>(R.id.txtScore)

        var score=0
        for(entry in quiz.questions.entries){
            val question=entry.value
            if(question.answer==question.userAnswer){
                score+=10
            }
        }
        txtScore.text="Your Score:$score"

    }

    private fun setAnswerView() {
        val txtAnswer=findViewById<TextView>(R.id.txtAnswer)
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }
}