package com.example.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzed.R
import com.example.quizzed.adapters.OptionAdapter
import com.example.quizzed.models.Question
import com.example.quizzed.models.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {

    var quizzes:MutableList<Quiz>?=null
    var questions:MutableMap<String,Question>?=null
    var index=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)


        //bindViews()
        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        val btnPrevious:Button=findViewById(R.id.btnPrevious)
        val btnSubmit: Button =findViewById(R.id.btnSubmit)
        val btnNext:Button=findViewById(R.id.btnNext)
        btnPrevious.setOnClickListener{
            index--
            bindViews()
        }
        btnNext.setOnClickListener{
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener{
            //Log.d("Final Quiz",questions.toString())

            val intent=Intent(this,ResultActivity::class.java)
            val json=Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if(it != null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        val btnPrevious:Button=findViewById(R.id.btnPrevious)
        val btnSubmit: Button =findViewById(R.id.btnSubmit)
        val btnNext:Button=findViewById(R.id.btnNext)

        btnPrevious.visibility= View.GONE
        btnSubmit.visibility=View.GONE
        btnNext.visibility=View.GONE

        if(index==1){
            btnNext.visibility=View.VISIBLE
        }
        else if(index==questions!!.size){
            btnSubmit.visibility=View.VISIBLE
            btnPrevious.visibility=View.VISIBLE
        }
        else{
            btnPrevious.visibility=View.VISIBLE
            btnNext.visibility=View.VISIBLE
        }

        val question=questions!!["question$index"]


        question?.let {
            val description=findViewById<TextView>(R.id.description)
            description.text=it.description
            val optionAdapter=OptionAdapter(this,it)
            val optionList=findViewById<RecyclerView>(R.id.optionList)
            optionList.layoutManager=LinearLayoutManager(this)
            optionList.adapter=optionAdapter
            optionList.setHasFixedSize(true)
        }

    }
}