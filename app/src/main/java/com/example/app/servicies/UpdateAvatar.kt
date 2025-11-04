package com.example.app.servicies

import com.example.app.data.Base
import io.github.jan.supabase.postgrest.from

object UpdateAvatar {
    suspend inline fun update(uri: String, email: String): String {
        return try {
            Base.getClient().from("users").update(
                {
                    set("avatar", uri)
                }
            ) {
                filter {
                    eq("email", email)
                }
            }
            "success"
        } catch (e: Exception){
            e.message.toString()
        }

    }

}