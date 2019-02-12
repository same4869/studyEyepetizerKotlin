package com.xun.eyepetizerkotlin.mvp.contract

import com.xun.eyepetizerkotlin.base.BasePresenter
import com.xun.eyepetizerkotlin.base.BaseView
import com.xun.eyepetizerkotlin.model.bean.HomeBean

/**
 * Created by wangxun on 2019/2/12.
 */
interface HomeContract {
    interface View : BaseView<Presenter> {
        fun setData(bean: HomeBean)
    }

    interface Presenter : BasePresenter {
        fun requestData()
    }
}