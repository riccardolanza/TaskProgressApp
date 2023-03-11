package com.example.taskprogress13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskprogress13.ui.theme.TaskProgress13Theme
import com.example.taskprogress13.ui.theme.WindowSize
import com.example.taskprogress13.ui.theme.rememberWindowSizeClass
import com.example.taskprogress13.ui.theme.smallDimensions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val window = rememberWindowSizeClass()
            TaskProgress13Theme(window) {
                 TaskProgressApp(modifier=Modifier)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val window = rememberWindowSizeClass()
    TaskProgress13Theme(window) {
        TaskProgressApp(modifier = Modifier)
    }
}