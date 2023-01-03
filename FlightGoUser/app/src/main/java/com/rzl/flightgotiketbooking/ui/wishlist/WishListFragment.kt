package com.rzl.flightgotiketbooking.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.ResponseListWish
import com.rzl.flightgotiketbooking.ui.component.CenterProgressBar
import com.rzl.flightgotiketbooking.ui.component.ErrorMessage
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
fun WishListScreen(viewModel: WishListViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getListWish()
    }

    val data = remember {
        mutableStateListOf<ResponseListWish.Data>()
    }

    viewModel.stateList.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                CenterProgressBar()
            }
            is UiState.Success -> {
                data.addAll(it.data.data)
            }
            is UiState.Error -> {
                ErrorMessage(msg = it.errorMessage)
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Wishlist", style = largeTitle.copy(
                        color = Color.Black, fontSize = 19.sp
                    )
                )
            }
        }, backgroundColor = colorResource(id = R.color.white))
    }) { pv: PaddingValues ->
        if (data.size <= 0) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = "Anda belum memiliki Wishlist",
                    style = caption,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(pv)
            ) {
                items(data, key = { d -> d.id }) { d ->
                    ItemWishList(d, delete = {
                        data.remove(d)
                    })
                }
            }
        }
    }
}

@Composable
fun ItemWishList(
    data: ResponseListWish.Data, delete: () -> Unit = {}
) {
    Card(
        elevation = 5.dp, shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = data.product.imageProduct),
                    contentDescription = "",
                    Modifier
                        .width(50.dp)
                        .height(50.dp),
                    contentScale = ContentScale.Fit
                )
                SpacerWidth(width = 8.dp)
                Text(
                    text = data.product.kotaAsal, style = largeTitleSemiBold.copy(
                        fontSize = 10.sp,
                    )
                )
                Icon(Icons.Filled.ArrowRightAlt, contentDescription = "")
                Text(
                    text = data.product.kotaTujuan, style = largeTitleSemiBold.copy(
                        fontSize = 10.sp,
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = data.product.price.toString().toCurrencyFormat(), style = caption.copy(
                        fontSize = 18.sp, color = colorResource(id = R.color.orange)
                    )
                )
                SpacerWidth(width = 8.dp)
                Text(text = "Economy", style = caption)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = delete, Modifier.align(Alignment.Bottom)) {
                    Icon(
                        Icons.Filled.Delete, contentDescription = "", tint = GreyFlight
                    )
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