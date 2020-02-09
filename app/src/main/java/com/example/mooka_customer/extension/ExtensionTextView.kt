package com.example.mooka_customer.extension

import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.mooka_customer.R

fun TextView.toSudahMembayar(){
    this.background = ContextCompat.getDrawable(this.context, R.drawable.background_green_line_soft)
    this.setTextColor(ContextCompat.getColor(this.context, R.color.green_500))
    this.text = "Sudah Terbayar"
}

fun TextView.toBelumMembayar(){
    this.background = ContextCompat.getDrawable(this.context, R.drawable.background_red_line_soft)
    this.setTextColor(ContextCompat.getColor(this.context, R.color.red_500))
    this.text = "Belum Terbayar"
}


fun String.boldFromHtml(): Spanned{
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}