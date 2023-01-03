package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.utils.caption
import binar.academy.flightgoadmin.utils.largeTitle
import coil.compose.rememberAsyncImagePainter

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ItemCardTicket()
}

@Composable
fun ItemCardTicket(
    modifier: Modifier = Modifier,
    model: TiketResponseItem? = null,
) {
    Card(
        shape = RoundedCornerShape(
            30.dp
        ), modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TheRoute(
                    city = model?.kotaAsal.toString(), code = model?.kodeNegaraAsal.toString()
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = rememberAsyncImagePainter(model = model?.imageProduct),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                TheRoute(
                    city = model?.kotaTujuan.toString(),
                    code = model?.kodeNegaraTujuan.toString(),
                    horizontalAlignment = Alignment.End
                )
            }
            SpacerHeight(height = 10.dp)
            Row(modifier = Modifier.fillMaxWidth()) {
                DoubleDescription(
                    label = "Depart", value = model?.depatureTime.plus(" PM")
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                DoubleDescription(
                    label = "Flight Type",
                    value = model?.jenisPenerbangan.toString(),
                    horizontalAlignment = Alignment.End
                )
            }
            SpacerHeight(height = 8.dp)
            FlightLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            SpacerHeight(height = 16.dp)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Rp 1.200.000", style = caption.copy(
                        fontSize = 18.sp, color = colorResource(id = R.color.orange)
                    )
                )
                SpacerWidth(width = 8.dp)
                Text(text = "Economy", style = caption)
            }
        }
    }
}

@Composable
fun DoubleDescription(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier.wrapContentSize(), horizontalAlignment = horizontalAlignment
    ) {
        Text(text = label, style = caption)
        Text(
            text = value, style = caption.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun TheRoute(
    modifier: Modifier = Modifier,
    city: String = "Jakarta",
    code: String = "JKT",
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier.wrapContentSize(), horizontalAlignment = horizontalAlignment
    ) {
        Text(text = city, style = caption)
        Text(text = code, style = largeTitle)
    }
}