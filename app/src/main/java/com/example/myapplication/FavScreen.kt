package com.example.myapplication

import UserChordViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.models.Chord
import com.example.myapplication.models.User

@Composable
fun FavScreen(
    modifier: Modifier = Modifier,
    user: User,
    viewModel: UserChordViewModel,
    instruments: List<String>
) {
    val favorites by viewModel.getUserFavorites(user.id).observeAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(30.dp))

        Text(
            text = "Favoritos",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth()) {
            NavHorizontal(instruments)
        }

        Spacer(Modifier.height(20.dp))

        if (favorites == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        val favoriteChords = favorites?.chords ?: emptyList()

        if (favoriteChords.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma cifra favoritada ainda.")
            }
        } else {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                favoriteChords.forEachIndexed { index, chord ->
                    Spacer(Modifier.height(8.dp))
                    SongCard(
                        index = index,
                        chord = chord,
                        onRemove = {
                            viewModel.removeFavorite(user.id, chord.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SongCard(
    index: Int,
    chord: Chord,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFCCC2DC))
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${chord.id}. ${chord.title}",
            color = Color(0xFF7D5260),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remover dos favoritos",
                tint = Color.DarkGray
            )
        }
    }
}
