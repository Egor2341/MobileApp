package com.example.app.servicies

import com.example.app.data.Base
import io.github.jan.supabase.storage.storage
import java.util.UUID

class UserImages {

    suspend fun uploadImage(email: String, img: ByteArray): String {
        return try {

            val fileName = "${UUID.randomUUID()}"
            val storagePath = "$email/$fileName"

            Base.newInstance().getClient()
                .storage
                .from("Images")
                .upload(storagePath, img, upsert = true)

        } catch (e: Exception) {
            throw Exception("Ошибка загрузки изображения: ${e.message}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserImages()
    }
}