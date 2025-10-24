import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.models.User

@Composable
fun ProfileScreen(
    user: User?,
    navController: NavController
) {
    if (user == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(16.dp))

        ProfileInfoRow("Nome", user.name)
        ProfileInfoRow("Email", user.email)
        ProfileInfoRow("Telefone", user.phoneNumber)

        Spacer(Modifier.height(24.dp))

        Button(onClick = { navController.navigate("editProfile") }) {
            Icon(Icons.Default.Edit, contentDescription = "Editar")
            Spacer(Modifier.width(8.dp))
            Text("Editar Perfil")
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFFCCC2DC))
            .padding(8.dp)
    ) {
        Text("$label: $value", fontSize = 18.sp, color = Color(0xFF7D5260))
    }
}
