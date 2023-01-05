package binar.academy.flightgoadmin.ui.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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

@Composable
fun MyTimePickerDialog(listener: (Int, Int, Int) -> Unit): TimePickerDialog {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMonth = mCalendar.get(Calendar.MINUTE)

    return TimePickerDialog(
        mContext, { a, b, c ->
            listener(b, c, 0)
        }, mYear, mMonth, true
    )
}