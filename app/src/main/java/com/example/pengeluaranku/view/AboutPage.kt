package com.example.pengeluaranku.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.pengeluaranku.R
import com.example.pengeluaranku.ui.theme.PengeluarankuTheme

@Composable
fun AboutPage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 120.dp)) {
        Box(modifier = Modifier
            .size(180.dp)){
            Image(painter = painterResource(id = R.drawable.logo_about), contentDescription = "Logo In About")
        }
        Text(
            text = stringResource(id = R.string.app_description),
            textAlign = TextAlign.Justify,
            fontSize = 18.sp,
            letterSpacing = 0.02.em,
            lineHeight = 30.sp,
            modifier = Modifier.padding(horizontal = 45.dp, vertical = 35.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun AboutPagePreview() {
    PengeluarankuTheme {
        AboutPage()
    }
}