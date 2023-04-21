import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import ui.MainScreen

@Composable
fun App(screenHeight: Dp) {
    MyApplicationTheme {
        MainScreen(screenHeight)
    }
}