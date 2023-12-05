package com.bälls.balllist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wojaksoftware.balllist.ui.theme.BallListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BallListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainLayout() {
    Column (
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Namen Liste",
            style = MaterialTheme.typography.headlineMedium,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        var nameInput by remember {
            mutableStateOf("")
        }

        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = nameInput,
            leadingIcon = { Icon(imageVector = Icons.Default.Person,
                contentDescription = "PersonIcon")},
            onValueChange = {
                nameInput = it
            },
            label = { Text(text = "Name")},
            placeholder = { Text(text = "Enter Name to add...")},
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(0.9f),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the done action if needed
                    keyboardController?.hide()
                }
            ),
        )

        var nameList by remember { mutableStateOf(listOf<String>()) }

        Button(onClick = {
            if (nameInput != "") {
                nameList = nameList + nameInput
                nameInput = ""
                keyboardController?.hide()
            }},
            colors = ButtonDefaults.buttonColors(contentColor = Color.DarkGray)) {
            Text(text = "Add Name", color = Color.White)
        }

        Text(nameList.size.toString() + " Items",
            Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp))

        LazyColumn(content = {
            items(nameList.size) { index ->
                Card (
                    Modifier
                        .fillMaxWidth(0.9f)
                        .padding(10.dp)
                ) {
                    Text(
                        text = nameList[index],
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = androidx.compose.ui.Modifier.padding(16.dp)
                    )
                }
            }
        })
    }
}
