package com.fitnest.android.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fitnest.android.R
import com.fitnest.android.navigation.handleNavigation
import com.fitnest.android.style.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance

@Composable
fun SplashScreen(navController: NavController) {
    val viewModelFactory: ViewModelProvider.Factory by rememberInstance()

    val viewModel = viewModel(
        factory = viewModelFactory,
        modelClass = SplashViewModel::class.java
    )

    var progress: Boolean? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = null) {
        launch {
            viewModel.routeSharedFlow.collect {
                handleNavigation(route = it, navController = navController)
            }
        }
        launch {
            viewModel.progressStateFlow.collect {
                progress = it
            }
        }
        viewModel.generateToken()
    }

    Scaffold {
        Box(
            modifier = Modifier
                .background(brush = Brush.verticalGradient(colors = BrandGradient))
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
            if (progress == false) {
                OutlinedButton(
                    onClick = { viewModel.navigateNext() },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(
                            start = Padding.Padding30,
                            end = Padding.Padding30,
                            bottom = Padding.Padding40
                        )
                        .height(Dimen.Dimen60)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                ) {
                    Text(
                        text = stringResource(id = R.string.splash_button_title),
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextSize.Size16
                    )
                }
            }
        }
    }
}
