package com.rzl.flightgotiketbooking.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.utils.caption
import com.rzl.flightgotiketbooking.utils.largeTitleSemiBold

class WishListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WishListScreen()
            }
        }
    }
}

@Composable
fun WishListScreen() {
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
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
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
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Rp 1.200.000", style = caption.copy(
                                    fontSize = 18.sp, color = colorResource(id = R.color.orange)
                                )
                            )
                            SpacerWidth(width = 8.dp)
                            Text(text = "Economy", style = caption)
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { }) {
                                Icon(Icons.Filled.Delete, contentDescription = "")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPrev() {
    MaterialTheme {
        WishListScreen()
    }
}