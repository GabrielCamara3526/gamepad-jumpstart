package com.example.mygamepads

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygamepads.ui.theme.MyGamepadsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyGamepadsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //Buttons of Xbox layout
    val buttonsList = listOf("A", "B", "X", "Y")
    var computerButton by remember { mutableStateOf(buttonsList.random()) }
    var userButton by remember { mutableStateOf("") }


    var isVisible by remember { mutableStateOf(true)}
    var isAnimating by remember { mutableStateOf(false)}

    val context = LocalContext.current
    // set up soundPool library
    val soundPool = SoundPool.Builder()
        .setMaxStreams(2)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()


    val correctSoundId = soundPool.load(context, R.raw.correctanswer, 1)
    var wrongSoundId = soundPool.load(context, R.raw.wronganswer, 1)

    LaunchedEffect(key1 = isAnimating) {
        if(isAnimating){
            isVisible = false
            delay(400)

            // reset computer's choice
            computerButton = ""
            // computer chooses a new target button
            computerButton += buttonsList.random()

            isVisible = true
            isAnimating = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AnimatedVisibility(
            isVisible,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
            )
        ) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .background(color = Color.Black)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = computerButton,
                    fontSize = 36.sp
                )
            }
        }

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ){
            Button(

                onClick = {
                userButton = ""
                userButton += "Y"

                if (userButton == computerButton){

                    soundPool.play(correctSoundId, 3f, 2f, 1, 0, 1f)

                    isAnimating = true
                }
                          },

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                ),

                shape = CircleShape,

                modifier = Modifier.size(75.dp)
            )
            {
                Text("Y", fontSize = 30.sp)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly){

            // X Button
            Button(onClick = {
                // reset userButton before updating it
                userButton = ""
                userButton += "X"

                // if user presses matching button to computer's choice
                if (userButton == computerButton){


                    soundPool.play(correctSoundId, 3f, 2f, 1, 0, 1f)

                    isAnimating = true
                }

            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                ),
                shape = CircleShape,
                modifier = Modifier.size(75.dp)){

                Text("X", fontSize = 30.sp)
            }

            // B Button
            Button(onClick = {

                // reset userButton before updating it
                userButton = ""
                userButton += "B"


                if (userButton == computerButton){

                    soundPool.play(correctSoundId, 3f, 2f, 1, 0, 1f)

                    isAnimating = true

                }

            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Black,
                ),
                shape = CircleShape,
                modifier = Modifier.size(75.dp)){
                Text("B", fontSize = 30.sp)
            }


        }

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly){

            // A Button
            Button(onClick = {
                userButton = ""
                userButton += "A"

                if (userButton == computerButton){

                    soundPool.play(correctSoundId, 3f, 2f, 1, 0, 1f)

                    isAnimating = true
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black,
                ),
                shape = CircleShape,
                modifier = Modifier.size(75.dp)){
                Text("A", fontSize = 30.sp)
            }

        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyGamepadsTheme {
        Greeting("Android")
    }
}