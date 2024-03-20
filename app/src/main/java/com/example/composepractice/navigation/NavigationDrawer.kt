package com.example.composepractice.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(
        DrawerItem(icon = Icons.Default.Favorite, label = "Likes", secondaryLabel = "64"),
        DrawerItem(icon = Icons.Default.Face, label = "Gente", secondaryLabel = "12"),
        DrawerItem(icon = Icons.Default.MailOutline, label = "Mail", secondaryLabel = "4"),
        DrawerItem(icon = Icons.Default.Settings, label = "Settings", secondaryLabel = ""),
    )
    var selectedItem by remember {
        mutableStateOf(items[0])
    }
    var people by remember {
        mutableStateOf(PeopleState())
    }
    val peopleRequest = listOf(
        Person(name = "Candentey", age = 68),
        Person(name = "Marcozz", age = 56),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Header", style = MaterialTheme.typography.headlineLarge)
                }

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.label) },
                        selected = item == selectedItem,
                        onClick = {
                            if (item.label === "Gente") {
                                people = people.copy(
                                    peopleRequest
                                )
                            }
                            selectedItem = item
                            scope.launch { drawerState.close() }
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        badge = { Text(text = item.secondaryLabel) },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Content(
                onMenuIconClick = {
                    scope.launch { drawerState.open() }
                },
                people
            )
        }
    )
}

data class DrawerItem(
    val icon: ImageVector,
    val label: String,
    val secondaryLabel: String,
)

data class Person(
    val name: String,
    val age: Number
)

data class PeopleState(
    val people: List<Person> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    onMenuIconClick: () -> Unit,
    people: PeopleState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onMenuIconClick() }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                title = { Text(text = "Top Stories") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(people.people) { person ->
                androidx.compose.material3.ListItem(headlineContent = { Text(text = person.name) })
            }
        }
    }
}