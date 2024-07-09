package com.example.easyreader.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyreader.R
import com.example.easyreader.ui.theme.Black22
import com.example.easyreader.ui.theme.Black33
import com.example.easyreader.ui.theme.Green40
import com.example.easyreader.ui.theme.Green80
import com.example.easyreader.ui.theme.White

@Composable
fun ArticleDetail(title: String, content: AnnotatedString, onBackClick: () -> Unit, onWordClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Green40,
                        Green80
                    ),
                    tileMode = TileMode.Clamp
                )
            )
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(onBackClick = onBackClick)
        ArticleContent(title, content, onWordClick = onWordClick)
    }
}

@Composable
fun Header(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 4.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = Black22,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(8.dp)
                .clickable {
                    onBackClick()
                }
        )
    }
}

@Composable
fun ArticleContent(title: String, content: AnnotatedString, onWordClick: (Int) -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = White,
        tonalElevation = 5.dp,
        modifier = Modifier.padding(all = 12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = Black33,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            ClickableText(
                text = content,
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                    fontSize = 18.sp,
                    color = Black33,
                    lineHeight = 28.sp
                ),
                onClick = { offset ->
                    Log.d("TAG", "offset: $offset")
                    onWordClick(offset)
                }
            )
        }
    }
}