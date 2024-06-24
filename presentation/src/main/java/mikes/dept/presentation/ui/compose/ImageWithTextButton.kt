package mikes.dept.presentation.ui.compose

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mikes.dept.presentation.R

@Composable
fun ImageWithTextButton(
    image: State<Uri?>,
    @StringRes textResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier.height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            AsyncImage(
                model = image.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(48.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = stringResource(id = textResId),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ImageWithTextButtonPreview() {
    ImageWithTextButton(
        image = remember { mutableStateOf(null) },
        textResId = R.string.photo_create_hint_text,
        onClick = {}
    )
}
