package com.example.instagramclonedemo.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.instagramclonedemo.common.utils.Constants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImagePickerBottomSheetDialog(
    bottomSheetState: ModalBottomSheetState,
    onPickerOptionClicked: (String) -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SheetContent(onPickerOptionClicked)
        }) {}
}

@Composable
fun SheetContent(onPickerOptionClicked: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFB2F0CC))
                    .size(70.dp)
                    .clickable { onPickerOptionClicked(Constants.GALLERY) }
            ) {
                Icon(imageVector = Icons.Filled.Image, contentDescription = null, tint = Color(0xFF00CD58))
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "Gallery", style = MaterialTheme.typography.body1)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFFFE2C6))
                    .size(70.dp)
                    .clickable { onPickerOptionClicked(Constants.CAMERA) }
            ) {
                Icon(imageVector = Icons.Filled.Camera, contentDescription = null, tint = Color(0xFFFF9F42))
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "Camera", style = MaterialTheme.typography.body1)
        }
    }
}