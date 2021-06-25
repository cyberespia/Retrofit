package com.mbu.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getDogByBreeds(@Url url: String): Response<PerrosModel>
    //como va a trabajar en corotiona se agrega a la funcion suspend
}