package com.mahmood_imtiaz.todaysheadlines

object ColorPicker {
    val colors = arrayOf("#96D6FF", "#4DB8FF", "#0099FF", "#265C80", "#004D80", "#007ACC")
    var currentColor = 1
    fun getColor(): String {
        return colors[currentColor++ % colors.size]
    }
}