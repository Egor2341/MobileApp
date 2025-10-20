package com.example.app.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class Base {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://vdtbjriekspausmkcggd.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGJqcmlla3NwYXVzbWtjZ2dkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjA1MTQ1MzAsImV4cCI6MjA3NjA5MDUzMH0.huZUuIzeNA0pwYZ65RHW-XDHMB_pBZkbIkfmztF2cRo"
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