package com.example.adex.data

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @GET("api/getUser/{login}/{password}")
    suspend fun getUserById(
        @Path("login") login : String,
        @Path("password") password : String
    ) : User

    @GET("api/applications")
    suspend fun getAllApplications() : List<Application>

    @GET("api/getApplicationsTG")
    suspend fun getApplicationsTG() : List<Application>

    @GET("api/getApplicationsVK")
    suspend fun getApplicationsVK() : List<Application>

    @POST("api/createUser")
    suspend fun createUser(@Body body : UserSignup) : ResponseBody

    @POST("api/createOffer")
    suspend fun createOffer(@Body body : OfferCreated) : ResponseBody

    @GET("api/getApplicationsForUser/{userId}")
    suspend fun getApplicationsForUser(
        @Path("userId") userId : Int
    ) : List<Application>

    @GET("api/getOfferRecipient/{accountRecipient}")
    suspend fun getOfferRecipient(
        @Path("accountRecipient") userId : Int
    ) : List<Offer>

    @GET("api/getOfferConsumer/{accountConsumer}")
    suspend fun getOfferConsumer(
        @Path("accountConsumer") userId : Int
    ) : List<Offer>


}