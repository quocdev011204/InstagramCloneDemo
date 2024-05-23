package com.example.instagramclonedemo.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.instagramclonedemo.ui.theme.AccentColor
import com.example.instagramclonedemo.ui.theme.IconDark

@Composable
fun CustomRaisedButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor),
        shape = RoundedCornerShape(size = 5.dp),
        onClick = onClick
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = IconDark)
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.button,
                color = Color.White,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }
}