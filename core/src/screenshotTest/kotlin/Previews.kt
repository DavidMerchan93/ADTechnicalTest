import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.davidmerchan.core.ui.ConnectionMessage
import com.davidmerchan.core.ui.ErrorScreen
import com.davidmerchan.core.ui.LoadingScreen
import com.davidmerchan.core.ui.theme.ADTechnicalTestTheme

@Preview
@Composable
internal fun LoadingScreenPreview() {
    ADTechnicalTestTheme {
        LoadingScreen()
    }
}

@Preview
@Composable
internal fun ErrorScreenPreview() {
    ADTechnicalTestTheme {
        ErrorScreen()
    }
}

@Preview
@Composable
internal fun ConnectionMessagePreview() {
    ADTechnicalTestTheme {
        ConnectionMessage()
    }
}
