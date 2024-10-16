package com.weatherapp.ui

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.RegisterActivity
import com.weatherapp.db.fb.FBAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginPage(modifier: Modifier = Modifier)
{
    var email by rememberSaveable { mutableStateOf(value="") }
    var password by rememberSaveable { mutableStateOf(value="") }
    var activity = LocalContext.current as? Activity
    val coroutineScope = rememberCoroutineScope()
    val firebaseAuth = remember { FBAuth() }


    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo/a!",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        DataField(
            value = email,
            onChange = { email = it },
            label = "Digite seu e-mail",
            modifier = modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.size(12.dp)
        )
        PasswordField(
            value = password,
            onChange = { password = it },
            label = "Digite sua senha",
            modifier = modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row (
            modifier = Modifier
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        val status = firebaseAuth.signIn(email, password)
                        val msg = if (status) "Login OK!" else "Login FALHOU!"
                        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text(text = "Login")
            }
            Spacer(
                modifier = Modifier.size(5.dp)
            )
            Button(
                onClick = {
                    activity?.startActivity(Intent(activity, RegisterActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                }
            ) {
                Text(text = "Cadastrar Usuário")
            }
            Spacer(
                modifier = Modifier.size(5.dp)
            )
            Button(
                onClick = { email = ""; password = ""; },
                enabled = email.isNotEmpty() || password.isNotEmpty()
            ) {
                Text(text = "Limpar")
            }
        }
    }
}