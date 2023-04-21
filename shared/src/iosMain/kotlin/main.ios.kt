import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import platform.CoreGraphics.CGFloat


fun MainViewController(height: CGFloat) = ComposeUIViewController { App(height.dp) }