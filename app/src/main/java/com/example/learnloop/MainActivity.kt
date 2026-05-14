package com.example.learnloop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.learnloop.ui.navigation.LearnLoopNavGraph
import com.example.learnloop.ui.theme.LearnLoopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnLoopTheme {
                LearnLoopNavGraph()
            }
        }
    }
}