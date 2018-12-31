package com.example.hyeon.siksha2android.dev.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class Menu : RealmObject() {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("restaurant")
    @Expose
    var restaurant: Restaurant? = null
    @SerializedName("meals")
    @Expose
    var meals: RealmList<Meal>? = null
    @SerializedName("date")
    @Expose
    lateinit var date: String
    @SerializedName("type")
    @Expose
    lateinit var type: String
}
