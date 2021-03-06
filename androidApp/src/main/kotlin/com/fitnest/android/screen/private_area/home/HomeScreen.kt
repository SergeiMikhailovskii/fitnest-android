package com.fitnest.android.screen.private_area.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fitnest.android.navigation.handleNavigation
import com.fitnest.android.screen.private_area.home.composable.*
import com.fitnest.android.style.Padding
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance
import kotlin.time.ExperimentalTime

@Preview
@Composable
@ExperimentalAnimationApi
@ExperimentalTime
fun HomeScreenPreview() {
    HomeScreen(navController = rememberAnimatedNavController(AnimatedComposeNavigator()))
}

@ExperimentalTime
@Composable
fun HomeScreen(navController: NavController) {
    val viewModelFactory: ViewModelProvider.Factory by rememberInstance()
    val viewModel = viewModel(
        factory = viewModelFactory,
        modelClass = HomeViewModel::class.java
    )

    val screenData by viewModel.screenDataFlow.collectAsState()
    val loading by viewModel.progressStateFlow.collectAsState()

    LaunchedEffect(null) {
        launch {
            viewModel.routeSharedFlow.collect {
                handleNavigation(
                    route = it,
                    navController = navController
                )
            }
        }
    }

    Scaffold {
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(horizontal = Padding.Padding30)
                    .padding(bottom = Padding.Padding30)
                    .verticalScroll(rememberScrollState())
            ) {
                screenData.headerWidget?.let { HeaderBlock(it, viewModel) }
                screenData.bmiWidget?.let { BMIBlock(it) }
                screenData.todayTargetWidget?.let { TodayTargetBlock(it) }
                screenData.activityStatusWidget?.let { ActivityStatusBlock(it) }
                screenData.latestWorkoutWidget?.let { LatestWorkoutBlock(it) }
                Box(modifier = Modifier.height(200.dp))
            }
        }
    }
}
