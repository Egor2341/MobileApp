package com.example.app.data

import com.example.app.servicies.Insert
import io.github.cdimascio.dotenv.dotenv
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class Base {
    private val dotenv = dotenv()
    private val supabase = createSupabaseClient(
        supabaseUrl = dotenv["SUPABASE_URL"],
        supabaseKey = dotenv["SUPABASE_KEY"]
    ) {
        install(Postgrest)
    }

    fun getClient() : SupabaseClient{
        return supabase
    }

    companion object {
        @JvmStatic
        fun newInstance() = Base()
    }
}