package com.example.composepractice

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    val searchHistory = listOf("Poringa", "Gang bang", "Xvideos")

    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { newQuery ->
            println("Performing search on query: $newQuery")
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
                IconButton(onClick = { if (query.isNotEmpty()) query = "" else active = false }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                }
            }
        }
    ) {
        searchHistory.takeLast(3).forEach { item ->
            ListItem(
                modifier = Modifier.clickable {
                    query = item
                },
                headlineContent = { Text(text = item) },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_history),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

data class SearchHistoryState(
    val history: List<String> = listOf("Casa", "Tribunal", "Marengo"),
    val filtered: List<String> = history
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockedSearchBar() {
    var query by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    var filteredSearch by remember {
        mutableStateOf(SearchHistoryState())
    }

    androidx.compose.material3.DockedSearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            filteredSearch = filteredSearch.copy(
                filtered = filteredSearch.history.filter { item -> item.contains(query) }
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
                IconButton(onClick = { if (query.isNotEmpty()) query = "" else active = false }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                }
            }
        }
    ) {
        filteredSearch.filtered.forEach { item ->
            ListItem(
                modifier = Modifier.clickable {
                    query = item
                },
                headlineContent = { Text(text = item) },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_history),
                        contentDescription = null
                    )
                }
            )
        }
    }
}