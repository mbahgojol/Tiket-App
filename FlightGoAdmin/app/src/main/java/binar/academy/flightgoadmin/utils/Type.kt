package binar.academy.flightgoadmin.utils

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import binar.academy.flightgoadmin.R

val poppinsFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_regular, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = poppinsFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val largeTitle = TextStyle(
    fontWeight = FontWeight.Medium, fontSize = 24.sp, fontFamily = poppinsFamily
)

val largeTitleBold = TextStyle(
    fontWeight = FontWeight.Bold, fontSize = 24.sp, fontFamily = poppinsFamily
)

val caption = TextStyle(
    fontFamily = poppinsFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp
)

val largeTitleSemiBold = TextStyle(
    fontWeight = FontWeight.SemiBold, fontSize = 18.sp, fontFamily = poppinsFamily
)