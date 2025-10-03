package com.example.app

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.app.fragments.onboarding.FirstOnboardingFragment
import com.example.app.fragments.GettingStartedFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    val TOKEN = stringPreferencesKey("token")
    val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        var screen = 0

            // Для сброса IS_FIRST_TIME
//        lifecycleScope.launch {
//            dataStore.updateData { currentPrefs ->
//                val mutable = currentPrefs.toMutablePreferences()
//                mutable[IS_FIRST_TIME] = true
//                mutable
//            }
//        }



        lifecycleScope.launch {
            dataStore.data.collect { prefs ->
                val isFirstTime = prefs[IS_FIRST_TIME] ?: true

                if (isFirstTime) {
                    val newToken = "token_${System.currentTimeMillis()}"
                    screen=1
                    dataStore.updateData { currentPrefs ->
                        val mutable = currentPrefs.toMutablePreferences()
                        mutable[TOKEN] = newToken
                        mutable[IS_FIRST_TIME] = false
                        mutable
                    }
                }
                // Для проверки Onboarding
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.splash_screen, FirstOnboardingFragment.newInstance())
//                    .commit()
                if (screen == 1) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.splash_screen, FirstOnboardingFragment.newInstance())
                        .commit()
                } else {
                    supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.splash_screen, GettingStartedFragment.newInstance())
                    .commit()
                }
            }
        }





    }

}