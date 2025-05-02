package com.example.todo_compose_app.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo_compose_app.R

@Composable
fun SuccessFullCreatedScreen(
    navController: NavController
){
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ){
            DrawingDoneIc()
            SuccessFullyCreatedTxt()
            DrawingCreatedProceedBtn(Modifier , navController)

        }
    }


}

@Composable
fun DrawingDoneIc(modifier: Modifier = Modifier){
    Icon(
        modifier = modifier.size(88.dp),
        painter = painterResource(R.drawable.done_ic) ,
        contentDescription = "" ,
        tint = Color.Unspecified

    )
}

@Composable
fun SuccessFullyCreatedTxt(){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
               vertical = 35.dp ,
                horizontal = 12.dp
            ),
        text ="Your account has been successfully created" ,
        fontSize = 18.sp,
        fontFamily = fontFamily ,
        color = Color.Gray ,
        fontWeight = FontWeight.SemiBold
    )
}


@Composable
fun DrawingCreatedProceedBtn(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Button(

        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 22.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue) // Set the background color to blue
        ),
        onClick = {

        },
        shape = RoundedCornerShape(8.dp)

    ) {
        Text(
            modifier = modifier.padding(8.dp),
            text = "Proceed",
            fontSize = 16.sp ,
            fontFamily = fontFamily
        )
    }
}