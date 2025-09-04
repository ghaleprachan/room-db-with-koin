package app.prgghale.roomdb.composables

import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.prgghale.roomdb.iconOutlined

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    title: String = "Room Database",
    onSearch: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val topAppBarState: TopAppBarState = rememberTopAppBarState()
    val decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay<Float>()
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = topAppBarState,
        flingAnimationSpec = decayAnimationSpec
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(iconOutlined.Search, contentDescription = null)
                    }
                }
            )
        },
        content = content
    )
}