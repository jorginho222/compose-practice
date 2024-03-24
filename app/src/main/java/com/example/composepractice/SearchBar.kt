package com.example.composepractice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ListState(
    val list: List<String> = listOf("Casa", "Tribunal", "Marengo"),
    val filtered: List<String> = list
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockedSearchBar() {
    var filteredSearch by remember {
        mutableStateOf(ListState())
    }
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    Scaffold {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                filteredSearch = filteredSearch.copy(
                    filtered = filteredSearch.list.filter { item -> item.contains(query) }
                )
            },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = "Busque nomas")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (active) {
                    IconButton(onClick = {
                        if (query.isNotEmpty()) query = "" else active = false
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(filteredSearch.filtered) { item ->
                    ListItem(headlineContent = { Text(text = item) })
                }
            }
        }
    }
}