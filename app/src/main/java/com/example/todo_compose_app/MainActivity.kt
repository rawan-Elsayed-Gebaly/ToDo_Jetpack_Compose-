package com.example.todo_compose_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.todo_compose_app.navigation.AppNavHost
import com.example.todo_compose_app.ui.theme.TODO_Compose_AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)

//            val color = remember { mutableStateOf(Color.Yellow) }

//            Column(
//                modifier =
//                Modifier.fillMaxSize()
//            ) {
//                ColorBox(
//                    Modifier
//                        .weight(1f)
//                        .fillMaxSize()
//                ) {
//                    color.value = it
//                }
//
//                Box(
//                    Modifier
//                        .weight(1f)
//                        .background(color.value)
//                        .fillMaxSize()
//                )
//
//            }

// ################# Textfields, Buttons & Showing Snackbars - Android Jetpack Compose - Part 7 ######
//            Column(modifier = Modifier.fillMaxSize()) {
//
//                GenerateTextFilesAndSnackBar(Modifier.weight(1f))
//
//                GeneratingListOfItems(Modifier.weight(1f))
//
//
//            }


//            okkkay Well Done RooRoo

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

}

@Composable
fun GreetingPreview() {
    TODO_Compose_AppTheme {
        Greeting("Android")
    }
}


@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit

) {
    Box(modifier
        .fillMaxSize()
        .background(Color.Green)
        .clickable {
            updateColor(
                Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )

        }
    ) {


        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 50.sp
                    )
                ) {
                    append("J")
                }
                append("etpack ")
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 50.sp
                    )
                ) {
                    append("C")
                }
                append("ompose")

            },
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}


@Composable
fun GenerateTextFilesAndSnackBar (
    modifier: Modifier ,
) {
    var textFiledValueState  by remember{
        mutableStateOf("")
    }

    val snackBarHostState = remember{ SnackbarHostState()}

    val scope =  rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ){


        Column (
            modifier = Modifier.fillMaxSize(), // Fix height issue
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = textFiledValueState ,
                label = {
                    Text("Enter Your Name!")
                } ,
                onValueChange = {
                    textFiledValueState = it
                } ,
                singleLine = true ,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.Black ,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 16.sp  ,
                    fontStyle = FontStyle.Italic
                )

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Hello $textFiledValueState" ,
                            actionLabel = "Dismiss" ,
                            duration = SnackbarDuration.Long
                        )
                    }

                }
            ) {
                Text("Greeting Me")
            }

        }

        SnackbarHost(
            hostState = snackBarHostState ,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

    }



}

@Composable
fun GeneratingListOfItems (
    modifier: Modifier
){
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize() ,
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            for( i in 1..60){
                Text(
                    text = "Item $i" ,
                    fontSize = 18.sp ,
                    fontStyle = FontStyle.Normal ,
                    fontWeight = FontWeight.Bold ,
                    textAlign = TextAlign.Center ,
                    modifier = Modifier.padding(6.dp)

                )
                Spacer(modifier = Modifier.height(3.dp))
            }

        }
    }

}

