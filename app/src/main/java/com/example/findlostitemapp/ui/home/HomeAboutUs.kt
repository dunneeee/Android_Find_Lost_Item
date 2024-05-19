package com.example.findlostitemapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme

@Composable
fun HomeAboutUs(modifier: Modifier = Modifier) {
    ElevatedCard(modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Về chúng tôi", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align
                    (Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Chào mừng bạn đến với Tìm Đồ Thất Lạc!\n" +
                        "\n" +
                        "Chúng tôi là nền tảng trực tuyến giúp bạn tìm lại những đồ vật mất mát một cách dễ dàng. Với sứ mệnh làm việc cùng cộng đồng, chúng tôi cam kết đem lại trải nghiệm minh bạch và hiệu quả nhất cho mọi người.\n" +
                        "\n" +
                        "Hãy tham gia với chúng tôi ngay hôm nay và giúp nhau tìm lại những món đồ quan trọng!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview
@Composable
private fun HomeAboutUsPreview() {
    FindLostItemAppTheme {
        HomeAboutUs()
    }
}