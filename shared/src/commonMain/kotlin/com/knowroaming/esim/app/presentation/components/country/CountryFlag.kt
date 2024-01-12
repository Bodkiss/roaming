package com.knowroaming.esim.app.presentation.components.country

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.knowroaming.esim.app.domain.model.Country
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.util.FlagKit
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CountryFlag(
    height: Dp = 56.dp,
    width: Dp = 68.dp,
    contentScale: ContentScale? = null,
    modifier: Modifier = Modifier,
    country: Country
) {
    if (country.isRegion) {
        Image(
            contentScale = ContentScale.Fit,
            contentDescription = "Country Flag: ${country.name}",
            painter = painterResource("regions/${country.code}.png"),
            modifier = modifier.background(BrandColor.White).heightIn(min = height),
        )
    } else {
        Image(
            contentScale = contentScale ?: ContentScale.FillHeight,
            contentDescription = "Country Flag: ${country.name}",
            painter = rememberImagePainter(FlagKit.getUrl(country.code)),
            modifier = modifier.heightIn(min = height),
        )
    }
}