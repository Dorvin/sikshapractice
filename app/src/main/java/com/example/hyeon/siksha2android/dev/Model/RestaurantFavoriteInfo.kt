package com.example.hyeon.siksha2android.dev.Model

import io.realm.RealmObject

open class RestaurantFavoriteInfo: RealmObject() {
    var id:Int = -1
    var priority:Int = -1
    var favorite:Boolean = false
}