package com.example.hyeon.siksha2android.dev.Dialog

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.dev.Model.Meal
import com.example.hyeon.siksha2android.dev.Model.Restaurant
import kotlinx.android.synthetic.main.evaluate_dial.*

class EvalDial(context: Context?, val meal: Meal, val res:Restaurant) : Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evaluate_dial)
        val name = findViewById<TextView>(R.id.tv_rtname)
        name.setText(res.krName)
        tv_menuname.text = meal.krName
        if(meal.score != null)  score.text = meal.score.toString()
        tv_count.text = meal.scoreCount.toString()
        var window = this.window
        window.setGravity(Gravity.BOTTOM)
        val params = window.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.horizontalMargin = 0f
        params.verticalMargin = 0f
        window.attributes = params as android.view.WindowManager.LayoutParams
    }
}