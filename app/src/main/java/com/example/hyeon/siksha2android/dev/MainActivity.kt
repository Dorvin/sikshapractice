package com.example.hyeon.siksha2android.dev

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.CheckBox
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.R.drawable.radio_main
import com.example.hyeon.siksha2android.dev.Fragment.FavorListFragment
import com.example.hyeon.siksha2android.dev.Fragment.MainListFragment
import com.example.hyeon.siksha2android.dev.Fragment.SetFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity(), Serializable {

    lateinit var fm: FragmentManager
    lateinit var tran: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rb_main.isChecked = true
        rb_setting.isChecked = false
        rb_star.isChecked = false
        fm = supportFragmentManager
        rb_main.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                rb_main.isChecked = true
                rb_setting.isChecked = false
                rb_star.isChecked = false
                setFrag(1)
            }
            else{
                if(!rb_star.isChecked && !rb_setting.isChecked){
                    rb_main.isChecked = true
                }
            }
        }
        rb_setting.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                rb_main.isChecked = false
                rb_setting.isChecked = true
                rb_star.isChecked = false
                setFrag(2)
            }
            else{
                if(!rb_star.isChecked && !rb_main.isChecked){
                    rb_setting.isChecked = true
                }
            }
        }
        rb_star.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                rb_main.isChecked = false
                rb_setting.isChecked = false
                rb_star.isChecked = true
                setFrag(0)
            }
            else{
                if(!rb_main.isChecked && !rb_setting.isChecked){
                    rb_star.isChecked = true
                }
            }
        }
    }
    fun setFrag(n:Int){
        tran = fm.beginTransaction()
        when(n){
            0->{
                tran.replace(fragment.id, FavorListFragment())
                tran.commit()
            }
            1->{
                tran.replace(fragment.id, MainListFragment())
                tran.commit()
            }
            2->{
                tran.replace(fragment.id, SetFragment())
                tran.commit()
            }
        }
    }
}
