import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable fun MainView() = App(LocalConfiguration.current.screenHeightDp.dp)
