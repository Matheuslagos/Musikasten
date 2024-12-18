package com.example.musikasten

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musikasten.ui.theme.MusikastenTheme

class MusicListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusikastenTheme {
                // Passa o contexto para a tela
                MusicListScreen(this)
            }
        }
    }
}

@Composable
fun MusicListScreen(context: Context) {
    // Recupera o username do SharedPreferences
    val sharedPref = remember {
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }
    val username = sharedPref.getString("username", "Usuário")

    // Dados fictícios de músicas
    val musicList = listOf(
        Pair("Música 1", R.drawable.music1),
        Pair("Música 2", R.drawable.music2),
        Pair("Música 3", R.drawable.music3)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Exibe o nome do usuário logado no topo
        Text(
            text = "Bem-vindo, $username!",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text("Lista de Músicas", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Exibe as músicas em formato de cards
        Column(modifier = Modifier.fillMaxWidth()) {
            musicList.forEach { (title, imageRes) ->
                MusicCard(title = title, imageRes = imageRes)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MusicCard(title: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Imagem da música
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            // Título da música
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
