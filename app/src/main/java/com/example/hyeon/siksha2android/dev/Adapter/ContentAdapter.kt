package com.example.hyeon.siksha2android.dev.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.hyeon.siksha2android.dev.Interface.ShowInfo
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.dev.Model.Extra
import com.example.hyeon.siksha2android.dev.Util.IsOpen
import com.example.hyeon.siksha2android.dev.ViewHolder.ContentViewHolder
import com.example.hyeon.siksha2android.dev.Model.Menu
import com.example.hyeon.siksha2android.dev.Model.Restaurant
import io.realm.Realm
import kotlinx.android.synthetic.main.menu.view.*

class ContentAdapter(var mcontext: Context, var content_list: List<Menu>, var func: ShowInfo) : RecyclerView.Adapter<ContentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val baseview = View.inflate(mcontext, R.layout.contentlayout, null)
        return ContentViewHolder(baseview)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val menu = content_list[position]
        val realm = Realm.getDefaultInstance()
        val extraRestaurantInformation = realm.where(Extra::class.java).findAll().get(0).restaurants
        holder.setFavor.isChecked = extraRestaurantInformation!!.get(menu.restaurant!!.id!!).favorite
        holder.restaurant_name.setText(menu.restaurant!!.krName)
        holder.info.setOnClickListener( View.OnClickListener {
            func.showDial(menu.restaurant as Restaurant)
        })
        holder.setFavor.setOnClickListener {
            val cb = it as CheckBox
            if(!cb.isChecked){
                realm.executeTransaction { realm ->
                    val target = realm.where(Extra::class.java).findAll().get(0).restaurants!!.get(menu.restaurant!!.id!!)
                    if(target.favorite)target.favorite = false
                }
            }
            else{
                realm.executeTransaction { realm ->
                    val target = realm.where(Extra::class.java).findAll().get(0).restaurants!!.get(menu.restaurant!!.id!!)
                    if(!target.favorite)target.favorite = true
                }
            }
        }
        if(IsOpen.isopen(menu.restaurant!!.hoursBreakfast)||IsOpen.isopen(menu.restaurant!!.hoursLunch)||IsOpen.isopen(menu.restaurant!!.hoursDinner)){
            holder.open.setText("OPEN")
        }
        else holder.open.setText("CLOSE")
        val meals = menu.meals
        holder.menusholder.removeAllViews()
        when{
            meals != null -> {
                if(meals.size != 0) {
                    for (i in meals!!.indices) {
                        val meal = meals!!.get(i)
                        val v = View.inflate(mcontext, R.layout.menu, null)
                        val t1 = v.findViewById(R.id.tv_price) as TextView
                        val t2 = v.findViewById(R.id.tv_krname) as TextView
                        t1.setText(meal.price)
                        t2.setText(meal.krName)
                        v.tv_krname.setOnClickListener {
                            func.showScore(menu.restaurant as Restaurant, meal)
                        }
                        holder.menusholder.addView(v)
                    }
                }
                else{
                    val t = View.inflate(mcontext, R.layout.nomeal, null)
                    t.findViewById<TextView>(R.id.tv_nomeal).setText("식단이 없습니다.")
                    holder.menusholder.addView(t)
                }
            }
            else ->{
                val t = TextView(mcontext)
                t.setText("식단이 없습니다.")
                holder.menusholder.addView(t)
            }
        }
    }

    override fun getItemCount(): Int {
        return content_list.size
    }

    fun update(breakfastmenus: List<Menu>) {
        content_list = breakfastmenus
        notifyDataSetChanged()
    }
}