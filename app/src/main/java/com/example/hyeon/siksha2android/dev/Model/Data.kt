package com.example.hyeon.siksha2android.dev.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Data : RealmObject() {
    @SerializedName("today")
    @Expose
    var today: Day? = null
    @SerializedName("tomorrow")
    @Expose
    var tomorrow: Day? = null
}