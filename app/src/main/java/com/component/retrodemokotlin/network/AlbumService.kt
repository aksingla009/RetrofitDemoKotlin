package com.component.retrodemokotlin.network

import com.component.retrodemokotlin.Albums
import com.component.retrodemokotlin.AlbumsItem
import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums() : Response<Albums>

    //Query Parameter
    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") passedUserID : Int) : Response<Albums>

    //Path Parameter
    @GET("/albums/{id}")
    suspend fun getParticularAlbumWithAlbumID (@Path(value = "id") albumID : Int) : Response<AlbumsItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumsItem) : Response<AlbumsItem>
}