package com.otmanethedev.oompaloompa.info.data.service

import com.otmanethedev.oompaloompa.info.data.models.OompaLoompaDto
import com.otmanethedev.oompaloompa.info.data.models.OompaLoompasListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/napptilus/oompa-loompas")
    suspend fun getOompaLoompasByPage(@Query("page") page: Int = 1): OompaLoompasListDto

    @GET("/napptilus/oompa-loompas/{id}")
    suspend fun getOompaLoompaById(@Path("id") id: Int): OompaLoompaDto
}