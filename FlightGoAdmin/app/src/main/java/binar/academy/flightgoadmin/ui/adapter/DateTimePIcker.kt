package binar.academy.flightgoadmin.ui.adapter

import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

object DateTimePIcker {
    @RequiresApi(Build.VERSION_CODES.O)
    fun datePick(manager : FragmentManager,value : EditText){
        val myFormat = "yyyy-MM-dd"
        val formattedDate = "2000-01-01"

        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        val date = sdf.parse(formattedDate)
        val timeInMillis = date?.time

        val constraintBuilder = CalendarConstraints.Builder().setOpenAt(
            timeInMillis!! //pass time in milli seconds
        ).build()


        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setCalendarConstraints(constraintBuilder)
            .build()

        picker.show(manager, "Date Picker")

        picker.addOnPositiveButtonClickListener {
            val setDate = Date(it)
            value.setText(sdf.format(setDate))// date selected by the user
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timePick(manager: FragmentManager, value: EditText){
        val formatTime = "24:00"
        val formatter = ofPattern("HH:mm")
            .withZone(ZoneId.of(TimeZone.getDefault().id))
        val time = formatter.parse(formatTime)

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Select Appointment time")
                .build()

        picker.show(manager, "Time Picker")

        picker.addOnPositiveButtonClickListener {
            val newHour: Int = picker.hour
            val newMinute: Int = picker.minute
            value.setText(buildString {
                append(newHour)
                append(".")
                append(newMinute)
            })
        }
    }

}