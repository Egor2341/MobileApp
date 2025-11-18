package com.example.app.servicies

import android.util.Log
import com.example.app.data.Base
import com.example.app.data.Data
import io.github.jan.supabase.postgrest.from

object Insert {
    suspend inline fun <reified T : Data> insertData(tableName: String, item: T) {
            val res = Base.getClient().from(tableName)
                .insert(item)

    }
}