package com.example.headsupprep.model

import com.example.headsupprep.classes.CelebrityGame
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/celebrities/")
    fun showInfo(): Call <List<CelebrityGame?>>

    @Headers("Content-Type: application/json")
    @POST("/celebrities/")
    fun addInfo(@Body newUbring: AddCelebrity): Call<AddCelebrity>

    @Headers("Content-Type: application/json")
    @PUT("/celebrities/{pk}")
    fun updateInfo(@Path("pk") pk: Int, @Body updateUbring: CelebrityGame): Call<CelebrityGame>

    @Headers("Content-Type: application/json")
    @DELETE ("/celebrities/{pk}")
    fun deleteInfo(@Path("pk") pk: Int): Call<Void>

}