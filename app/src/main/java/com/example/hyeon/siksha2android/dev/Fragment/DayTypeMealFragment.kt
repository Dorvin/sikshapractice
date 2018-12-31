package com.example.hyeon.siksha2android.dev.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.dev.Adapter.ContentAdapter
import com.example.hyeon.siksha2android.dev.Dialog.EvalDial
import com.example.hyeon.siksha2android.dev.Dialog.RestInfoDial
import com.example.hyeon.siksha2android.dev.Interface.ShowInfo
import com.example.hyeon.siksha2android.dev.Model.*
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_breakfast.*
import java.io.Serializable
import java.util.ArrayList


class DayTypeMealFragment : Fragment(), ShowInfo {
    override fun showScore(restaurant: Restaurant, meal: Meal) {
        var dial = EvalDial(mContext, meal, restaurant)
        dial.show()
    }

    override fun showDial(res: Restaurant) {
        //Toast.makeText(mContext, res.krName, Toast.LENGTH_SHORT).show()
        var dial = RestInfoDial(mContext, res, R.style.DialogTheme)
        dial.show()
    }


    private var ca: ContentAdapter? = null
    private var rv: RecyclerView? = null
    private var day: String? = null
    private var type: String? = null
    private var favorite: Boolean? = null
    private var mContext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            day = arguments!!.getString("day")
            type = arguments!!.getString("type")
            favorite = arguments!!.getBoolean("favorite")
            mContext = arguments!!.getSerializable("con") as Context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val baseview = inflater.inflate(R.layout.fragment_breakfast, container, false)
        return baseview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ca = ContentAdapter(activity as Context, ArrayList<Menu>(), this)
        rv = rv_main
        rv!!.layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
        rv!!.adapter = ca
        val realm = Realm.getDefaultInstance()
        val datas = realm.where(Data::class.java).findAll()
        Log.d("forcheck", day)
        var data = Data()
        if (datas.size > 0) data = datas.get(0)
        val today = data.today
        val tomorrow = data.tomorrow
        val today_menus = today!!.menus
        val tomorrow_menus = tomorrow!!.menus
        val themenus = ArrayList<Menu>()
        if(favorite!!){
            val extraRestInfosBefore = realm.where(Extra::class.java).findAll()
            var extra = Extra()
            if(extraRestInfosBefore.size > 0)   extra = extraRestInfosBefore.get(0)
            var extraRestInfo = extra.restaurants
            if (day == "today") {
                for (i in today_menus!!.indices) {
                    val thismenu = today_menus.get(i)
                    val targetId = thismenu.restaurant!!.id
                    if(!extraRestInfo!!.get(targetId!!).favorite)   continue
                    if (thismenu.type.equals(type)) {
                        themenus.add(thismenu)
                    }
                }
            } else if (day == "tomorrow") {
                for (i in tomorrow_menus!!.indices) {
                    val thismenu = tomorrow_menus.get(i)
                    val targetId = thismenu.restaurant!!.id
                    if(!extraRestInfo!!.get(targetId!!).favorite)   continue
                    if (thismenu.type.equals(type)) {
                        themenus.add(thismenu)
                    }
                }
            }
        }
        else {
            if (day == "today") {
                for (i in today_menus!!.indices) {
                    val thismenu = today_menus.get(i)
                    if (thismenu.type.equals(type)) {
                        themenus.add(thismenu)
                    }
                }
            } else if (day == "tomorrow") {
                for (i in tomorrow_menus!!.indices) {
                    val thismenu = tomorrow_menus!!.get(i)
                    if (thismenu.type.equals(type)) {
                        themenus.add(thismenu)
                    }
                }
            }
        }
        //sort themenus here
        val newTheMenus = themenus.sortedWith(CompareObjects)
        ca!!.update(newTheMenus)
    }

    companion object {
        fun newInstance(day: String, type: String, favorite: Boolean, c: Serializable): DayTypeMealFragment {
            val dtm = DayTypeMealFragment()
            val args = Bundle()
            args.putString("day", day)
            args.putString("type", type)
            args.putBoolean("favorite", favorite)
            args.putSerializable("con", c)
            dtm.arguments = args
            return dtm
        }
    }
}

class CompareObjects {

    companion object : Comparator<Menu> {

        override fun compare(a: Menu, b: Menu): Int{
            val realm = Realm.getDefaultInstance()
            val extraInfoBefore = realm.where(Extra::class.java).findAll()
            val extraInfo = extraInfoBefore.get(0).restaurants
            Log.d("reamIndex", ""+extraInfo)
            val aP = extraInfo!!.get(a.restaurant!!.id!!).priority
            val bP = extraInfo!!.get(b.restaurant!!.id!!).priority
            //return a.restaurant!!.id!! - b.restaurant!!.id!!
            return aP - bP
        }
    }
}
