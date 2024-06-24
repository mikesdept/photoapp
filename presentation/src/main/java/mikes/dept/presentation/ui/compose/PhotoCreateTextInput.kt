package mikes.dept.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mikes.dept.presentation.R

@Composable
fun PhotoCreateTextInput(
    value: State<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value.value,
        onValueChange = { newText -> onValueChanged(newText) },
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(1.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.value.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.photo_create_hint_text),
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun PhotoCreateTextInputPreview() {
    PhotoCreateTextInput(
        value = remember { mutableStateOf("") },
        onValueChanged = {}
    )
}
