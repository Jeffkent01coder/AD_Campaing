package com.example.adcampaing.core.shared_composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.adcampaing.R
import com.example.adcampaing.ui.theme.Purple500

@Composable
fun CircularImage(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    userImage: String
) {
    val viewed by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(
                shape = CircleShape,
                border = if (viewed) {
                    BorderStroke(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Blue,
                                Purple500,
                                Color.Cyan
                            ),
                            start = Offset(
                                0f, 0f
                            ),
                            end = Offset(
                                100f, 100f
                            )
                        )
                    )
                } else {
                    BorderStroke(
                        width = 2.dp,
                        color = Color.Gray
                    )
                }
            )
            .padding(4.dp)
            .clickable {
                onClicked()
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userImage)
                .crossfade(true)
                .fallback(R.drawable.user)
                .build(),
            contentDescription = "stories",
            modifier = Modifier.clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }

}