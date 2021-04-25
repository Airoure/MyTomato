package com.zjl.mytomato.entity

import android.os.Parcelable
import androidx.room.Entity
import cn.bmob.v3.BmobObject
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "FinishTodoEntity", primaryKeys = ["finishDate", "finishTime"])
data class FinishTodoEntity(
    var name: String,
    var imageUrl: String = "",
    var finishDate: String = "",
    var finishTime: String = "",
    var hour: Int = 0,
    var minute: Int = 0
) : Parcelable, BaseLocalEntity {
    override fun toNetwork(username: String) = NFinishTodoEntity(
        name, imageUrl, finishDate, finishTime, hour, minute, username
    )
}

class NFinishTodoEntity(
    var name: String,
    var imageUrl: String = "",
    var finishDate: String = "",
    var finishTime: String = "",
    var hour: Int = 0,
    var minute: Int = 0,
    var username: String
) : BmobObject(), BaseNetworkEntity {


    override fun toLocal()= FinishTodoEntity(
        name, imageUrl, finishDate, finishTime, hour, minute
    )
}
