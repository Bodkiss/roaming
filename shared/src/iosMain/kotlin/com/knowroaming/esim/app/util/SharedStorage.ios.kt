package com.knowroaming.esim.app.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedStorage {
    actual companion object {
        actual fun clearAllStorage() {
        }

        actual fun clearSecureStorage() {
        }

        actual fun clearSharedStorage() {
        }

        actual fun getAll(): Map<String, *> {
            TODO("Not yet implemented")
        }

        actual fun getAllSecure(): Map<String, *> {
            TODO("Not yet implemented")
        }

        actual fun <T> load(key: String, default: T): T {
            TODO("Not yet implemented")
        }

        actual fun <T> save(value: T, forKey: String) {
        }

        actual fun secureLoad(key: String, default: String): String {
            TODO("Not yet implemented")
        }

        actual fun secureSave(value: String, forKey: String) {
        }
    }

}