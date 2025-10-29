package com.example.app.servicies

import android.util.Log
import com.example.app.data.Base
import com.example.app.data.User
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlin.String
import com.example.app.R

class SignIn {

    suspend inline fun signIn(login: String, password: String, hash: Boolean): User? {

        try {
            val user = Base.newInstance().getClient()
                .from("users")
                .select(
                    columns = Columns.list(
                        "email",
                        "password",
                        "last_name",
                        "first_name",
                        "patronymic",
                        "dob",
                        "gender",
                        "license_number",
                        "license_date",
                        "avatar",
                        "created_at"
                    )
                ) {
                    filter {
                        User::email eq login
                        if (hash){
                            User::password eq password
                        }
                    }
                }.decodeSingleOrNull<User>()

            if (user == null) {
                Log.d("SIGNIN", "non user")
                return null
            }

            if (hash || HashPassword.newInstance().checkPassword(password, user.password)) {
                return user
            } else {
                Log.d("SIGNIN", "non password")
                return null
            }

        } catch (e: Exception) {
            Log.d("SIGNIN", e.message.toString())
            return null
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = SignIn()
    }


}