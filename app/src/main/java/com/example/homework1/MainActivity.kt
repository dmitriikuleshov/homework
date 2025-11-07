package com.example.homework1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    GridCounterScreen()
                }
            }
        }
    }
}

@Composable
private fun GridCounterScreen() {
    var itemCount by rememberSaveable { mutableIntStateOf(0) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    GridCounterContent(
        itemCount = itemCount,
        isLandscape = isLandscape,
        onAddItem = { itemCount++ }
    )
}

@Composable
private fun GridCounterContent(
    itemCount: Int,
    isLandscape: Boolean,
    onAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    val columnCount = if (isLandscape) {
        GridConstants.COLUMNS_LANDSCAPE
    } else {
        GridConstants.COLUMNS_PORTRAIT
    }

    val buttonWeight = if (isLandscape) {
        GridConstants.BUTTON_WEIGHT_LANDSCAPE
    } else {
        GridConstants.BUTTON_WEIGHT_PORTRAIT
    }

    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(AppDimens.SpacingTop))

        NumberGrid(
            itemCount = itemCount,
            columnCount = columnCount,
            modifier = Modifier.weight(GridConstants.GRID_WEIGHT)
        )

        AddItemButton(
            onClick = onAddItem,
            modifier = Modifier
                .weight(buttonWeight)
                .padding(AppDimens.SpacingButton)
        )
    }
}

@Composable
private fun NumberGrid(
    itemCount: Int,
    columnCount: Int,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount), modifier = modifier
    ) {
        items(
            count = itemCount, key = { it }) { index ->
            NumberGridItem(number = index)
        }
    }
}

@Composable
private fun NumberGridItem(
    number: Int,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (number % 2 == 0) {
        colorResource(R.color.grid_item_even)
    } else {
        colorResource(R.color.grid_item_odd)
    }

    Text(
        text = number.toString(),
        modifier = modifier
            .aspectRatio(1f)
            .padding(AppDimens.SpacingItem)
            .background(backgroundColor)
            .wrapContentHeight(),
        color = colorResource(R.color.text_white),
        textAlign = TextAlign.Center,
        fontSize = AppDimens.FontSizeGridItem
    )
}

@Composable
private fun AddItemButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(AppDimens.CornerRadiusButton)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add item"
        )
    }
}