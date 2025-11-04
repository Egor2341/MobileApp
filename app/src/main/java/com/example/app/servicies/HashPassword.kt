package com.example.app.servicies

import com.example.app.fragments.sign_up.FirstSignUpFragment
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object HashPassword {

    private fun generateRandomSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    fun hashPassword(password: String): String {
        val salt = generateRandomSalt()
        val spec = PBEKeySpec(password.toCharArray(), salt,
            65536, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded

        return Base64.getEncoder().encodeToString(salt + hash)
    }

    fun checkPassword(password: String, storedHash: String) : Boolean {
        return try {

            val decoded = Base64.getDecoder().decode(storedHash)

            val salt = decoded.copyOfRange(0, 16)

            val originalHash = decoded.copyOfRange(16, decoded.size)

            val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 256)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val testHash = factory.generateSecret(spec).encoded

            originalHash.contentEquals(testHash)

        } catch (e: Exception) {
            false
        }
    }

}