package de.handler.mobile.guerillaprose.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.handler.mobile.guerillaprose.BuildConfig
import de.handler.mobile.guerillaprose.parseItem
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


class UserProvider(private val client: OkHttpClient, private val moshi: Moshi) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun getUser(id: String): Deferred<User?> {
        return async {
            try {
                val request = Request.Builder().url("${BuildConfig.BACKEND_URI}user?id=$id").get().build()
                val response = client.newCall(request).execute()
                return@async response.parseItem<User>(moshi, Types.newParameterizedType(User::class.java))
            } catch (e: Exception) {
                Timber.e(e)
                return@async null
            }
        }
    }

    fun createUser(user: User): Deferred<User?> {
        return async {
            try {
                val jsonString = moshi.adapter<User>(
                        Types.newParameterizedType(User::class.java)).toJson(user)
                Timber.i("JSON String $jsonString")
                val requestBody = RequestBody.create(
                        MediaType.parse("application/json"),
                        jsonString)
                val request = Request.Builder().url("${BuildConfig.BACKEND_URI}user").post(requestBody).build()
                val response = client.newCall(request).execute()
                return@async response.parseItem<User>(moshi, Types.newParameterizedType(User::class.java))
            } catch (e: Exception) {
                Timber.e(e)
                return@async null
            }
        }
    }

    fun updateUser(user: User): Deferred<User?> {
        return async {
            try {
                val jsonString = moshi.adapter<User>(
                        Types.newParameterizedType(User::class.java)).toJson(user)
                Timber.i("JSON String $jsonString")
                val requestBody = RequestBody.create(
                        MediaType.parse("application/json"),
                        jsonString)
                val request = Request.Builder().url("${BuildConfig.BACKEND_URI}user").put(requestBody).build()
                val response = client.newCall(request).execute()
                return@async response.parseItem<User>(moshi, Types.newParameterizedType(User::class.java))
            } catch (e: Exception) {
                Timber.e(e)
                return@async null
            }
        }
    }

    fun deleteUser(id: String): Deferred<User?> {
        return async {
            try {
                val request = Request.Builder().url("${BuildConfig.BACKEND_URI}user?id=$id").delete().build()
                val response = client.newCall(request).execute()
                return@async response.parseItem<User>(moshi, Types.newParameterizedType(User::class.java))
            } catch (e: Exception) {
                Timber.e(e)
                return@async null
            }
        }
    }
}
