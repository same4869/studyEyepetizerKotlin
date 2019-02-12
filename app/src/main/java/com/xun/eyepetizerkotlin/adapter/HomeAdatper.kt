package com.xun.eyepetizerkotlin.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xun.eyepetizerkotlin.R
import com.xun.eyepetizerkotlin.model.bean.HomeBean
import com.xun.eyepetizerkotlin.utils.ImageLoadUtils

/**
 * Created by wangxun on 2019/2/12.
 */
class HomeAdatper(context: Context, list: MutableList<HomeBean.IssueListBean.ItemListBean>?) : RecyclerView.Adapter<HomeAdatper.HomeViewHolder>() {
    var mContext: Context? = null
    var mList: MutableList<HomeBean.IssueListBean.ItemListBean>? = null
    var layoutInflater: LayoutInflater? = null

    init {
        mContext = context
        mList = list
        layoutInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        return HomeViewHolder(layoutInflater?.inflate(R.layout.item_home,parent,false), mContext!!)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        var bean = mList?.get(position)
        var title = bean?.data?.title
        var category = bean?.data?.category
        var minute = bean?.data?.duration?.div(60)
        var second = bean?.data?.duration?.minus((minute?.times(60)) as Long )
        var realMinute : String
        var realSecond : String
        if(minute!!<10){
            realMinute = "0"+minute
        }else{
            realMinute = minute.toString()
        }
        if(second!!<10){
            realSecond = "0"+second
        }else{
            realSecond = second.toString()
        }
        var playUrl = bean?.data?.playUrl
        var photo = bean?.data?.cover?.feed
        var author = bean?.data?.author
        ImageLoadUtils.display(mContext!!,holder?.iv_photo, photo as String)
        holder?.tv_title?.text = title
        holder?.tv_detail?.text = "发布于 $category / $realMinute:$realSecond"
        if(author!=null){
            ImageLoadUtils.display(mContext!!,holder?.iv_user,author.icon as String)
        }else{
            holder?.iv_user?.visibility = View.GONE
        }
        holder?.itemView?.setOnClickListener {
            //跳转视频详情页
//            var intent : Intent = Intent(context,VideoDetailActivity::class.java)
//            var desc = bean?.data?.description
//            var duration = bean?.data?.duration
//            var playUrl = bean?.data?.playUrl
//            var blurred = bean?.data?.cover?.blurred
//            var collect = bean?.data?.consumption?.collectionCount
//            var share = bean?.data?.consumption?.shareCount
//            var reply = bean?.data?.consumption?.replyCount
//            var time = System.currentTimeMillis()
//            var videoBean  = VideoBean(photo,title,desc,duration,playUrl,category,blurred,collect ,share ,reply,time)
//            var url = SPUtils.getInstance(context!!,"beans").getString(playUrl!!)
//            if(url.equals("")){
//                var count = SPUtils.getInstance(context!!,"beans").getInt("count")
//                if(count!=-1){
//                    count = count.inc()
//                }else{
//                    count = 1
//                }
//                SPUtils.getInstance(context!!,"beans").put("count",count)
//                SPUtils.getInstance(context!!,"beans").put(playUrl!!,playUrl)
//                ObjectSaveUtils.saveObject(context!!,"bean$count",videoBean)
//            }
//            intent.putExtra("data",videoBean as Parcelable)
//            context?.let { context -> context.startActivity(intent) }
        }
    }

    class HomeViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView) {
        var tv_detail : TextView?= null
        var tv_title : TextView? = null
        var tv_time : TextView? = null
        var iv_photo : ImageView? = null
        var iv_user : ImageView? = null
        init {
            tv_detail = itemView?.findViewById(R.id.tv_detail) as TextView?
            tv_title = itemView?.findViewById(R.id.tv_title) as TextView?
            iv_photo = itemView?.findViewById(R.id.iv_photo) as ImageView?
            iv_user =  itemView?.findViewById(R.id.iv_user) as ImageView?
            tv_title?.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")

        }
    }
}