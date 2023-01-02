package com.rzl.flightgotiketbooking.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.databinding.FragmentHistoryBinding
import com.rzl.flightgotiketbooking.ui.component.SpacerHeight
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.utils.GreenCard
import com.rzl.flightgotiketbooking.utils.OrangeFlight
import com.rzl.flightgotiketbooking.utils.caption
import com.rzl.flightgotiketbooking.utils.largeTitleSemiBold

class HistoryFragment : Fragment() {
    private var binding: FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding?.composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HistoryScreen()
            }
        }
        return binding?.root
    }
}

@Composable
fun HistoryScreen() {
    Scaffold {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(it)
        ) {
            items(10) {
                Card(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Kode Transaksi : 00$it", style = caption.copy(
                                        fontSize = 12.sp
                                    )
                                )
                            }

                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.garuda),
                                    contentDescription = "",
                                    Modifier
                                        .width(50.dp)
                                        .height(50.dp),
                                    contentScale = ContentScale.Fit
                                )
                                SpacerWidth(width = 8.dp)
                                Text(
                                    text = "Jakarta", style = largeTitleSemiBold.copy(
                                        fontSize = 10.sp,
                                    )
                                )
                                Icon(Icons.Filled.ArrowRightAlt, contentDescription = "")
                                Text(
                                    text = "Semarang", style = largeTitleSemiBold.copy(
                                        fontSize = 10.sp,
                                    )
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = { }, colors = ButtonDefaults.buttonColors(
                                        backgroundColor = GreenCard
                                    ), shape = CircleShape, modifier = Modifier.height(35.dp)
                                ) {
                                    Text(
                                        text = "Economy", style = caption.copy(
                                            color = Color.White, fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .background(color = OrangeFlight)
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "Total:", style = caption.copy(
                                    fontSize = 10.sp, color = Color.White
                                )
                            )
                            SpacerHeight(height = 8.dp)
                            Text(
                                text = "Rp 700.000", style = caption.copy(
                                    fontSize = 12.sp, color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
    MaterialTheme {
        HistoryScreen()
    }
}