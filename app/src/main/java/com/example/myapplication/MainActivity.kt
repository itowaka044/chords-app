package com.example.myapplication

import EditProfileScreen
import ProfileScreen
import SearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.models.User
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.viewModel.UserChordViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(this)
        val repository = UserRepository(db.userDao(), db.chordDao(), db.userChordRefDao())
        val factory = UserChordViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[UserChordViewModel::class.java]

        viewModel.seedInitialChords()

        setContent {


            val navController = rememberNavController()
            val chords by viewModel.allChords.observeAsState(emptyList())
            val user by viewModel.currentUser.collectAsState(initial = null)


            val genres = remember { mutableStateListOf("all", "rock", "pop", "jazz", "folk") }
            val instruments = remember { mutableStateListOf("guitarra", "baixo", "bateria", "teclado")}

            LaunchedEffect(user) {
                if (user == null) {
                    viewModel.defaultUser()
                }
            }

            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.height(80.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(onClick = { navController.navigate("home") })
                            {
                                Icon(Icons.Filled.Home, "Início")
                            }
                            Button(onClick = { navController.navigate("favoritos") })
                            {
                                Icon(Icons.Filled.Favorite, "Favoritos")
                            }
                            Button(onClick = { navController.navigate("perfil") })
                            {
                                Icon(Icons.Filled.Person, "Perfil")
                            }
                        }
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {

                    composable("home") {
                        val user by viewModel.currentUser.collectAsState()
                        if (user != null)
                            HomeScreen(viewModel, user!!, navController)
                    }

                    composable("favoritos") {
                        val instruments = remember { listOf("Guitarra", "Baixo", "Teclado") }
                        val user by viewModel.currentUser.collectAsState()

                        if (user != null) {
                            FavScreen(
                                user = user!!,
                                viewModel = viewModel,
                                instruments = instruments
                            )
                        }
                    }

                    composable("perfil") {
                        val user by viewModel.currentUser.collectAsState()
                        ProfileScreen(user, navController)
                    }

                    composable("editProfile") {
                        val user by viewModel.currentUser.collectAsState()
                        EditProfileScreen(user, viewModel, navController)
                    }

                    composable("search") {
                        val user by viewModel.currentUser.collectAsState()
                        if (user != null) {
                            SearchScreen(viewModel, user!!)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BottomIcon(icon: ImageVector, desc: String, onClick: () -> Unit) {
        Button(onClick = onClick, modifier = Modifier.size(60.dp)) {
            Icon(icon, desc)
        }
    }

    @Composable
    fun AddChordFromList(){}

    @Composable
    fun RemoveChordFromList(){}

    @Composable
    fun RowProfile(text: String = "textoo"){
        Row(

        ) {
            Row() {

            }

            Row() {

            }
        }
    }

    @Composable
    fun NameProfile(user: User, saveUserClick: (String,String,String) -> Unit){

        var userName by remember { mutableStateOf(user.name) }
        var userPhone by remember { mutableStateOf(user.phoneNumber) }
        var userEmail by remember { mutableStateOf(user.email) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            TextField(
                value = userPhone,
                onValueChange = {
                    userPhone = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            TextField(
                value = userEmail,
                onValueChange = {
                    userEmail = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            Button(
                onClick = {
                    saveUserClick(userName, userPhone, userEmail)
                }
            ){
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "edit user",
                )
            }
        }
    }

    @Composable
    fun CardProfile(userName: String) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .size(width = 500.dp,
                    height = 100.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(20.dp)
            ) {

                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = "pessoa",
                    modifier = Modifier
                        .size(80.dp)

                )


                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Column {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight(500)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )

                    Text(
                        text = "$userName"
                    )
                }
            }
        }
    }
}