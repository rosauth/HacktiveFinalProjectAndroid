package com.bcaf.ivan.finalprojectandroid.ViewHolder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.R

open class BusListViewHolder : RecyclerView.ViewHolder {
    var busCode: TextView
    var busMake: TextView
    var busCapacity: TextView
    var busCard:CardView
    var btn_expand: AppCompatToggleButton
    var lyt_detail: LinearLayout

    constructor(v: View) : super(v) {
        busCode = v.findViewById(R.id.txt_busCode)
        busMake = v.findViewById(R.id.txt_busMake)
        busCapacity = v.findViewById(R.id.txt_busCapacity)
        busCard = v.findViewById(R.id.card_bus)
        lyt_detail = v.findViewById(R.id.lyt_detail)
        btn_expand = v.findViewById(R.id.btn_expand)
    }
}