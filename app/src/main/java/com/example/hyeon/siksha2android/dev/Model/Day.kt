package com.example.hyeon.siksha2android.dev.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class Day : RealmObject() {
    @SerializedName("date")
    @Expose
    var date: String = ""
    @SerializedName("menus")
    @Expose
    var menus: RealmList<Menu>? = null
}