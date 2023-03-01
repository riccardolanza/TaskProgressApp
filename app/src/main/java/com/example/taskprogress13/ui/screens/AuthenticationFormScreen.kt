package com.example.taskprogress13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun AuthenticationFormScreen(
    onPasswordVerify: (String) -> Unit,
)
{

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Creating a variable to store password
        var password by remember { mutableStateOf("") }

        // Creating a variable to store toggle state
        var passwordVisible by remember { mutableStateOf(false) }

        var passwordCorretta:Boolean? by remember { mutableStateOf(null) }

        // Create a Text Field for giving password input
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                // Toggle button to hide or display password
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
        Box(modifier=Modifier.padding(horizontal = 110.dp, vertical = 10.dp)) {
            Button(onClick = { onPasswordVerify(password) }
            ) { Text(text = "Verifica") }
        }
        if(passwordCorretta != null) {
            if (passwordCorretta==true)
                Text(text = "Password corretta")
            else
                Text(text = "Password errata")
        }
    }
}

fun PasswordVerify(password:String): Boolean?
{
    if (password == "ciao") return true
    else return false
}
