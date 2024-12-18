package com.example.musikasten

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musikasten.ui.theme.MusikastenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusikastenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        onLoginSuccess = {
                            // Navega para MusicListActivity
                            val intent = Intent(this, MusicListActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        onNavigateToRegister = {
                            // Navega para RegisterActivity
                            val intent = Intent(this, RegisterActivity::class.java)
                            startActivity(intent)
                        },
                        validateLogin = { username, password ->
                            validateLogin(username, password)
                        }
                    )
                }
            }
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        // Recupera os dados do SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", null)
        val savedPassword = sharedPref.getString("password", null)

        return if (username == savedUsername && password == savedPassword) {
            true
        } else {
            Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            false
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    validateLogin: (String, String) -> Boolean
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Campo de Usuário
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuário") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Senha
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botão de Login
        Button(
            onClick = {
                if (validateLogin(username, password)) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Entrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Registro
        OutlinedButton(
            onClick = { onNavigateToRegister() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Criar Conta")
        }
    }
}
