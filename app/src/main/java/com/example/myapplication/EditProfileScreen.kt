import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.model.User

@Composable
fun EditProfileScreen(
    user: User?,
    viewModel: UserChordViewModel,
    navController: NavController
) {
    if (user == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var name by remember { mutableStateOf(user.name) }
    var phone by remember { mutableStateOf(user.phoneNumber) }
    var email by remember { mutableStateOf(user.email) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Editar Perfil", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        TextField(value = name, onValueChange = { name = it }, label = { Text("Nome") })
        Spacer(Modifier.height(10.dp))
        TextField(value = phone, onValueChange = { phone = it }, label = { Text("Telefone") })
        Spacer(Modifier.height(10.dp))
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            val updated = user.copy(name = name, phoneNumber = phone, email = email)
            viewModel.updateUser(updated)
            navController.popBackStack()
        }) {
            Icon(Icons.Default.Check, contentDescription = "Salvar")
            Spacer(Modifier.width(8.dp))
            Text("Salvar")
        }
    }
}
