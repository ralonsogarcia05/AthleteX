package com.example.newapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.ui.text.TextStyle


@Composable
fun OnboardingScreen(onContinue: () -> Unit) {
    var selectedGender by remember { mutableStateOf<String?>(null) }
    var selectedHeight by remember { mutableStateOf(170) }
    var weight by remember { mutableStateOf(70f) }
    var isKg by remember { mutableStateOf(true) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF161211))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color(0xFF3E3A39), RoundedCornerShape(100))
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Let us know you better",
                fontSize = 20.sp,
                color = Color(0xFFF8F8F8).copy(alpha = 0.48f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "What is your gender?",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF8F8F8),
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderCard("Male", Icons.Filled.Male, selectedGender == "Male", Color(0xFF79D7FF)) { selectedGender = "Male" }
            GenderCard("Female", Icons.Filled.Female, selectedGender == "Female", Color(0xFFFF69B4)) { selectedGender = "Female" }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Your height & weight?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Let us know you better",
            fontSize = 18.sp,
            color = Color.White.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Height picker
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Height", fontSize = 20.sp, color = Color.White)
            Box(modifier = Modifier.height(150.dp)) {
                LazyColumn(state = listState) {
                    items(61) { index ->
                        val height = 150 + index
                        Text(
                            text = "$height cm",
                            fontSize = if (selectedHeight == height) 36.sp else 24.sp,
                            color = if (selectedHeight == height) Color.White else Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedHeight = height
                                    coroutineScope.launch {
                                        listState.animateScrollBy(0f)
                                    }
                                },
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Weight input
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Weight", fontSize = 20.sp, color = Color.White)
                TextButton(onClick = { isKg = !isKg }) {
                    Text("Change", color = Color.White)
                }
            }
            OutlinedTextField(
                value = weight.toString(),
                onValueChange = { input ->
                    weight = input.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: weight
                },
                label = { Text("Weight in ${if (isKg) "kg" else "lb"}") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White),

                        colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { if (selectedGender != null) onContinue() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3E3A39),
                contentColor = Color.White
            )
        ) {
            Text("Continue", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun GenderCard(gender: String, icon: ImageVector, selected: Boolean, iconTint: Color, onClick: () -> Unit) {
    val bgColor = if (selected) Color(0xFF3E3A39) else Color(0xFFF8F8F8)
    val textColor = if (selected) Color(0xFFF8F8F8) else Color(0xFF3E3A39)

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(184.dp)
            .background(bgColor, RoundedCornerShape(24.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = gender,
                tint = iconTint,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = gender,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
