package com.siddhika.data.auth

import java.io.File
import java.util.Base64
import java.util.Properties
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Desktop implementation of AuthTokenStorage using encrypted file storage.
 */
class AuthTokenStorageImpl : AuthTokenStorage {

    private val storageFile: File = File(System.getProperty("user.home"), ".siddhika/auth_tokens.enc")
    private val secretKey: SecretKeySpec

    init {
        storageFile.parentFile?.mkdirs()
        secretKey = deriveKey()
    }

    override suspend fun saveToken(token: String) {
        val props = loadProperties()
        props[KEY_ID_TOKEN] = encrypt(token)
        saveProperties(props)
    }

    override suspend fun getToken(): String? {
        val props = loadProperties()
        val encrypted = props.getProperty(KEY_ID_TOKEN) ?: return null
        return decrypt(encrypted)
    }

    override suspend fun clearToken() {
        val props = loadProperties()
        props.remove(KEY_ID_TOKEN)
        saveProperties(props)
    }

    override suspend fun saveRefreshToken(token: String) {
        val props = loadProperties()
        props[KEY_REFRESH_TOKEN] = encrypt(token)
        saveProperties(props)
    }

    override suspend fun getRefreshToken(): String? {
        val props = loadProperties()
        val encrypted = props.getProperty(KEY_REFRESH_TOKEN) ?: return null
        return decrypt(encrypted)
    }

    override suspend fun clearAll() {
        if (storageFile.exists()) {
            storageFile.delete()
        }
    }

    private fun loadProperties(): Properties {
        val props = Properties()
        if (storageFile.exists()) {
            storageFile.inputStream().use { props.load(it) }
        }
        return props
    }

    private fun saveProperties(props: Properties) {
        storageFile.outputStream().use { props.store(it, null) }
    }

    private fun deriveKey(): SecretKeySpec {
        // Using a machine-specific salt for key derivation
        val machineId = System.getProperty("user.name") + System.getProperty("os.name")
        val salt = machineId.toByteArray().copyOf(16)

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(ENCRYPTION_PASSWORD.toCharArray(), salt, 65536, 256)
        val secret = factory.generateSecret(spec)
        return SecretKeySpec(secret.encoded, "AES")
    }

    private fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        // Combine IV and encrypted data
        val combined = ByteArray(iv.size + encrypted.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encrypted, 0, combined, iv.size, encrypted.size)

        return Base64.getEncoder().encodeToString(combined)
    }

    private fun decrypt(encryptedText: String): String {
        val combined = Base64.getDecoder().decode(encryptedText)

        // Extract IV and encrypted data
        val iv = combined.copyOfRange(0, 16)
        val encrypted = combined.copyOfRange(16, combined.size)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
        val decrypted = cipher.doFinal(encrypted)

        return String(decrypted, Charsets.UTF_8)
    }

    companion object {
        private const val KEY_ID_TOKEN = "id_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val ENCRYPTION_PASSWORD = "SiddhikaAuthStorage2024"
    }
}
