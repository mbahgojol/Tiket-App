package com.rzl.flightgotiketbooking.ui.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun MyDatePickerDialog(listener: (Int, Int, Int) -> Unit): DatePickerDialog {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    return DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            listener(mYear, mMonth, mDayOfMonth)
        }, mYear, mMonth, mDay
    )
}