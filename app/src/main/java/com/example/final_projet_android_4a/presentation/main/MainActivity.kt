package com.example.final_projet_android_4a.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.final_projet_android_4a.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.loginLiveData.observe(this, Observer {
            when(it){
                is LoginSuccess -> {
                    //TODO Navigate
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Erreur")
                        .setMessage("bv bogoss")
                        .setPositiveButton("Ok") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)
                }
                LoginError -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Erreur")
                        .setMessage("Compte inconnu")
                        .setPositiveButton("Ok") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()

                }
            }
        })
        login_button.setOnClickListener {
            mainViewModel.onClickedLogin(login_edit.text.toString().trim(), password_edit.text.toString())
        }

        create_account_button.setOnClickListener{
            mainViewModel.onClickedCreate(login_edit.text.toString().trim(), password_edit.text.toString())
        }
    }
}