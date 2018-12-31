package com.example.hyeon.siksha2android.dev.Model

import io.realm.RealmList
import io.realm.RealmObject

open class Extra : RealmObject() {
    var today = ""
    var tomorrow = ""
    var restaurants:RealmList<RestaurantFavoriteInfo>? = null
}