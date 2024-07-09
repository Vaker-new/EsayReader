package com.example.easyreader.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyreader.R
import com.example.easyreader.dao.data.Article
import com.example.easyreader.ui.theme.Black22
import com.example.easyreader.ui.theme.Black33
import com.example.easyreader.ui.theme.Black66
import com.example.easyreader.ui.theme.Green40
import com.example.easyreader.ui.theme.Green80
import com.example.easyreader.ui.theme.GreenTheme
import com.example.easyreader.ui.theme.White

@Composable
fun MainView(articleList: List<Article>, onItemClick: (article: Article) -> Unit) {
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
            .padding(top = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()
        ArticleList(articleList = articleList, onItemClick = onItemClick)
    }
}

@Composable
fun Title() {
    Text(
        text = "文章列表",
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        fontSize = 24.sp,
        color = Black22,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun ArticleList(articleList: List<Article>, onItemClick: (article: Article) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(articleList.size) { index ->
            ArticleListItem(article = articleList[index], onItemClick = onItemClick)
        }
    }
}

@Composable
fun ArticleListItem(article: Article, onItemClick: (article: Article) -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = White,
        tonalElevation = 5.dp,
        modifier = Modifier.padding(all = 8.dp),
        onClick = { onItemClick(article) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_article),
                contentDescription = null,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                tint = GreenTheme
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = article.title,
                    fontSize = 20.sp,
                    color = Black33,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.titleZh,
                    fontSize = 15.sp,
                    color = Black66,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}