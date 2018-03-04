package com.tasktoys_rt.myfirebasepractice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class MainFirebaseImageLoadActivity : AppCompatActivity() {

    private lateinit var fileNameList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_firebase_image_load)

        val mGridView = findViewById<GridView>(R.id.gridView)
        val fileNameList = ArrayList<String>()
        for(i in 0..10){
            fileNameList.add("a"+i.toString()+".png")
        }

        mGridView.adapter = FireBaseStrageImageAdapter(this, fileNameList)
    }
}
