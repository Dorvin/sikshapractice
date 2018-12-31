

package com.example.hyeon.siksha2android.dev.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hyeon.siksha2android.R

class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var restaurant_name: TextView
    var menusholder: LinearLayout
    var info: ImageView
    var open: TextView
    var setFavor: CheckBox

    init {
        restaurant_name = itemView.findViewById(R.id.tv_rtname) as TextView
        menusholder = itemView.findViewById(R.id.ll_menus) as LinearLayout
        info = itemView.findViewById(R.id.info_btn) as ImageView
        open = itemView.findViewById(R.id.tv_openinfo) as TextView
        setFavor = itemView.findViewById(R.id.imageView6) as CheckBox
    }
}
