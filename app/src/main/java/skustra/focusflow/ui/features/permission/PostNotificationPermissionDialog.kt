package skustra.focusflow.ui.features.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.ButtonColor

@Composable
fun PostNotificationPermissionDialog(
    drawableProvider: DrawableProvider,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    onAllowClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .shadow(
                elevation = 1.dp,
                spotColor = Color.Gray
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LocalizationManager.getText(LocalizationKey.PostPermissionNeedMessage),
                textAlign = TextAlign.Center,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(color = ButtonColor)
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onAllowClicked) {
                        Icon(
                            painter = painterResource(id = drawableProvider.getNotificationIcon()),
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = LocalizationManager.getText(LocalizationKey.Allow),
                        modifier = Modifier.padding(end = 16.dp, bottom = 2.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}