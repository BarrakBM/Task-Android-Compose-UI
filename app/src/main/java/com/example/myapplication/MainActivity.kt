package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import javax.xml.transform.Result

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionsActivity(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun QuestionsActivity(modifier: Modifier = Modifier) {
    // dictionary of questions and answers
    val questions = mapOf(
        "Android is an operating system?" to true,
        "iOS is an operating system?" to true,
        "Windows is a mobile OS?" to false
    )

    // convert question to list (easier approach)
    val questionsList = questions.toList()
    // remember current question
    var currentQuestionIndex by remember { mutableStateOf(0) }

    // set to false to not show the result at the beginning till user choose an answer it will set to true
    var result by remember { mutableStateOf(false) }
    // store user answer (null since it's empty when app activity starts)
    var userAnswer by remember { mutableStateOf<Boolean?>(null) }

    // check if there's any questions left
    if (currentQuestionIndex < questionsList.size) {
        // assign $currentQuestion to the question and $correctAnswer to answer
        val (currentQuestion, correctAnswer) = questionsList[currentQuestionIndex]
        // check if user answerd and the answer is correct by comparing it to the correct answer
        val answeredCorrectly = result && userAnswer == correctAnswer

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxSize().padding(10.dp)
        ) {
            // get current question
            Text(
                text = currentQuestion,
                fontSize = 35.sp,
                lineHeight = 40.sp,
                modifier = Modifier
            )

            if(result && userAnswer != null){
                ShowResult(userAnswer == correctAnswer)
            }
            // if answer is correct
            if(answeredCorrectly){
                Button(onClick = {
                    // move to next question
                    currentQuestionIndex++
                    result = false
                    userAnswer = null
                }) {
                    Text("Next Question")
                }
            } else {
                // if answer is not correct or user hasn't answer
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = {
                        userAnswer = true
                        result = true
                    }) { Text("True") }
                    Button(onClick = {
                        userAnswer = false
                        result = true
                    }) { Text("False") }
                }
            }
        }
    }
}

//@Composable
//fun Question(modifier: Modifier = Modifier, question: String, correctAnswer: Boolean,  onNextQuestion: () -> Unit) {
//
//
//    // set to false to not show the result at the beginning till user choose an answer it will set to true
//    var result by remember { mutableStateOf(false) }
//    // store user answer (null since it's empty when app activity starts)
//    var userAnswer by remember { mutableStateOf<Boolean?>(null) }
//
//    // check if user choose an answer and it's correct
//    val answeredCorrectly = result && userAnswer == correctAnswer
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween,
//        modifier = modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        // question
//        Text(
//            text = question,
//            fontSize = 35.sp,
//            lineHeight = 40.sp,
//            modifier = Modifier
//        )
//
//        // check if result and user answer not null
//        if(result && userAnswer != null){
//            val isCorrect = userAnswer == correctAnswer
//            ShowResult(isCorrect)
//        }
//
//        // if user answered correctly, show next question box
//        if(answeredCorrectly){
//            Button(
//                onClick = onNextQuestion,
//            ) {
//                Text(
//                    text = "Next Question",
//                    fontSize = 18.sp
//                )
//            }
//        }
//        // true false
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // when user click true
//            Button(onClick = {
//                userAnswer = true // save answer
//                result = true     // show result
//            }) {
//                Text("True")
//            }
//            // when user click false
//            Button(onClick = {
//                userAnswer = false
//                result = true     // show result
//            }) {
//                Text("False")
//            }
//        }
//    }
//}

@Composable
fun ShowResult(isCorrectAnswer: Boolean, modifier: Modifier = Modifier){
    val boxColor = if (isCorrectAnswer) Color.Green else Color.Red
    val resultText = if (isCorrectAnswer) "Correct answer!" else "Wrong answer!"

    // circle
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(boxColor, CircleShape)
            , contentAlignment = Alignment.Center
    ){
        Text(
            text = resultText,
            textAlign = TextAlign.Center,
            fontSize = 30.sp
            )

    }
}

