package com.alvayonara.openweatherapps.core.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Helper {

    /**
     * @param outputFormat format dateTIme for result
     * @return return new date time format based on outputFormat
     *
     * @see DateFormat for supported DateFormat
     */
    @SuppressLint("SimpleDateFormat")
    fun Long?.dateTimeConvert(outputFormat: String): String {
        return try {
            val sdf = SimpleDateFormat(outputFormat, Locale.getDefault())
            val calendar = Calendar.getInstance().apply {
                timeInMillis = (this@dateTimeConvert!! * 1000)
            }
            sdf.format(calendar.time)
        } catch (e: ParseException) {
            ""
        }
    }

    fun Double?.convertToCelsius(): String {
        return try {
            ((this!!.minus(273) * 10.0).roundToInt() / 10.0).toString()
        } catch (e: NumberFormatException) {
            ""
        }
    }
}