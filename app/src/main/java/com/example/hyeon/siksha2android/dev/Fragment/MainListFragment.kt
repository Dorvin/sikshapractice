package com.example.hyeon.siksha2android.dev.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.dev.Adapter.VPAdapter
import com.example.hyeon.siksha2android.dev.Model.Data
import com.example.hyeon.siksha2android.dev.Model.Extra
import com.example.hyeon.siksha2android.dev.Model.RestaurantFavoriteInfo
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObjectChangeListener
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_main_list.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MainListFragment : Fragment() {
    lateinit var myadapt: VPAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_main_list, container, false)
        val realm = Realm.getDefaultInstance()
        val datas = realm.where(Data::class.java).findAll()
        val extrainfos = realm.where(Extra::class.java).findAll()
        extrainfos.addChangeListener(RealmObjectChangeListener<RealmResults<Extra>> { t, changeSet -> })
        val temp = Extra()
        temp.today = "errortoday"
        temp.tomorrow = "errortomorrow"
        var extrainfo = temp
        when {
            extrainfos.size > 0 -> extrainfo = extrainfos.get(0)
        }
        val temp2 = rootview.findViewById(R.id.cb_tomorrow) as Button
        val temp1 = rootview.findViewById(R.id.cb_today) as Button
        temp1.text = extrainfo.today
        temp2.text = extrainfo.tomorrow
        val vp = rootview.findViewById(R.id.view_pager) as ViewPager
        val mcontext = context as Serializable
        val frags = arrayOf<Fragment>(DayTypeMealFragment.newInstance("today", "BR", false, mcontext),
                DayTypeMealFragment.newInstance("today", "LU", false, mcontext),
                DayTypeMealFragment.newInstance("today", "DN", false, mcontext),
                DayTypeMealFragment.newInstance("tomorrow", "BR", false, mcontext),
                DayTypeMealFragment.newInstance("tomorrow", "LU", false, mcontext),
                DayTypeMealFragment.newInstance("tomorrow", "DN", false, mcontext))
        myadapt = VPAdapter(childFragmentManager, frags)
        vp.adapter = myadapt
        val cb_today = rootview.findViewById<CheckBox>(R.id.cb_today)
        val cb_tomorrow = rootview.findViewById<CheckBox>(R.id.cb_tomorrow)
        val cb_break = rootview.findViewById<CheckBox>(R.id.cb_break)
        val cb_lunch = rootview.findViewById<CheckBox>(R.id.cb_lunch)
        val cb_dinner = rootview.findViewById<CheckBox>(R.id.cb_dinner)
        cb_today.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cb_tomorrow.isChecked = false
                cb_today.isChecked = true
                cb_break.isChecked = false
                cb_lunch.isChecked = true
                cb_dinner.isChecked = false
                vp.setCurrentItem(1)
            } else {
                if (!cb_tomorrow.isChecked) cb_today.isChecked = true
            }
        }
        cb_tomorrow.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cb_tomorrow.isChecked = true
                cb_today.isChecked = false
                cb_break.isChecked = false
                cb_lunch.isChecked = true
                cb_dinner.isChecked = false
                vp.setCurrentItem(4)
            } else {
                if (!cb_today.isChecked) cb_tomorrow.isChecked = true
            }
        }
        cb_break.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cb_break.isChecked = true
                cb_lunch.isChecked = false
                cb_dinner.isChecked = false
                if (cb_today.isChecked) vp.setCurrentItem(0)
                else vp.setCurrentItem(3)
            } else {
                if (!(cb_lunch.isChecked || cb_dinner.isChecked)) cb_break.isChecked = true
            }
        }
        cb_lunch.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cb_break.isChecked = false
                cb_lunch.isChecked = true
                cb_dinner.isChecked = false
                if (cb_today.isChecked) vp.setCurrentItem(1)
                else vp.setCurrentItem(4)
            } else {
                if (!(cb_break.isChecked || cb_dinner.isChecked)) cb_lunch.isChecked = true
            }
        }
        cb_dinner.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cb_break.isChecked = false
                cb_lunch.isChecked = false
                cb_dinner.isChecked = true
                if (cb_today.isChecked) vp.setCurrentItem(2)
                else vp.setCurrentItem(5)
            } else {
                if (!(cb_break.isChecked || cb_lunch.isChecked)) cb_dinner.isChecked = true
            }
        }
        cb_today.isChecked = true
        cb_lunch.isChecked = true
        vp.setCurrentItem(1)
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                val frags2 = arrayOf<Fragment>(DayTypeMealFragment.newInstance("today", "BR", false, mcontext),
                        DayTypeMealFragment.newInstance("today", "LU", false, mcontext),
                        DayTypeMealFragment.newInstance("today", "DN", false, mcontext),
                        DayTypeMealFragment.newInstance("tomorrow", "BR", false, mcontext),
                        DayTypeMealFragment.newInstance("tomorrow", "LU", false, mcontext),
                        DayTypeMealFragment.newInstance("tomorrow", "DN", false, mcontext))
                myadapt.setFrags(frags2)
                myadapt.notifyDataSetChanged()
                when (position) {
                    0 -> {
                        cb_today.isChecked = true
                        cb_tomorrow.isChecked = false
                        cb_break.isChecked = true
                        cb_lunch.isChecked = false
                        cb_dinner.isChecked = false
                    }
                    1 -> {
                        cb_today.isChecked = true
                        cb_tomorrow.isChecked = false
                        cb_break.isChecked = false
                        cb_lunch.isChecked = true
                        cb_dinner.isChecked = false
                    }
                    2 -> {
                        cb_today.isChecked = true
                        cb_tomorrow.isChecked = false
                        cb_break.isChecked = false
                        cb_lunch.isChecked = false
                        cb_dinner.isChecked = true
                    }
                    3 -> {
                        cb_today.isChecked = false
                        cb_tomorrow.isChecked = true
                        cb_break.isChecked = true
                        cb_lunch.isChecked = false
                        cb_dinner.isChecked = false
                    }
                    4 -> {
                        cb_today.isChecked = false
                        cb_tomorrow.isChecked = true
                        cb_break.isChecked = false
                        cb_lunch.isChecked = true
                        cb_dinner.isChecked = false
                    }
                    5 -> {
                        cb_today.isChecked = false
                        cb_tomorrow.isChecked = true
                        cb_break.isChecked = false
                        cb_lunch.isChecked = false
                        cb_dinner.isChecked = true
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        return rootview
    }
}
