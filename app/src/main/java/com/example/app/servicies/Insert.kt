package com.example.app.servicies

import com.example.app.data.Base
import com.example.app.data.Data
import io.github.jan.supabase.postgrest.from

object Insert {
    suspend inline fun <reified T : Data> insertData(tableName: String, item: T) {
            Base.getClient().from(tableName)
                .insert(item)
    }
}