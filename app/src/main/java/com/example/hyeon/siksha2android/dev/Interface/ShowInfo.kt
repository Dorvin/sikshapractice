package com.example.hyeon.siksha2android.dev.Interface

import com.example.hyeon.siksha2android.dev.Model.Meal
import com.example.hyeon.siksha2android.dev.Model.Restaurant

interface ShowInfo {
    fun showDial(res: Restaurant)
    fun showScore(restaurant: Restaurant, meal: Meal)
}