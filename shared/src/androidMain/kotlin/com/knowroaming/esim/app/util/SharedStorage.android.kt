package com.knowroaming.esim.app.util


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.securepreferences.SecurePreferences
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SharedStorage {

    actual companion object {


        private val defaultPreferenceName = "${BuildConfig.APP_PACKAGE}.preferences"
        private val migrationFlagKey = "$defaultPreferenceName::isMigrated"
        private val password = "${BuildConfig.APP_PACKAGE}.password"


        private val concurrentCommit = AtomicBoolean(false)

        private var isMigrated: Boolean
            get() = load(migrationFlagKey, false)
            set(value) = save(value, migrationFlagKey)

        private lateinit var encryptedPreferences: SharedPreferences
        private lateinit var sharedPreferences: SharedPreferences


        actual fun clearAllStorage() {
            clearSharedStorage()
            clearSecureStorage()
        }

        actual fun clearSecureStorage() = clearPreferences(encryptedPreferences)

        actual fun clearSharedStorage() = clearPreferences(sharedPreferences)

        actual fun getAll(): Map<String, *> = try {
            sharedPreferences.all.filter { it.key != migrationFlagKey }
        } catch (e: Throwable) {
            emptyMap<String, Any>()
        }

        actual fun getAllSecure(): Map<String, *> = try {
            encryptedPreferences.all
        } catch (e: Throwable) {
            emptyMap<String, Any>()
        }

        actual fun <T> load(key: String, default: T) = loadData(key, default, false)

        actual fun <T> save(value: T, forKey: String) = saveData(value, forKey, false)

        actual fun secureLoad(key: String, default: String) = loadData(key, default, true)

        actual fun secureSave(value: String, forKey: String) = saveData(value, forKey, true)


        fun configure(
            context: Context,
            preferenceName: String = defaultPreferenceName,
            preferenceMode: Int = Context.MODE_PRIVATE
        ) {
            sharedPreferences = context.getSharedPreferences(preferenceName, preferenceMode)
            setEncryptedPreferences(context, preferenceName)
            doMigrationIfNeeded(context, preferenceMode)
        }


        private fun clearPreferences(preferences: SharedPreferences) {
            preferences.edit {
                clear()
                commit()
            }
        }

        private fun commit(editor: SharedPreferences.Editor) {
            if (!concurrentCommit.get()) {
                editor.apply()
                return
            }

            editor.commit()
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> loadData(key: String, default: T, secure: Boolean): T {
            val preference: SharedPreferences = if (secure) {
                encryptedPreferences
            } else {
                sharedPreferences
            }
            val valueForKey: (String) -> T = {
                when (default) {
                    is Int -> preference.getInt(it, default) as T
                    is Boolean -> preference.getBoolean(it, default) as T
                    is Long -> preference.getLong(it, default) as T
                    is Float -> preference.getFloat(it, default) as T
                    else -> preference.getString(it, default.toString()) as T
                }
            }

            if (preference.contains(key)) {
                return valueForKey(key)
            }
            if (secure) {
                val encryptedKey = SecurePreferences.hashPrefKey(key)
                if (preference.contains(encryptedKey)) {
                    return valueForKey(encryptedKey)
                }
            }
            return default
        }

        private fun <T> saveData(value: T, forKey: String, secure: Boolean) {
            val editor: SharedPreferences.Editor = if (secure) {
                encryptedPreferences.edit()
            } else {
                sharedPreferences.edit()
            }
            when (value) {
                is Int -> editor.putInt(forKey, value)
                is Long -> editor.putLong(forKey, value)
                is Float -> editor.putFloat(forKey, value)
                is Boolean -> editor.putBoolean(forKey, value)
                else -> editor.putString(forKey, value.toString())
            }

            commit(editor)
        }

        private fun setEncryptedPreferences(context: Context, preferenceName: String) {
            val prefName = "$preferenceName::encrypted"
            val masterKey =
                MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            encryptedPreferences = EncryptedSharedPreferences.create(
                context,
                prefName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        private fun doMigrationIfNeeded(context: Context, preferenceMode: Int) {
            if (isMigrated) {
                return
            }
            migrateSharedPreferences(context, preferenceMode)
            migrateSecurePreferences(context)
            isMigrated = true
        }

        private fun migrateSecurePreferences(context: Context) {
            val prefName = defaultPreferenceName + "_encrypted"
            val preferences = SecurePreferences(context, password, prefName)
            preferences.all.forEach {
                secureSave(it.value, it.key)
            }
            clearPreferences(preferences)
        }

        private fun migrateSharedPreferences(context: Context, preferenceMode: Int) {
            val preferences = context.getSharedPreferences(defaultPreferenceName, preferenceMode)
            preferences.all.forEach {
                save(it.value, it.key)
            }
            clearPreferences(preferences)
        }
    }
}