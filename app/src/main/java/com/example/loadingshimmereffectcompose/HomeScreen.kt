package com.example.loadingshimmereffectcompose

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loadingshimmereffectcompose.ui.theme.LoadingShimmerEffectComposeTheme
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    var isLoading by remember{
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true){
        delay(10000)
        isLoading = false
    }
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(20){
            ShimmerListItem(isLoading = isLoading, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Icon(Icons.Default.Person,null, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "This is a long text to show that our shimmer display " +
                                "is looking perfectly fine"
                    )
                }
            }
        }
    }

}

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading){
        Row(modifier = modifier) {
            Box(modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
            }
        }
    }else{
        contentAfterLoading()
    }
}

//Hero Code of the Project
fun Modifier.shimmerEffect(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(0.6f),
            Color.LightGray.copy(0.2f),
            Color.LightGray.copy(0.6f),
        ),
        start = Offset.Zero,
        end = Offset( x = animatedOffset, y = animatedOffset)
    )
    background(brush)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoadingShimmerEffectComposeTheme {
        HomeScreen()
    }
}