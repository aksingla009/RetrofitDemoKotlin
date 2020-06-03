package com.component.retrodemokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.component.retrodemokotlin.network.AlbumService
import com.component.retrodemokotlin.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG : String = "MainActivity"
    private lateinit var retrofitService : AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitService  = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        //getRequest()

        //getRequestWithQueryParameters()
        //getRequestWithPathParameters()

        //PostRequest to save Album to server
        postRequestToUploadAlbum()




    }

    private fun postRequestToUploadAlbum() {
        val album = AlbumsItem(0,"My Title",3)

        val postResponse : LiveData<Response<AlbumsItem>> = liveData {
            val response = retrofitService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this, Observer {
            it.body()?.let { receivedAlbum ->
                val result : String = " " + "Album Title : ${receivedAlbum.title}" + "\n" +
                        " " + "Album ID : ${receivedAlbum.id}" + "\n" +
                        " " + "User ID : ${receivedAlbum.userId}" + "\n\n\n"

                textView.append(result)
            }

        })

    }

    private fun getRequest() {
        val responseLiveData : LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if(albumsList != null){
                while(albumsList.hasNext()){
                    val albumsItem = albumsList.next()

                    val result : String = " " + "Album Title : ${albumsItem.title}" + "\n" +
                            " " + "Album ID : ${albumsItem.id}" + "\n" +
                            " " + "User ID : ${albumsItem.userId}" + "\n\n\n"

                    textView.append(result)

                }
            }
        })

    }

    private fun getRequestWithQueryParameters() {
        val responseLiveData : LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getSortedAlbums(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if(albumsList != null){
                while(albumsList.hasNext()){
                    val albumsItem = albumsList.next()

                    val result : String = " " + "Album Title : ${albumsItem.title}" + "\n" +
                            " " + "Album ID : ${albumsItem.id}" + "\n" +
                            " " + "User ID : ${albumsItem.userId}" + "\n\n\n"

                    textView.append(result)

                }
            }
        })
    }

    private fun getRequestWithPathParameters() {
        val responseItem : LiveData<Response<AlbumsItem>> = liveData {
            val response = retrofitService.getParticularAlbumWithAlbumID(3)
            emit(response)
        }

        responseItem.observe(this, Observer {
            it.body()?.let {
                Log.i(TAG,it.title)
            }
        })
    }
}