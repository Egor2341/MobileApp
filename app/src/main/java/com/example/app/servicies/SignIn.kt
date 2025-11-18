package com.example.app.servicies

import android.util.Log
import com.example.app.data.User
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlin.String
import com.example.app.data.Base

object SignIn {

    suspend inline fun signIn(login: String, password: String, hash: Boolean): User? {

        try {
            val user = Base.getClient()
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
                        "created_at",
                        "id"
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

            if (hash || HashPassword.checkPassword(password, user.password)) {
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

}