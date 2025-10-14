package com.example.myapplication

import UserChordViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.model.Chord
import com.example.myapplication.model.User

@Composable
fun HomeScreen(
    viewModel: UserChordViewModel,
    user: User,
    navController: NavController
) {
    val chords by viewModel.allChords.observeAsState(emptyList())
    val genres = remember { listOf("all", "rock", "pop", "jazz", "folk") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        HeaderHome(navController)
        Spacer(Modifier.height(10.dp))
        NavHorizontal(genres)
        Spacer(Modifier.height(10.dp))
        ImageBanner()
        Spacer(Modifier.height(20.dp))

        Text(
            "Top cifras",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        chords.forEachIndexed { index, chord ->
            CardChordItem(
                index = index + 1,
                chord = chord,
                onLikeClick = { viewModel.addFavorite(user.id, chord.id) }
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun CardChordItem(
    index: Int,
    chord: Chord,
    onLikeClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCCC2DC)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$index. ${chord.title}", fontWeight = FontWeight.Bold)
            Button(onClick = onLikeClick) {
                Icon(Icons.Default.Favorite, contentDescription = "Favoritar")
            }
        }
    }
}

@Composable
fun HeaderHome(navController: NavController) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(40.dp))
        Button(onClick = { navController.navigate("search") }) {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
        }
    }
}

@Composable
fun ImageBanner(){
    Card(
        modifier = Modifier
            .size(width = 500.dp, height = 200.dp),
    ){
        Image(
            painter = painterResource(id = R.drawable.default_banner),
            contentDescription = "foto da postagem",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

