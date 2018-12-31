package com.example.hyeon.siksha2android.dev.Dialog

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.dev.Model.Restaurant



class RestInfoDial(context: Context?, val res: Restaurant, dialogTheme: Int) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information)
        val location = findViewById<TextView>(R.id.location)
        val time = findViewById<TextView>(R.id.time)
        val name = findViewById<TextView>(R.id.tv_rtname)
        val timeres = (if(!res.hoursBreakfast.equals("정보 없음"))(res.hoursBreakfast.replace("-", "~") +"\n") else "")+
                (if(!res.hoursLunch.equals("정보 없음"))(res.hoursLunch.replace("-", "~") +"\n") else "")+
                (if(!res.hoursDinner.equals("정보 없음"))res.hoursDinner.replace("-", "~") else "")
        name.setText(res.krName)
        location.setText(res.location)
        //time.setText(res.operatingHours + "\n")
        time.setText(timeres)
        var window = this.window
        window.setGravity(Gravity.BOTTOM)
        val params = window.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.horizontalMargin = 0f
        params.verticalMargin = 0f
        window.attributes = params as android.view.WindowManager.LayoutParams
    }
}