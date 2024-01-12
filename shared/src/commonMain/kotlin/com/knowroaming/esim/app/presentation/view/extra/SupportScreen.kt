package com.knowroaming.esim.app.presentation.view.extra

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.knowroaming.esim.app.presentation.components.button.AppButton
import com.knowroaming.esim.app.presentation.components.button.AppButtonVariant
import com.knowroaming.esim.app.presentation.theme.BrandColor
import com.knowroaming.esim.app.presentation.theme.BrandSize
import com.knowroaming.esim.app.util.AppRoute
import com.knowroaming.esim.app.util.OpenEmailClient
import com.knowroaming.esim.app.util.OpenWhatsApp
import moe.tlaster.precompose.navigation.Navigator


enum class SupportAction {
    EMAIL, WHATSAPP, ACTIONED
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SupportScreen(navigator: Navigator) {
    val pagerState = rememberPagerState(pageCount = { 5 })

    val (action, setAction) = remember { mutableStateOf(SupportAction.ACTIONED) }

    when (action) {
        SupportAction.WHATSAPP -> {
            OpenWhatsApp("Support: I need help with eSim App")
            setAction(SupportAction.ACTIONED)
        }

        SupportAction.EMAIL -> {
            OpenEmailClient(
                "support@knowroaming.com", "Support", "I need help with eSim App"
            )
            setAction(SupportAction.ACTIONED)
        }

        else -> {}
    }



    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = BrandSize.lg),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = BrandSize.md,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = BrandSize.lg)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(BrandSize.lg)
            ) {
                Text(
                    text = "Frequently Asked Questions",
                    modifier = Modifier.padding(BrandSize.lg).align(Alignment.CenterHorizontally)
                )
                HorizontalPager(
                    state = pagerState, modifier = Modifier.weight(1f)
                ) { page ->
                    ViewPagerItem(page = page, navigator)
                }

                ViewPagerDotsIndicator(
                    Modifier.height(BrandSize.x3l).fillMaxWidth().align(Alignment.End),
                    pageCount = 5,
                    currentPageIteration = pagerState.currentPage
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = BrandSize.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BrandSize.lg),
        ) {

            AppButton(
                type = AppButtonVariant.Primary,
                text = "Email support@knowroaming.com",
                onClick = {
                    setAction(SupportAction.EMAIL)
                })

            AppButton(type = AppButtonVariant.Tertiary, text = "WhatsApp +1", onClick = {
                setAction(SupportAction.WHATSAPP)
            })
        }
    }
}


@Composable
fun ViewPagerItem(page: Int, navigator: Navigator) {
    val supportPage = SupportPage.entries[page]
    Box(
        modifier = Modifier.fillMaxSize().clickable { navigator.navigate(AppRoute.FAQ) },
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = supportPage.title,
            modifier = Modifier.padding(bottom = BrandSize.sm),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight(500),
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun ViewPagerDotsIndicator(
    modifier: Modifier = Modifier, pageCount: Int, currentPageIteration: Int
) {

    Row(
        modifier = modifier, horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (currentPageIteration == iteration) BrandColor.White else BrandColor.Gray200
            Box(
                modifier = Modifier.padding(BrandSize.sm).background(color).size(BrandSize.md)
            )
        }
    }
}

enum class SupportPage(val title: String) {
    Installation("Installation"), Troubleshooting("Troubleshooting"), Support("Support"), General("General"), AboutESims(
        "About eSims"
    )
}