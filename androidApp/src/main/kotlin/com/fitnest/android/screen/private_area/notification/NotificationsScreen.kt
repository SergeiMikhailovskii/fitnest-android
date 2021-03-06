package com.fitnest.android.screen.private_area.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitnest.android.extension.vibrate
import com.fitnest.android.style.Padding
import com.fitnest.domain.extension.move
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import org.kodein.di.compose.rememberInstance
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalTime
@Composable
internal fun NotificationsScreen() {
    val viewModelFactory: ViewModelProvider.Factory by rememberInstance()
    val viewModel = viewModel(
        factory = viewModelFactory,
        modelClass = NotificationsViewModel::class.java
    )

    val screenData by viewModel.screenDataFlow.collectAsState()

    val listState = rememberLazyListState()
    var position by remember { mutableStateOf<Float?>(null) }
    var draggedItem by remember { mutableStateOf<Int?>(null) }

    val progress by viewModel.progressStateFlow.collectAsState()

    val view = LocalView.current

    val indexWithOffset by derivedStateOf {
        draggedItem
            ?.let { listState.layoutInfo.visibleItemsInfo.getOrNull(it - listState.firstVisibleItemIndex) }
            ?.let { Pair(it.index, (position ?: 0f) - it.offset - it.size / 2f) }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .combine(snapshotFlow { position }.distinctUntilChanged()) { state, pos ->
                pos?.let { draggedCenter ->
                    state.visibleItemsInfo.minByOrNull {
                        (draggedCenter - (it.offset + it.size / 2F)).absoluteValue
                    }?.index
                }
            }.distinctUntilChanged()
            .collect { near ->
                draggedItem = when {
                    near == null -> null
                    draggedItem == null -> near
                    else -> near.also {
                        val movedList = screenData.notifications.toMutableList().apply {
                            move(draggedItem ?: 0, it)
                        }.toList()
                        viewModel.updateNotifications(movedList)

                        vibrate(view)
                    }
                }
            }
    }

    Box {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(horizontal = Padding.Padding16)
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset ->
                            listState.layoutInfo.visibleItemsInfo
                                .firstOrNull {
                                    offset.y.toInt() in it.offset..it.offset + it.size
                                }
                                ?.also {
                                    position = it.offset + it.size / 2F
                                }
                        },
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            position = position?.plus(dragAmount.y)
                        },
                        onDragEnd = {
                            draggedItem = null
                            position = null
                        }
                    )
                }
        ) {
            itemsIndexed(screenData.notifications) { index, it ->
                val offset by remember {
                    derivedStateOf {
                        indexWithOffset?.takeIf { it.first == index }?.second
                    }
                }
                NotificationItem(
                    modifier = Modifier
                        .zIndex(offset?.let { 1f } ?: 0f)
                        .graphicsLayer {
                            translationY = offset ?: 0f
                        },
                    title = it.title,
                    description = it.description,
                    icon = it.icon,
                    isActive = it.isActive,
                    isPinned = it.isPinned,
                    onPin = {
                        viewModel.pinNotification(screenData.notifications[index].id)
                    },
                    onDelete = {
                        viewModel.deleteNotification(screenData.notifications[index].id)
                    }
                )
                if (screenData.notifications.last() != it) {
                    Divider()
                }
            }
        }
    }

    if (progress) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
