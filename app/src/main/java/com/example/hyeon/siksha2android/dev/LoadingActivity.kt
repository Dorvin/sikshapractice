package com.example.hyeon.siksha2android.dev

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hyeon.siksha2android.R
import com.example.hyeon.siksha2android.dev.Model.Data
import com.example.hyeon.siksha2android.dev.Model.Extra
import com.example.hyeon.siksha2android.dev.Model.RestaurantFavoriteInfo
import com.example.hyeon.siksha2android.dev.Retrofit.APIClient
import com.example.hyeon.siksha2android.dev.Retrofit.APIInterface
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Realm.init(this)
        val rec = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(rec)
        val realm = Realm.getDefaultInstance()
        val mcontext = this
        val sp = getSharedPreferences("check", Context.MODE_PRIVATE)
        val first = sp.getBoolean("isitfirst", true)
        Log.d("isitfirst", ""+first);
        var realmdata : RealmResults<Data>

        val mSimpleDateFormat = SimpleDateFormat("MM.", Locale.KOREA)
        val mSimpleDateFormat2 = SimpleDateFormat("dd. ", Locale.KOREA)
        val currentTime = Date()
        val tdmonth = mSimpleDateFormat.format(currentTime)
        val todaystring = mSimpleDateFormat2.format(currentTime)

        val cal = GregorianCalendar(Locale.KOREA)
        cal.time = currentTime
        cal.add(Calendar.DAY_OF_YEAR, 1)
        val tmmonth = mSimpleDateFormat.format(cal.time)
        val tomorrowString = mSimpleDateFormat2.format(cal.time)
        var tods = tdmonth + todaystring
        var toms = tmmonth + tomorrowString
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        when (day) {
            Calendar.SUNDAY -> {
                tods += "일"
                toms += "월"
            }
            Calendar.MONDAY -> {
                tods += "월"
                toms += "화"
            }
            Calendar.TUESDAY -> {
                tods += "화"
                toms += "수"
            }
            Calendar.WEDNESDAY -> {
                tods += "수"
                toms += "목"
            }
            Calendar.THURSDAY -> {
                tods += "목"
                toms += "금"
            }
            Calendar.FRIDAY -> {
                tods += "금"
                toms += "토"
            }
            Calendar.SATURDAY -> {
                tods += "토"
                toms += "일"
            }
        }
        //여기에 nullpointexception 방지 필요, 처음 부팅했을때 필요.
        if (first) {
            Log.d("isitfirst", "entered!")
            val apiInterface = APIClient.client!!.create(APIInterface::class.java)
            val call = apiInterface.data
            call.enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    val data = response.body()
                    sp.edit().putBoolean("isitfirst", false).commit()
                    val extra = Extra()
                    extra.today = tods
                    extra.tomorrow = toms
                    val restinfos = RealmList<RestaurantFavoriteInfo>()
                    for(i in 0..50){
                        var tempRest = RestaurantFavoriteInfo()
                        tempRest.favorite =false
                        tempRest.id=i
                        tempRest.priority=i
                        restinfos.add(tempRest)
                    }
                    extra.restaurants = restinfos
                    realm.executeTransaction { realm ->
                        realm.copyToRealm(data)
                        realm.copyToRealm(extra)
                    }
                    Toast.makeText(mcontext, "식단을 성공적으로 업데이트했습니다\n식샤2.0을 선택해주셔서 감사합니다!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(mcontext, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    overridePendingTransition(0, 0)
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.d("isitfirst", "initialfail")
                    Toast.makeText(mcontext, "식단을 받아오지 못해 종료합니다", Toast.LENGTH_LONG).show()
                    finish()
                    overridePendingTransition(0, 0)
                }
            })
        } else {
            realmdata = realm.where(Data::class.java).findAll()
            val maindata = realmdata.get(0)
            val newSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val ccurrentTime = Date()
            val mTime = newSimpleDateFormat.format(ccurrentTime)
            val dbTime = maindata.today!!.date
            if (mTime == dbTime) {
                Toast.makeText(mcontext, "식단이 최신 상태입니다", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
                overridePendingTransition(0, 0)
            } else {
                val apiInterface = APIClient.client!!.create(APIInterface::class.java)
                val call = apiInterface.data
                call.enqueue(object : Callback<Data> {
                    override fun onResponse(call: Call<Data>, response: Response<Data>) {
                        val data = response.body()
                        val extra = Extra()
                        extra.today = tods
                        extra.tomorrow = toms
                        realm.executeTransaction { realm ->
                            realm.where(Data::class.java).findAll().deleteAllFromRealm()
                            realm.copyToRealm(data)
                            val temporayRealm = realm.where(Extra::class.java).findAll().get(0)
                            temporayRealm.today = tods
                            temporayRealm.tomorrow = toms
                            /*val tempretInfo = realm.where(Extra::class.java).findAll().get(0).restaurants
                            extra.restaurants = tempretInfo
                            realm.where(Extra::class.java).findAll().deleteAllFromRealm()
                            realm.copyToRealm(extra)*/
                        }
                        Toast.makeText(mcontext, "식단을 성공적으로 업데이트했습니다", Toast.LENGTH_SHORT).show()
                        val intent = Intent(mcontext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                        overridePendingTransition(0, 0)
                    }

                    override fun onFailure(call: Call<Data>, t: Throwable) {
                        Toast.makeText(mcontext, "식단을 업데이트하지 못했습니다", Toast.LENGTH_LONG).show()
                        val intent = Intent(mcontext, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                        overridePendingTransition(0, 0)
                    }
                })
            }
        }
    }
}
