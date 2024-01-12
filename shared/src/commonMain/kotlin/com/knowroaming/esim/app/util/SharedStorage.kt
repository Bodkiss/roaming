package com.knowroaming.esim.app.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SharedStorage {
    companion object {
        fun clearAllStorage()
        fun clearSecureStorage()
        fun clearSharedStorage()
        fun getAll(): Map<String, *>
        fun getAllSecure(): Map<String, *>
        fun <T> load(key: String, default: T): T
        fun <T> save(value: T, forKey: String)
        fun secureLoad(key: String, default: String): String
        fun secureSave(value: String, forKey: String)
    }
}