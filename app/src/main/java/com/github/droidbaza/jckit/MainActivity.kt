package com.github.droidbaza.jckit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.droidbaza.jckit.ui.component.PreviewIconButtonWithText
import com.github.droidbaza.jckit.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Preview(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Preview(modifier: Modifier) {
    PreviewIconButtonWithText()
}
