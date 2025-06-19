package com.example.todo_compose_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo_compose_app.R

val fontFamily  = FontFamily(
    Font(R.font.poppins_bold , FontWeight.Bold) ,
    Font(R.font.poppins_thin , FontWeight.Thin) ,
    Font(R.font.poppins_black , FontWeight.Black) ,
    Font(R.font.poppins_light , FontWeight.Light) ,
    Font(R.font.poppins_regular , FontWeight.Normal) ,
    Font(R.font.poppins_medium , FontWeight.Medium) ,
    Font(R.font.poppins_semibold , FontWeight.SemiBold) ,
    Font(R.font.poppins_extra_bold , FontWeight.ExtraBold) ,
    Font(R.font.poppins_extra_light , FontWeight.ExtraLight) ,
)
@Composable
fun GetStaredScreen(
    navController: NavController
){
    CollectingScreenElements(Modifier , navController)
}

@Composable
fun CollectingScreenElements(
    modifier: Modifier = Modifier ,
    navController: NavController
){
    val spacing = 75.dp
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = Color.White)

    ) {
        Column (
            modifier = modifier.fillMaxSize()
                .padding(18.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.spacedBy(spacing , Alignment.CenterVertically)

        ){
            DrawingToDoTheIcon()
            DrawingText()
            DrawingTheDotsIc()
            DrawingTheGetStartBtn(Modifier  , navController)
        }
    }

}

@Composable
fun DrawingToDoTheIcon(modifier: Modifier = Modifier){
    Icon(
        modifier = modifier.size(77.dp),
        painter = painterResource(R.drawable.ic_to_do) ,
        contentDescription = "" ,
        tint = Color.Unspecified

    )
}
@Preview(showBackground = true)
@Composable
fun DrawingText(
    modifier: Modifier = Modifier
){
    Text(
        text = "Welcome to a whole new world" +
                " of scheduling  made easier" ,
        modifier = modifier.fillMaxWidth()
            .padding(18.dp),
        color = Color.Black ,
        fontSize = 20.sp ,
        textAlign = TextAlign.Center
    )

}
@Preview(showBackground = true)
@Composable
fun DrawingTheDotsIc(modifier : Modifier = Modifier){
    Icon(
        painter = painterResource(R.drawable.ic_dots) ,
        contentDescription = "" ,
        tint = Color.Unspecified
    )
}

@Composable
fun DrawingTheGetStartBtn(
    modifier: Modifier = Modifier ,
    navController: NavController
){
    Button(

        modifier = modifier.width(230.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue) // Set the background color to blue
        ),
        onClick = {
            navController.navigate("signUp")
        }  ,
        shape = RoundedCornerShape(8.dp)

    ) {
        Text(
            modifier = modifier.padding(4.dp),
            text =  "Get Start" ,
            fontSize = 16.sp
        )
    }
}