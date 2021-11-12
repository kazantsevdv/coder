package com.kazantsev.coder.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kazantsev.coder.App
import com.kazantsev.coder.R
import com.kazantsev.coder.repo.UsersRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var repo: UsersRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        GlobalScope.launch {
            repo.getUsers()
        }
    }
}