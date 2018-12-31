package com.example.hyeon.siksha2android.dev.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Meal : RealmObject() {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("en_name")
    @Expose
    var enName: String = ""
    @SerializedName("kr_name")
    @Expose
    var krName: String = ""
    @SerializedName("restaurant")
    @Expose
    var restaurant: Int? = null
    @SerializedName("score")
    @Expose
    var score: Double? = null
    @SerializedName("score_count")
    @Expose
    var scoreCount: Int? = null
    @SerializedName("price")
    @Expose
    lateinit var price: String
    @SerializedName("etc")
    @Expose
    lateinit var etc: String
}