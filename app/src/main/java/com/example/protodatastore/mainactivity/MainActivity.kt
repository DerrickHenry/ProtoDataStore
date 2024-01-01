package com.example.protodatastore.mainactivity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.protodatastore.R
import com.example.protodatastore.users.views.users.UsersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, UsersFragment())
                    .commit()
        }
    }

}