package com.example.todo_compose_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo_compose_app.R


@Composable
fun EmailVerificationScreen(
    navController: NavController
){
    var otpValue by remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center

    ){

        DrawingToDoLogo()
        EmailVerificationTxt()
        OtpNoteTxt()
        OtpTextFiled(Modifier ,
            otpValue ,4,
            onOtpTextChanged = { value, otpInputFilled ->
            otpValue = value
        })
        OtpResendTxt()
        DrawingProceedBtn(modifier = Modifier , navController)

    }
}

@Composable
fun DrawingToDoLogo(){
    Row(
        modifier = Modifier.padding(22.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        DrawingSignUpToDoIc(modifier = Modifier)
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "ToDo",
            style = TextStyle(
                fontSize = 24.sp,
                color = colorResource(R.color.blue),
                fontWeight = FontWeight.Bold
            ),
            fontFamily = fontFamily
        )
    }
}

@Composable
fun EmailVerificationTxt(){
        Text(
            modifier = Modifier.fillMaxWidth().padding(top =24.dp),
            text = "Email Verification",
            fontSize = 20.sp,
            textAlign = TextAlign.Center  ,
            fontFamily = fontFamily ,
            fontWeight = FontWeight.SemiBold ,
            color = Color.Black
        )
}
@Composable
fun OtpNoteTxt(){
        Text(
            modifier = Modifier.fillMaxWidth().padding(top=8.dp , bottom = 20.dp),
            text = "Input the 4 digit OTP has been sent to your email address",
            fontSize = 14.sp,
            textAlign = TextAlign.Center  ,
            fontFamily = fontFamily ,
            color = Color.Gray ,
            fontWeight = FontWeight.SemiBold
        )
}
@Composable
fun OtpResendTxt(){
        Text(
            modifier = Modifier.fillMaxWidth().padding(top=8.dp , bottom = 20.dp),
            text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily ,
                    color = Color.Gray ,
                    fontWeight = FontWeight.SemiBold
                )){
                   append("You didn,t receive OTP? ")
                }
                withStyle(SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = fontFamily ,
                    color = colorResource(R.color.blue),
                    fontWeight = FontWeight.SemiBold
                )){
                   append("Resend")
                }
            },

            textAlign = TextAlign.Center  ,

        )
}

@Composable
fun AuthCodePlace(){
    Row(
        modifier = Modifier.padding(horizontal = 20.dp , vertical = 35.dp)
    )
    {
        
    }

}

@Composable
fun DrawingProceedBtn(
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

            navController.navigate("successfullyCreated")
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

@Composable
fun OtpTextFiled(
    modifier: Modifier = Modifier,
    otpText:String,
    otpDigits :Int = 6,
    onOtpTextChanged:(String , Boolean)->Unit
){
    BasicTextField(
        modifier = modifier.padding(24.dp) ,
        value = TextFieldValue(otpText  , selection = TextRange(otpText.length)) ,
        onValueChange = { it ->
            if(it.text.length <= otpDigits){
                onOtpTextChanged.invoke(it.text , it.text.length==otpDigits)
            }
        } ,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row() {

                repeat(otpDigits){index->

                    OtpCardDigit(
                        index = index ,
                        text = otpText
                    )
                    Spacer(modifier.width(4.dp))
                }
            }

        }
    )
}

@Composable
fun OtpCardDigit(
    index :Int ,
    text:String
){
    val isFocused = text.length == index
    val currentChar = when{
       index == text.length -> "_" //Current digit to enter
        index >text.length -> "-" // Future digit to enter
        else -> text[index].toString() // already entered
    }
   Box(
        modifier = Modifier.padding(8.dp).size(width = 56.dp, height = 56.dp)
            .border(
                1.dp,
                color = when {
                    isFocused -> colorResource(R.color.blue)
                    else -> Color.LightGray
                }, RoundedCornerShape(8.dp)
            ),
       contentAlignment = Alignment.Center

    ){
       Text(
           text = currentChar ,
           fontSize = 24.sp,
           textAlign = TextAlign.Center,
           color = if (isFocused) colorResource(R.color.blue) else Color.LightGray ,

           )
   }
}


