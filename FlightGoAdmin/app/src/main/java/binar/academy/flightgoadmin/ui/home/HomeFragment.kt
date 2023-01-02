package binar.academy.flightgoadmin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.ui.component.*
import binar.academy.flightgoadmin.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeScreen(clickCostumer = {
                    findNavController().navigate(R.id.action_homeFragment_to_costumerFragment)
                }, clickDetailTiket = {
                    val bundle = Bundle()
                    bundle.putSerializable("dataTiket", it)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_detailTiketFragment, bundle
                    )
                }, clickProfile = {
                    findNavController().navigate(R.id.profileFragment)
                }, clickAddTiket = {
                    findNavController().navigate(R.id.addTiketFragment)
                })
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    clickProfile: () -> Unit = {},
    clickAddTiket: () -> Unit = {},
    clickCostumer: () -> Unit = {},
    clickDetailTiket: (TiketResponseItem) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.getListFlight()
    }

    viewModel.uistateList.collectAsState().value.also {
        when (it) {
            is UiState.Loading -> {
                CenterProgressBar()
            }
            is UiState.Success -> {
                val response = it.data
                Scaffold(floatingActionButton = {
                    FloatingActionButton(
                        onClick = clickAddTiket, backgroundColor = GreyCard
                    ) {
                        Icon(
                            Icons.Filled.Add, contentDescription = "", tint = OrangeFlight
                        )
                    }
                }, topBar = {
                    TopAppBar(backgroundColor = Color.White, elevation = 0.dp) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Home", style = largeTitle.copy(
                                    color = Color.Black, fontSize = 19.sp
                                ), modifier = Modifier.align(Alignment.Center)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.img),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        clickProfile()
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }) { pv ->
                    Column(
                        Modifier
                            .padding(pv)
                            .fillMaxSize()
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            SpacerHeight(height = 16.dp)
                            Text(text = "Helo, Admin!", style = largeTitleBold)
                            SpacerHeight(height = 8.dp)
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(color = GreyBox)
                                    .padding(horizontal = 18.dp, vertical = 14.dp)
                                    .clickable {
                                        clickCostumer()
                                    }, verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Customer", style = caption.copy(
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painter = painterResource(id = R.drawable.right_arrow),
                                    contentDescription = ""
                                )
                            }
                            SpacerHeight(height = 16.dp)
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "List Tiket", style = largeTitleSemiBold.copy(
                                        fontSize = 14.sp, color = OrangeFlight
                                    )
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Oneway",
                                    style = caption,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(color = GreyFlight)
                                        .padding(horizontal = 5.dp, vertical = 3.dp),
                                    color = Color.White
                                )
                                SpacerWidth(width = 8.dp)
                                Text(
                                    text = "Roundtrip", style = caption,
                                    color = Color.White,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(color = GreyFlight)
                                        .padding(horizontal = 5.dp, vertical = 3.dp),
                                )
                            }
                        }
                        LazyColumn(
                            Modifier.fillMaxSize(), contentPadding = PaddingValues(
                                top = 16.dp, bottom = 40.dp, start = 16.dp, end = 16.dp
                            ), verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(response.size) { i ->
                                ItemCardTicket(model = response[i], modifier = Modifier.clickable {
                                    clickDetailTiket(response[i])
                                })
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                ErrorMessage(msg = it.errorMessage)
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}