package com.example.expensetrackersubmission

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import com.example.expensetrackersubmission.ui.Theme
import com.example.expensetrackersubmission.features.ui.ExpenseScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                Surface {
                    ExpenseScreen()
                }
            }
        }
    }
}
