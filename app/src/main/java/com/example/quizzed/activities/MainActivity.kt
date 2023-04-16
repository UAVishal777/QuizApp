package com.example.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzed.R
import com.example.quizzed.adapters.QuizAdapter
import com.example.quizzed.models.Quiz
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    lateinit var adapter: QuizAdapter
    private var quizList= mutableListOf<Quiz>()

    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //populateDummyData()
        setUpView()
    }

    private fun populateDummyData() {
        quizList.add(Quiz("12-10-2022","12-10-2022"))
        quizList.add(Quiz("15-10-2022","15-10-2022"))
        quizList.add(Quiz("17-10-2022","17-10-2022"))
        quizList.add(Quiz("19-10-2022","19-10-2022"))
        quizList.add(Quiz("20-10-2022","22-10-2022"))
        quizList.add(Quiz("22-10-2022","24-10-2022"))
        quizList.add(Quiz("19-10-2022","19-10-2022"))
        quizList.add(Quiz("20-10-2022","22-10-2022"))
        quizList.add(Quiz("22-10-2022","24-10-2022"))
        quizList.add(Quiz("19-10-2022","19-10-2022"))
        quizList.add(Quiz("20-10-2022","22-10-2022"))
        quizList.add(Quiz("22-10-2022","24-10-2022"))
        quizList.add(Quiz("19-10-2022","19-10-2022"))
        quizList.add(Quiz("20-10-2022","22-10-2022"))
        quizList.add(Quiz("22-10-2022","24-10-2022"))
    }

    fun setUpView(){
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        val btnDatePicker:FloatingActionButton=findViewById(R.id.btnDatePicker)

        btnDatePicker.setOnClickListener{
            val datePicker=MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnNegativeButtonClickListener() {
                Log.d("DatePicker",datePicker.headerText)

            }
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DatePicker",datePicker.headerText)
                val dateFormatter=SimpleDateFormat("dd-mm-yyyy")
                val date= dateFormatter.format(Date(it))
                val intent=Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnCancelListener {
                Log.d("DatePicker","Date Picker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        firestore=FirebaseFirestore.getInstance()
        val collectionReference:CollectionReference=firestore.collection("quizzes")
        collectionReference.addSnapshotListener{
            value,error->
            if(value==null || error!=null){
                Toast.makeText(this,"Error fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("Data",value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView(){
        val quizRecyclerView:RecyclerView=findViewById(R.id.quizRecyclerView)
        adapter= QuizAdapter(this,quizList)
        quizRecyclerView.layoutManager=GridLayoutManager(this,2)
        quizRecyclerView.adapter=adapter

    }

    fun setUpDrawerLayout(){
        val navigationView=findViewById<NavigationView>(R.id.navigationView)
        val appBar:MaterialToolbar=findViewById(R.id.appBar)
        val mainDrawer:DrawerLayout=findViewById(R.id.mainDrawer)
        setSupportActionBar(appBar)
        actionBarDrawerToggle= ActionBarDrawerToggle(this,mainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            val intent=Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            mainDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}