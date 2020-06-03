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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitService  = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

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
}