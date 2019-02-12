package com.xun.eyepetizerkotlin.mvp.presenter

import android.content.Context
import com.xun.eyepetizerkotlin.model.HomeModel
import com.xun.eyepetizerkotlin.model.bean.HomeBean
import com.xun.eyepetizerkotlin.mvp.contract.HomeContract
import com.xun.eyepetizerkotlin.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by wangxun on 2019/2/12.
 */
class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {
    var mContext: Context? = null
    var mView: HomeContract.View? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable: Observable<HomeBean>? = mContext?.let {
            mModel.loadData(it, true, "0")
        }
        observable?.applySchedulers()?.subscribe {
            mView?.setData(it)
        }
    }

    fun moreData(data: String?) {
        val observable: Observable<HomeBean>? = mContext?.let { mModel.loadData(it, false, data) }
        observable?.applySchedulers()?.subscribe { homeBean: HomeBean ->
            mView?.setData(homeBean)
        }
    }

}