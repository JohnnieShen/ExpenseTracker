package com.example.expensetrackersubmission

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Wrapper for main activity, used in manifest to enable Hilt
@HiltAndroidApp
class ExpenseTrackerSubmission : Application()
