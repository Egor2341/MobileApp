package com.example.app.servicies

import android.util.Log
import com.example.app.data.Base
import io.github.jan.supabase.postgrest.from

class UpdateAvatar {
    suspend inline fun update(uri: String, email: String): String {
        return try {
            Base.newInstance().getClient().from("users").update(
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

    companion object {
        @JvmStatic
        fun newInstance() = UpdateAvatar()
    }
}