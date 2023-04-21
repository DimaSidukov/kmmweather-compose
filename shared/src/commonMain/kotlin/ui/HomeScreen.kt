package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entities.Forecast
import darkPurple
import darkPurple2
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    screenHeight: Dp,
    viewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    latitude: Double,
    longitude: Double,
    forceRemote: Boolean
) {
    val currentHour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour

    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.requestData(latitude, longitude, forceRemote)
        viewModel.requestLocationList()
    }

    var forecast by remember {
        mutableStateOf<Forecast?>(null)
    }
    var forecastList by remember {
        mutableStateOf<List<Forecast>>(emptyList())
    }

    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is HomeViewState.Forecast -> {
                forecast = (uiState.value as HomeViewState.Forecast).forecast
            }

            is HomeViewState.Error -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = (uiState.value as HomeViewState.Error).cause,
                )
            }

            is HomeViewState.ForecastList -> {
                forecastList = (uiState.value as HomeViewState.ForecastList).forecastList
            }

            is HomeViewState.NoData -> {

            }
        }
    }

    Box {
        HomeScreenBackground(screenHeight.value)
        TopAppBar()
        ForecastOverview(
            screenHeight = screenHeight,
            forecast = forecast,
            forecastList = forecastList,
            currentHour = currentHour,
            onItemClicked = {
                viewModel.requestData(it.latitude, it.longitude, true)
            }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreenBackground(screenHeight: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF311C7F),
                        darkPurple
                    ),
                    startY = screenHeight / 2
                )
            )
    ) {
        Image(
            painter = painterResource("bg_night_1.xml"),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopAppBar(
    onProfileClicked: () -> Unit = { },
    onDrawerClicked: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp),
            onClick = onProfileClicked
        ) {
            Icon(
                painter = painterResource("ic_account.xml"),
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp),
            onClick = onDrawerClicked
        ) {
            Icon(
                painter = painterResource("ic_drawer.xml"),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ForecastOverview(
    screenHeight: Dp,
    forecast: Forecast?,
    forecastList: List<Forecast>,
    currentHour: Int,
    onItemClicked: (Forecast) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 172.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(0.6f),
                text = forecast?.address?.uppercase() ?: "",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
            )
            Text(
                text = forecast?.dateTemperatureRange ?: "",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${forecast?.currentHourTemperature ?: ""}°C",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp
            )
            Text(
                text = forecast?.weatherDescription ?: "",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 21.sp,
                textAlign = TextAlign.End
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(top = screenHeight / 2 - 100.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
        ) {
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
            items(
                count = forecastList.size
            ) { idx ->
                Box(
                    modifier = Modifier
                        .size(172.dp, 215.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .clickable {
                            onItemClicked(forecastList[idx])
                        }
                ) {
                    Image(
                        painter = painterResource(
                            if (idx % 2 == 0) "bg_sun_location.xml"
                            else "bg_snow_mountains.xml"
                        ),
                        contentDescription = null
                    )
                    Text(
                        text = "${forecastList[idx].address} ${forecastList[idx].currentHourTemperature}°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(
                                top = 24.dp,
                                start = 20.dp,
                                end = 20.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(44.dp))
            }
        }
        Text(
            text = "Today",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp),
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 20.sp
        )
        LazyRow(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(start = 20.dp))
            }
            items(
                count = forecast?.dayTemperatureList?.size ?: 0
            ) { idx ->
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                        .background(darkPurple2)
                ) {
                    Text(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                            .padding(5.dp),
                        text = "${
                            forecast?.dayTemperatureList?.get(idx)
                        }°C",
                        color = Color.Black
                    )
                    Text(
                        text = if (idx == currentHour) "Now" else "$idx:00",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .align(Alignment.BottomCenter),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(21.dp))
            }
        }
    }
}