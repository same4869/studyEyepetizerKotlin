package com.xun.eyepetizerkotlin.model

import android.content.Context
import com.xun.eyepetizerkotlin.model.bean.HomeBean
import com.xun.eyepetizerkotlin.network.ApiService
import com.xun.eyepetizerkotlin.network.RetrofitClient
import io.reactivex.Observable
import retrofit2.Retrofit

/**
 * Created by wangxun on 2019/2/12.
 */
class HomeModel {
    fun loadData(context: Context, isFirst: Boolean, data: String?): Observable<HomeBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient?.create(ApiService::class.java)
        when (isFirst) {
            true -> return apiService?.getHomeData()
            false -> return apiService?.getHomeMoreData(data.toString(), "2")
        }
    }
}