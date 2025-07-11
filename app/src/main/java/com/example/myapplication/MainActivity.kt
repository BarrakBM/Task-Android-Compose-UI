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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionScreen(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun QuestionScreen(modifier: Modifier = Modifier) {
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

    // score state
    var score by remember { mutableStateOf(0) }

    // store user answer (null since it's empty when app activity starts)
    var userAnswer by remember { mutableStateOf<Boolean?>(null) }

    // check if there's any questions left
    if (currentQuestionIndex < questionsList.size) {
        // assign $currentQuestion to the question and $correctAnswer to answer
        val (currentQuestion, correctAnswer) = questionsList[currentQuestionIndex]

        // check if user answerd and the answer is correct by comparing it to the correct answer
        val answeredCorrectly = userAnswer == correctAnswer

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // get current question
            Text(
                text = currentQuestion,
                fontSize = 35.sp,
                lineHeight = 40.sp,
                modifier = Modifier
            )

            // if user choose an answer and result are not null (show result)
            if (userAnswer != null) {
                ShowResult(userAnswer == correctAnswer)
            }
            // if answer is correct
            if (answeredCorrectly && currentQuestionIndex < questionsList.size) {
                ElevatedButton(
                    onClick = {
                        // move to next question
                        currentQuestionIndex++
                        userAnswer = null
                        score++
                    }, modifier = modifier
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.next_question),
                        fontSize = 24.sp
                    )
                }
            } else {
                // if answer is not correct or user hasn't answer
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ElevatedButton(
                        onClick = {
                            userAnswer = true // user answer
                        }, modifier = modifier
                            .weight(1f)
                            .padding(end = 8.dp)

                    ) {
                        Text(
                            text = stringResource(R.string.TRUE),
                            fontSize = 24.sp
                        )
                    }

                    // false button
                    ElevatedButton(
                        onClick = {
                            userAnswer = false // user answer
                        },
                        modifier = modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.False),
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ShowScore(score)

            // Add some spacing between score and button
            Spacer(modifier = Modifier.height(32.dp))

            ElevatedButton(
                onClick = {
                    // Reset all state variables to restart the game
                    currentQuestionIndex = 0
                    score = 0
                    userAnswer = null
                }, modifier = modifier
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.restart_game),
                    fontSize = 24.sp
                )
            }
        }
    }
}


@Composable
fun ShowResult(isCorrectAnswer: Boolean, modifier: Modifier = Modifier) {
    val boxColor = if (isCorrectAnswer) Color.Green else Color.Red
    val resultText = if (isCorrectAnswer) "Correct answer!" else "Wrong answer!"

    // circle
    Box(
        modifier = modifier
            .size(200.dp)
            .background(boxColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = resultText,
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
    }
}

@Composable
fun ShowScore(score: Int, modifier: Modifier = Modifier) {
    val backgroundColor = Color.Blue
    var scoreResult by remember { mutableStateOf(score) }

    // Remove fillMaxSize() since it's now part of a Column layout
    Box(
        modifier = modifier
            .size(200.dp)
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.score, scoreResult),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color.White
        )
    }
}


