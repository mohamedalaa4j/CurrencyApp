package com.am.project.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val BASE_URL = "https://amtalek.com/amtalekadmin/public/api/"
    }

    @GET("mobile/contact-us")
    suspend fun contactUsInfo(): Response<ContactUsResponse>
}