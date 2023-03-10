package com.example.valyuta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.valyuta.adapter.RvAdpter
import com.example.valyuta.databinding.ActivityMainBinding
import com.example.valyuta.models.MyMoney
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var adpter:RvAdpter
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getCurrency()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(){
                binding.rv.adapter = adpter
            }
    }

    private fun getCurrency(): io.reactivex.Observable<Any>? {

        return io.reactivex.Observable.create {
            val url = URL("http://cbu.uz/uzc/arkhiv-kursov-valyut/json/")
            val list = ArrayList<MyMoney>()
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bufferReader = inputStream.bufferedReader()
            val gsonString = bufferReader.readLine()
            val gson = Gson()

            val type = object : TypeToken<ArrayList<MyMoney>>() {}.type
            list.addAll(gson.fromJson<ArrayList<MyMoney>>(gsonString, type))
            adpter = RvAdpter(list)
            it.onNext(list)
        }
    }
}