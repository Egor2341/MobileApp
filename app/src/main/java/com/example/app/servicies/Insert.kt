package com.example.app.servicies

import androidx.lifecycle.lifecycleScope
import com.example.app.MainActivity
import com.example.app.data.Base
import com.example.app.data.Data
import com.example.app.data.User
import com.example.app.fragments.sign_up.ThirdSignUpFragment
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class Insert() {
    suspend inline fun <reified T : Data> insertData(tableName: String, item: T) {
            Base.newInstance().getClient().from(tableName)
                .insert(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Insert()
    }
}