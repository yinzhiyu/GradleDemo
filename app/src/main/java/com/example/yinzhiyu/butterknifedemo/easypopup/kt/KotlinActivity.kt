package com.zyyoona7.easypopup.kt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.yinzhiyu.butterknifedemo.R
import com.zyyoona7.lib.EasyPopup

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val easyPop: EasyPopup = EasyPopup(this).init {
            layoutId = R.layout.layout_right_pop
        }

        val customPop: CustomPopup = CustomPopup(this).init {

        }
    }
}
