package com.example.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.data.User
import com.example.app.fragments.GettingStartedFragment
import com.example.app.fragments.NoConnectionFragment
import com.example.app.fragments.onboarding.FirstOnboardingFragment
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    private lateinit var user: User

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
                    screen = 1
                    dataStore.updateData { currentPrefs ->
                        val mutable = currentPrefs.toMutablePreferences()
                        mutable[TOKEN] = newToken
                        mutable[IS_FIRST_TIME] = false
                        mutable
                    }
                }

//                 Для проверки Onboarding
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.splash_screen, FirstOnboardingFragment.newInstance())
//                    .commit()

                if (screen == 1) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.splash_screen, FirstOnboardingFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                } else {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.splash_screen, GettingStartedFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    fun isInternet(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

    fun noConFragment(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(id, NoConnectionFragment.newInstance())
            .addToBackStack("No internet")
            .commit()
    }

    fun changeFragment(fragment: Fragment, id: Int, back: String) {
        if (isInternet()) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(id, fragment)
            transaction.addToBackStack(back)
            transaction.commit()
        } else {
            noConFragment(id)
        }
    }

    fun changeFragment(fragment: Fragment, id: Int) {
        if (isInternet()) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            noConFragment(id)
        }
    }

    fun setUser(data: User){
        user = data
    }

    fun getUser() : User{
        return user
    }
}