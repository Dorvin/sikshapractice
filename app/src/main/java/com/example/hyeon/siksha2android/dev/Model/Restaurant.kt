package com.example.hyeon.siksha2android.dev.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Restaurant : RealmObject() {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("en_name")
    @Expose
    var enName: String = ""
    @SerializedName("kr_name")
    @Expose
    var krName: String = ""
    @SerializedName("operating_hours")
    @Expose
    var operatingHours: String = ""
    @SerializedName("hours_breakfast")
    @Expose
    var hoursBreakfast: String = ""
    @SerializedName("hours_lunch")
    @Expose
    var hoursLunch: String = ""
    @SerializedName("hours_dinner")
    @Expose
    var hoursDinner: String = ""
    @SerializedName("location")
    @Expose
    var location: String = ""
    @SerializedName("latitude")
    @Expose
    var latitude: String = ""
    @SerializedName("longitude")
    @Expose
    var longitude: String = ""
}
